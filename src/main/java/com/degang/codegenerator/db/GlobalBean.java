package com.degang.codegenerator.db;

import lombok.Data;

import java.util.List;

/**
 * Created by degang on 2018/11/29
 */
@Data
public class GlobalBean {
    /** 当前时间，生成类时间用 */
    private String nowDate;

    /** 当前用户，生成类用 */
    private String userName;

    /** 工程包路径，生成类用 */
    private String packageName;

    /** 工程所有表List */
    private List<String> tableNames;
}
