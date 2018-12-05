package com.degang.codegenerator.db;

import com.degang.codegenerator.util.FilesUtil;
import com.degang.codegenerator.util.PropertiesUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by degang on 2018/11/28
 */
@Data
@Slf4j
public class DBSettings {

    public static final int DB_TYPE_MYSQL = 1;

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
    private String schema;

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

    private String genAbsPath;

    private String hasWorkFlow;

    private String bizType;

    private String creator;

    private String enumField;

    private String subEnumField;

    public static void main(String[] args) {
        DBSettings settings = new DBSettings();
        System.out.println(ClassLoader.getSystemResource("").getPath());
        System.out.println(System.getProperty("user.dir"));
        System.out.println(Paths.get("temp").toAbsolutePath().toString());
        // System.out.println(settings.initSystemParam());
    }

    /**
     * 初始化系统参数
     *
     * @return
     */
    public boolean initSystemParam() {
        boolean res = true;
        //加载DB属性文件
        String propertiesFilename = "config.properties";
        //解析属性文件
        Properties prop = PropertiesUtil.getPropertiesByResourceBundle(FilesUtil.getFilenameWithoutExt(propertiesFilename));
        if (prop == null) {
            log.error("OUT---[false]属性配置文件内容解析为空！");
            return false;
        }

        dbType =  DB_TYPE_MYSQL;
        url = (String) prop.get("DB_SERVER");
        port = (String) prop.get("DB_PORT");
        schema = (String) prop.get("DB_SCHEMA");
        dbUser = (String) prop.get("DB_USER");
        dbPwd = (String) prop.get("DB_PWD");
        //设置生成指定表、使用模版、Java包路径、代码输出路径
        mainTableName = (String) prop.get("DB_TABLES");
        String itemTableNameString = (String) prop.get("SUB_TABLES");
        if (!StringUtils.isEmpty(itemTableNameString)) {
            itemTableNames.addAll(Arrays.asList(itemTableNameString.split(",")));
        }
        module = (String)prop.get("MODULE_NAME");
        enumField = (String)prop.get("ENUM_FIELD");
        subEnumField = (String)prop.get("SUB_ENUM_FIELD");

        String genPathService = (String)prop.get("GEN_PATH_SERVICE");
        String genPathApi = (String) prop.get("GEN_PATH_API");
        String genPathWeb = (String) prop.get("GEN_PATH_WEB");

        // TODO 生成代码时根据需求修改
        setGenAbsPath(Paths.get("temp").toAbsolutePath().toString());

        setCreator((String)prop.get("CREATOR"));
        return res;
    }
}
