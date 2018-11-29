package com.degang.codegenerator.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by degang on 2018/11/28
 */
@Slf4j
public class PropertiesUtil {


    /**
     * 通过java.util.ResourceBundle获取属性,
     * 从ClassPath中获取路径，不需要写 “.properties”后缀
     * @param filename 文件名
     * @return java.util.Properties
     */
    public static Properties getPropertiesByResourceBundle(String filename) {
        Assert.notNull(filename, "文件名不能为空");

        ResourceBundle rb = null;

        try {
            rb = ResourceBundle.getBundle(filename);
        } catch (MissingResourceException e) {
            log.error("指定文件未找到：{}", filename);
            return null;
        }

        Properties prop = new Properties();

        Enumeration<String> keys = rb.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = rb.getString(key);
            prop.put(key, value);
        }

        return prop;
    }

    /**
     * java.util.Properties从java.io.InputStream(ClassLoader.getSystemResourceAsStream)中加载属性
     * 从ClassPath中获取路径
     * @param filename
     * @return java.util.Properties
     */
    public static Properties getPropertiesByFilename(String filename) {
        Properties prop = new Properties();


        try {
            InputStream is = ClassLoader.getSystemResourceAsStream(filename);

            if (is != null) {
                prop.load(is);
                is.close();
            } else {
                log.error("文件classpath路径错误：{}", filename);
            }
        } catch (IOException e) {
            log.error("filename:{}", filename, e);
        }

        return prop;
    }
}
