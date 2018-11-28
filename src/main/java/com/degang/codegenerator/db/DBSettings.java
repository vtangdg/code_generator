package com.degang.codegenerator.db;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by degang on 2018/11/28
 */
@Data
@Component
public class DBSettings {

    /**
     * 数据库驱动类名
     */
    private String driver;
    /**
     * 数据库类型
     */
    private int dbType;
    /**
     * 数据库服务器
     */
    private String url;
    /**
     * port
     */
    private String port;
    /**
     * 数据库名
     */
    private String dbName;

    private String dbConstants;

    /**
     * 数据库用户名
     */
    private String dbUser;
    /**
     * 数据库用户密码
     */
    private String dbPwd;
    /**
     * 指定生成表
     */
    private String mainTableName;

    private List<String> itemTableNames = new ArrayList<>();


    private String module;

    private String genPathService;

    private String genPathApi;

    private String genPathWeb;

    private String hasWorkFlow;

    private String bizType;

    private String creator;

    private String enumField;

    private String subEnumField;

   /* public static void main(String[] args) {
        DBSettings settings = new DBSettings();
        System.out.println(ClassLoader.getSystemResource("").getPath());
        System.out.println(environment.getProperty(""));
        // System.out.println(settings.initSystemParam());
    }

    *//**
     * 初始化系统参数
     *
     * @return
     *//*
    public boolean initSystemParam() {
        boolean res = true;
        //加载DB属性文件
        String propertiesFilename = "config.properties";
        //解析属性文件
        Properties prop = PropertiesUtil.getPropertiesByResourceBundle(FileUtil.getFilenameWithoutExt(propertiesFilename));
        if (prop == null) {
            logger.error("OUT---[false]属性配置文件内容解析为空！");
            return false;
        }

        dbType = Settings.DB_TYPE_MYSQL;
        url = (String) prop.get("DB_SERVER");
        port = (String) prop.get("DB_PORT");
        dbName = (String) prop.get("DB_NAME");
        dbConstants = (String) prop.get("DB_NAME_CONSTANTS");
        dbUser = (String) prop.get("DB_USER");
        dbPwd = (String) prop.get("DB_PWD");
        //设置生成指定表、使用模版、Java包路径、代码输出路径
        mainTableName = (String) prop.get("DB_TABLES");
        String itemTableNameString = (String) prop.get("SUB_TABLES");
        if (StringUtils.isNotEmpty(itemTableNameString)) {
            String[] ts = itemTableNameString.split(",");
            for (int i = 0; i < ts.length; i++) {
                itemTableNames.add(ts[i]);
            }
        }
        module = (String)prop.get("MODULE_NAME");
        enumField = (String)prop.get("ENUM_FIELD");
        subEnumField = (String)prop.get("SUB_ENUM_FIELD");
        String genPathService = (String)prop.get("GEN_PATH_SERVICE");
        String genPathApi = (String) prop.get("GEN_PATH_API");
        String genPathWeb = (String) prop.get("GEN_PATH_WEB");
        //users/beifang/javaproject/mogu/
        String baseService = System.getProperty("user.dir").replace("iip_erp", "");

        setGenPathService(baseService + genPathService);
        setGenPathApi(baseService + genPathApi);
        setGenPathWeb(baseService + genPathWeb);
        setBizType((String)prop.get("BIZ_TYPE"));
        setHasWorkFlow((String) prop.get("HAS_WORKFLOW"));
        setCreator((String)prop.get("CREATOR"));
        return res;
    }*/
}
