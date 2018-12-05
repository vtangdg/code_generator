package com.degang.codegenerator.db;

import com.degang.codegenerator.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * Created by degang on 2018/11/28
 */
@Slf4j
@Data
public class DBConnection {
    /** Mysql JDBC 驱动程序 */
    private final static String DB_DRIVER_MYSQL = "com.mysql.jdbc.Driver";

    /** Connection对象 */
    private Connection conn = null;

    /** Statement对象 */
    private Statement stmt = null;

    /** 数据库连接是否OK **/
    private boolean initialized;

    private DBSettings dbSettings;

    private DatabaseMetaData databaseMetaData;

    public DBConnection(DBSettings settings) {
        this.dbSettings = settings;
        init();
    }

    public DatabaseMetaData getDatabaseMetaData() {
        if (this.databaseMetaData != null) {
            return this.databaseMetaData;
        }
        DatabaseMetaData dbmd = null;
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException e) {
            log.error("", e);
        }
        this.databaseMetaData = dbmd;
        return dbmd;
    }

    public void closeConn() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.error("关闭连接出错", e);
        }
    }

    public void closeStmt() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            log.error("关闭Statement出错", e);
        }
        return;
    }



    private void init() {
        StringBuilder strConn = new StringBuilder();
        try {
            if (dbSettings.getDbType() == DBSettings.DB_TYPE_MYSQL) {
                dbSettings.setDriver(DB_DRIVER_MYSQL);
                Class.forName(DB_DRIVER_MYSQL);

                strConn.append("jdbc:mysql://")
                        .append(dbSettings.getUrl())
                        .append(':')
                        .append(dbSettings.getPort())
                        .append('/')
                        .append(dbSettings.getSchema());
                conn = DriverManager.getConnection(strConn.toString(), dbSettings.getDbUser(), dbSettings.getDbPwd());


            }
        } catch (ClassNotFoundException | SQLException e) {
            log.error("", e);
            initialized = false;
            return;
        }

        log.info("database connect success:{}", dbSettings.getSchema());
        initialized = true;
        logDatabaseMetaData();
    }

    private DatabaseMetaData logDatabaseMetaData() {
        DatabaseMetaData dbmd = null;
        ResultSet rs = null;

        try {
            dbmd = conn.getMetaData();
            rs = dbmd.getTypeInfo();

            while (rs.next()) {
                log.info("类型名称【"
                        + StringUtil.genLengthStr(rs.getString(1), 20)
                        + "】SqlType【"
                        + StringUtil.genLengthStr(rs.getString(2), 5)
                        + "】最大精度【"
                        + StringUtil.genLengthStr(rs.getString(3), 10) + "】");log.info("");
            }

            // 获取数据库信息
            log.info("数据库类型：{},版本：{},驱动程序：{},驱动程序版本：{}",
                    dbmd.getDatabaseProductName(), dbmd.getDatabaseProductVersion(), dbmd.getDriverName(), dbmd.getDriverVersion());

        } catch (SQLException e) {
            log.error("", e);
        }

        return dbmd;
    }
}
