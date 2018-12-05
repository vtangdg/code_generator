package com.degang.codegenerator.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用Files和Path取代File
 * Created by degang on 2018/11/29
 */
@Slf4j
public class FilesUtil {

    /**
     * 根据指定路径获取下面一级所有文件夹列表(不包括文件,或隐藏文件夹)
     *
     * @param filepath 文件路径
     * @return 返回List，如果传入的路径不对或扩展名为空，则返回空List。
     */
    public static List<String> listOneDepthFileDirectory(String filepath) {
        if (StringUtils.isEmpty(filepath)) {
            return Collections.emptyList();
        }

        Path path = Paths.get(filepath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            return Collections.emptyList();
        }
        try {
            return Files.list(path)
                    .filter(Files::isDirectory)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("", e);
            return Collections.emptyList();
        }

    }

    /**
     * 根据指定路径获取下一层级指定扩展名的文件列表(不包括文件夹)
     *
     * @param filepath 文件路径
     * @param ext 扩展名 空则不限定扩展名
     * @return 返回List，如果传入的路径不对，则返回空List；如果扩展名为空则返回所有文件
     */
    public static List<String> listOndDepthFileWithExt(String filepath, String ext) {
        if (StringUtils.isEmpty(filepath)) {
            return Collections.emptyList();
        }

        String validExt = ext == null ? "" : ext;
        Path path = Paths.get(filepath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            return Collections.emptyList();
        }

        try {
            return Files.list(path)
                    .filter(item -> ("".equals(validExt) || validExt.equals(getExt(item.getFileName()))))
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("", e);
            return Collections.emptyList();
        }


    }

    public static String getExt(Path path) {
        if (path == null) {
            return "";
        }
        int i = path.toString().lastIndexOf(".");
        if (i == -1) {
            return path.toString();
        }

        return path.toString().substring(i);
    }

    /**
     * 获取去除后缀名后的文件名
     * 例如：<br>
     * <ul>
     *  <li>test.vm -> test</li>
     *  <li>test -> test</li>
     *  <li>null -> ""</li>
     *  <li>"" -> ""</li>
     * </ul>
     *
     * @param filename 文件名
     * @return 如果传入的文件名为空则返回空字符串"",如果文件名没有扩展名，则返回原文件名。
     */
    public static String getFilenameWithoutExt(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return "";
        }
        int i = filename.lastIndexOf(".");
        if (i == -1) {
            return filename;
        }

        return filename.substring(0, i);
    }

    /**
     * 根据传入的路径创建文件夹,如果路径不存在则创建之。
     *
     * @param filepath 路径
     * @return true or false
     */
    public static boolean mkDirs(String filepath) {
        try {
            Files.createDirectories(Paths.get(filepath));
        } catch (IOException e) {
            log.error("", e);
            return false;
        }
        return true;
    }



    public static void main(String[] args) {
        String packagePath = "src/main/java/com/degang/codegenerator/";
        try {
            System.out.println(Files.isRegularFile(Paths.get(packagePath)));
            System.out.println(listOneDepthFileDirectory(packagePath));
            System.out.println(listOndDepthFileWithExt(packagePath, null));
            System.out.println(listOndDepthFileWithExt(packagePath, ".zip"));
            System.out.println(listOndDepthFileWithExt(packagePath, ".java"));
            System.out.println(getFilenameWithoutExt("1.txt"));
            //使用Paths工具类的get()方法创建
            Path path = Paths.get(packagePath, "util", "FileUtil.java");
            System.out.println(getExt(path));
            System.out.println("文件名：" + path.getFileName());
            System.out.println("名称元素的数量：" + path.getNameCount());
            System.out.println("父路径：" + path.getParent());
            System.out.println("根路径：" + path.getRoot());
            System.out.println("是否是绝对路径：" + path.isAbsolute());
            System.out.println("绝对路径：" + path.toAbsolutePath());
            //startsWith()方法的参数既可以是字符串也可以是Path对象
            System.out.println("是否是以为给定的路径D:开始：" + path.startsWith("D:\\") );
            System.out.println("该路径的字符串形式：" + path.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
