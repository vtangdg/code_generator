package com.degang.codegenerator.db;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by degang on 2018/11/29
 */
@Data
@Slf4j
public class TableBean {
    private List<TableBean> itemList;
    private TableConfig tableConfig;

    private String tableName;
    // 类名
    private String className;
    // 类对象名
    private String objectName;

    private String classNameComment;

    private String lowerClassName;

    private Integer hasEnum;
    private List<String> enumFieldList;
    private List<String> subEnumFieldList;



    private List<ColBean> colList = new ArrayList<>();
    private Map<String, ColBean> colMap = new LinkedHashMap<>();


    public TableBean(String tableName, TableConfig tableConfig) {
        this.tableName = tableName;
        this.tableConfig = tableConfig;
        this.className = getClassName(tableName);
    }

    public TableBean addColBean(ColBean colBean) {
        if (!this.colMap.containsKey(colBean.getColName())) {
            this.colMap.put(colBean.getColName(), colBean);
            this.colList.add(colBean);
        }
        return this;
    }



    /**
     * 根据表名生成对象类名
     * <ul>
     *     <li>user -> User</li>
     *     <li>user_info -> UserInfo</li>
     * </ul>
     * @param tableName 表名
     * @return 大写字母开头的类名
     */
    private String getClassName(String tableName) {
        // TODO

        return tableName;
    }
}
