package com.degang.codegenerator.db;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by degang on 2018/11/28
 */
@Slf4j
public class Gen {
    private final static String RESOURCE_PATH = ClassLoader.getSystemResource("").getPath();

    // 代码路径
    private final static String PATH_JAVA = "src/main/java/com/degang/codegenerator/temp/";
    private final static String PATH_RESOURCES = "src/main/resources/";
    protected static final  Map<String, TableConfig> tconfig = new ConcurrentHashMap<>();

    private GenTable genTable;
    private GlobalBean globalBean;
    private DBSettings settings;



    public Gen(DBConnection dbConnection) {
        genTable = new GenTable(dbConnection);
        globalBean = new GlobalBean();
        settings = new DBSettings();
    }


    public static void main(String[] args) {
        doGenDB();
    }

    private static void doGenDB() {
        log.info("task start=============");
        DBSettings settings = new DBSettings();
        if (!settings.initSystemParam()) {
            log.error("doGenDB initSystemParam failed");
            return;
        }
        DBConnection dbConnection = new DBConnection(settings);
        if (!dbConnection.isInitialized()) {
            log.error("doGenDB init db connection failed");
            return;
        }

        Gen gen = new Gen(dbConnection);
        gen.globalBean.setNowDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        gen.globalBean.setUserName(settings.getCreator());
        gen.globalBean.setPackageName(settings.getModule());

       boolean tableNameExist = gen.genTable.checkTableName(settings.getMainTableName());
        if (!tableNameExist) {
            log.error("not find table,tableName:{}", settings.getMainTableName());
            return;
        }

        dbConnection.closeConn();


        log.info("task end=============");
    }

    /**
     * 生成指定表的数据访问层
     * @return 表类名
     */
    private TableBean doGenTable(String mainTableName, List<String> itemTableNames, Map<String, String> allTabComments) {
        TableConfig conf = Gen.tconfig.get(mainTableName);
        TableBean mainTableBean = genTable.prepareTableBean(mainTableName, conf);
        mainTableBean.setTableConfig(conf == null ? TableConfig.DEFAULT : conf);
        mainTableBean.setClassNameComment(allTabComments.get(mainTableBean.getClassName()));

        List<TableBean> itemTableBeanList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(itemTableNames)) {
            for(String itemTableName : itemTableNames) {
                TableBean itemTableBean = genTable.prepareTableBean(itemTableName, conf);
                itemTableBean.setClassNameComment(allTabComments.get(itemTableBean.getClassName()));
                itemTableBeanList.add(itemTableBean);
            }
            mainTableBean.setItemList(itemTableBeanList);
        }
        // TODO

        return mainTableBean;
    }
}
