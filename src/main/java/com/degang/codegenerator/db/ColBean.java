package com.degang.codegenerator.db;

import com.degang.codegenerator.util.CharUtil;
import lombok.Data;

/**
 * Created by degang on 2018/12/3
 */
@Data
public class ColBean {
    /**================字段结构信息======================*/
    /** 字段列名，例如：full_name */
    private String colName;
    /** 字段注释，例如：姓名全称 */
    private String colComment;
    /** 字段类型名称，例如：VARCHAR2 */
    private String colType;
    /** 字段类型java.sql.Types，例如：Types.VARCHAR*/
    private int colSQLType;
    /** 字段是否可以为空 */
    private boolean nullable;
    /** 字段可为空时的缺省*/
    private String defaultValue;
    /** 字段长度 */
    private int precision;
    /** 字段小数位（float,double)*/
    private int scale;
    /** 是否主键 */
    private boolean isPK = false;
    /** 是否自动增长 */
    private boolean isAutoIncrement = false;

    /**================根据字段结构生成字段对象信息======================*/
    /** 方法名，例如：FullName */
    private String methodName;
    /** 属性名，例如：fullName */
    private String propertyName;
    /** 属性类型，例如：String */
    private String propertyType;

    /** 字段类型Mysql映射，例如：VARCHAR */
    private String colTypeForMysql;

    /**
     * 将字段名转成属性名,例如：full_name->fullName
     * @param colName 字段名
     * @return 属性名
     */
    public static String getPropName(String colName) {
        char[] cs = colName.toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean nextShouldUp = false;
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] != '_') {
                if (nextShouldUp) {
                    sb.append(CharUtil.toUpperCase(cs[i]));
                    nextShouldUp = false;
                } else {
                    sb.append(cs[i]);
                }
            } else {
                nextShouldUp = true;
            }
        }
        return sb.toString();
    }

    /**
     * 根据属性名生成方法名,例如：fullName->FullName
     * @param propName
     * @return
     */
    public static String getMethodName(String propName) {
        char[] a = propName.toCharArray();
        a[0] = CharUtil.toUpperCase(a[0]);
        return new String(a);
    }

}
