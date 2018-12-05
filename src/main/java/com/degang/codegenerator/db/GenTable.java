package com.degang.codegenerator.db;

import com.degang.codegenerator.util.ListUtil;
import com.degang.codegenerator.util.SqlType2Feild;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by degang on 2018/11/28
 */
@Slf4j
@Data
public class GenTable {
    private DBConnection conn;
    private DBSettings settings;



    public GenTable(DBConnection conn) {
        this.conn = conn;
        settings = conn.getDbSettings();
    }

    public boolean checkTableName(String tableName) {
        DatabaseMetaData dbmd = conn.getDatabaseMetaData();
        ResultSet rs;

        // 可选值："TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"
        String[] types = {"TABLE"};
        try {
            // 返回5列数据,如下所示(schema,catalog,table_name,table_type,REMARKS)
            rs = dbmd.getTables(null, null, "%", types);
            log.info("============================获取所有表结构信息：");
            while (rs.next()) {
                if (tableName.equals(rs.getString(3))) {
                    log.info("schema:{},tableName:{},tableType:{},tableComment:{}",
                            rs.getString(1), rs.getString(3), rs.getString(4), rs.getString(5));

                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public TableBean prepareTableBean(String tableName, TableConfig config) {
        if (config == null) {
            config = TableConfig.DEFAULT;
        }
        if (StringUtils.isEmpty(tableName)) {
            return null;
        }

        TableBean tableBean = new TableBean(tableName, config);
        this.setTableColumn(tableBean);
        this.convertTableBean(tableBean);
        tableBean.setLowerClassName(tableName.toLowerCase());
        tableBean.setEnumFieldList(StringUtils.isEmpty(settings.getEnumField()) ? null : ListUtil.string2List(settings.getEnumField()));
        tableBean.setSubEnumFieldList(StringUtils.isEmpty(settings.getSubEnumField()) ? null : ListUtil.string2List(settings.getSubEnumField()));
        if(CollectionUtils.isEmpty(tableBean.getEnumFieldList()) && CollectionUtils.isEmpty(tableBean.getSubEnumFieldList())) {
            tableBean.setHasEnum(0);
        } else {
            tableBean.setHasEnum(1);
        }
        return tableBean;
    }



    private void setTableColumn(TableBean tableBean) {
        DatabaseMetaData dbmd = null;
        ResultSet rs = null;

        try {
            dbmd = conn.getDatabaseMetaData();
            rs = dbmd.getColumns(null, null, tableBean.getTableName(), "%");
            while (rs.next()) {
                logColumnInfo(rs);
                ColBean cb = new ColBean();
                cb.setColName(rs.getString(4));
                cb.setColSQLType(rs.getInt(5));
                cb.setColType(rs.getString(6));
                cb.setColComment(StringUtils.isEmpty(rs.getString(12)) ? cb.getColName() : rs.getString(12));
                cb.setNullable(rs.getInt(11) != 0);
                cb.setDefaultValue(rs.getString(13));
                cb.setPrecision(rs.getInt(7) + 1);
                cb.setScale(rs.getInt(9));
                cb.setAutoIncrement("YES".equals(rs.getString(23)));
                cb.setPK(rs.getString(4).equals("id"));

                tableBean.addColBean(cb);
            }
        } catch (SQLException e) {
            log.error("", e);
        }
    }

    private void logColumnInfo(ResultSet rs) {
        try {
            log.info("表名【" + rs.getString(3)
                    + "】列名【" + rs.getString(4)
                    + "】列sqlType【" + rs.getInt(5)
                    + "】列typename【" + rs.getString(6)
                    + "】列precision【" + rs.getInt(7)
                    + "】列scale【" + rs.getInt(9)
                    + "】列isNullable【" + rs.getInt(11)
                    + "】列isNullable2【" + rs.getString(18)
                    + "】列comment【" + rs.getString(12)
                    + "】列defaultValue【" + rs.getString(13)
                    + "】列isAutoIncrement【" + rs.getString(23) + "】");
        } catch (SQLException e) {
        }
    }

    /**
     * 转化表对象各个字段列类型
     *
     * @param tableBean
     * @return
     */
    private void convertTableBean(TableBean tableBean) {
        List<ColBean> colBeans = tableBean.getColList();

        for (ColBean cb : colBeans) {
            cb.setPropertyName(ColBean.getPropName(cb.getColName()));
            cb.setMethodName(ColBean.getMethodName(cb.getPropertyName()));
            cb.setPropertyType(SqlType2Feild.mapJavaType(cb.getColSQLType()));
        }
    }
}
