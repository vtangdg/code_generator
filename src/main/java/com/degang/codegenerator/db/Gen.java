package com.degang.codegenerator.db;

/**
 * Created by degang on 2018/11/28
 */
public class Gen {
    private final static String RESOURCE_PATH = ClassLoader.getSystemResource("").getPath();

    // 代码路径
    private final static String PATH_JAVA = "src/main/java/com/degang/codegenerator/container/";
    private final static String PATH_RESOURCES = "src/main/resources/";



    public static void main(String[] args) {
        System.out.println(RESOURCE_PATH);
    }
}
