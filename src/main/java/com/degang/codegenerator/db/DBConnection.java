package com.degang.codegenerator.db;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by degang on 2018/11/28
 */
@Slf4j
public class DBConnection {
    /** Mysql JDBC 驱动程序 */
    private final static String DB_DRIVER_MYSQL = "com.mysql.jdbc.Driver";

    /** Connection对象 */
    private Connection conn = null;

    /** Statement对象 */
    private Statement stmt = null;

    /** 数据库连接是否OK **/
    private boolean isInit;

    private DBSettings dbSettings;

}
