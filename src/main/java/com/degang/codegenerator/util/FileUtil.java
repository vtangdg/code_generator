package com.degang.codegenerator.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by degang on 2018/11/28
 */
@Slf4j
public class FileUtil {
    /**
     * 根据指定路径获取下面一级所有文件夹列表(不包括文件,或隐藏文件夹)
     *
     * @param filepath 文件路径
     * @return 返回List，如果传入的路径不对或扩展名为空，则返回空List。
     */
    public static List<String> listFileDirector(String filepath) {
        if (StringUtils.isEmpty(filepath)) {
            return Collections.emptyList();
        }

        File file = new File(filepath);
        if (!file.exists() || !file.isDirectory()) {
            return Collections.emptyList();
        }

        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }

        return Arrays.stream(files).filter(item -> item.isDirectory() && !item.isHidden()).map(File::getName).collect(Collectors.toList());
    }


    /**
     * 根据指定路径获取下一层级指定扩展名的文件列表(不包括文件夹)
     *
     * @param filepath 文件路径
     * @param ext 扩展名 空则不限定扩展名
     * @return 返回List，如果传入的路径不对，则返回空List；如果扩展名为空则返回所有文件
     */
    public static List<String> listFileWithExt(String filepath, String ext) {
        if (StringUtils.isEmpty(filepath)) {
            return Collections.emptyList();
        }

        String anothExt = ext == null ? "" : ext;
        File file = new File(filepath);
        if (!file.exists() || !file.isDirectory()) {
            return Collections.emptyList();
        }

        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }

        return Arrays.stream(files).filter(item -> ("".equals(anothExt) || anothExt.equals(getExt(item.getName())) && item.isFile())).map(File::getName).collect(Collectors.toList());
    }

    /**
     * 获取文件扩展名<br>
     * 例如：<br>
     * <ul></li>
     * <li>test.vm -> .vm</li>
     * <li>test -> ""</li>
     * <li>null -> ""</li>
     * <li>"" -> ""</li>
     * </ul>
     *
     * @param filename 文件路径
     * @return 如果传入的文件名为空则返回空字符串"",如果文件名没有扩展名，则返回""。
     */
    public static String getExt(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return "";
        }
        if (filename.lastIndexOf(".") == -1) {
            return "";
        }

        return filename.substring(filename.lastIndexOf("."));
    }

    /**
     * 获取去除后缀名后的文件名
     * 例如：<br>
     * <ul></li>
     * <li>test.vm -> test</li>
     * <li>test -> test</li>
     * <li>null -> ""</li>
     * <li>"" -> ""</li>
     * </ul>
     *
     * @param filename 文件名
     * @return 如果传入的文件名为空则返回空字符串"",如果文件名没有扩展名，则返回原文件名。
     */
    public static String getFilenameWithoutExt(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return "";
        }
        if (filename.lastIndexOf(".") > -1) {
            return filename.substring(0, filename.lastIndexOf("."));
        }

        return filename;
    }

    /**
     * 根据传入的路径创建文件夹,如果路径不存在则创建之。
     *
     * @param filepath 路径
     * @return true or false
     */
    public static boolean mkDirs(String filepath) {
        File file = new File(filepath);
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }

    /**
     * 复制文件
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @return true or false
     */
    public static boolean copyFile(File sourceFile, File targetFile) {
        if (sourceFile == null || targetFile == null) {
            return false;
        }

        try {
            FileInputStream is = new FileInputStream(sourceFile);
            BufferedInputStream inBuff = new BufferedInputStream(is);

            FileOutputStream out = new FileOutputStream(targetFile);
            BufferedOutputStream outBuff = new BufferedOutputStream(out);

            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();

            inBuff.close();
            is.close();
            outBuff.close();
            out.close();
        } catch (FileNotFoundException e) {
            log.error("文件拷贝的源文件或目标文件不存在, sourceFile:{}, targetFile:{}", sourceFile.getName(), targetFile.getName(), e);
            return false;
        } catch (IOException e) {
            log.error("IO exception", e);
            return false;
        }

        return true;
    }

    /**
     * 复制文件夹
     *
     * @param sourceDir
     * @param targetDir
     * @return true or false
     */
    public static boolean copyDirectory(String sourceDir, String targetDir) {
        if (StringUtils.isEmpty(sourceDir) || StringUtils.isEmpty(targetDir)) {
            return false;
        }
        // 新建目标目录
        File file = new File(targetDir);
        file.mkdirs();
        // 获取源文件夹当前下的文件或目录
        File sourceFileDir = new File(sourceDir);
        if (!sourceFileDir.exists() || !sourceFileDir.isDirectory() || sourceFileDir.isHidden()) {
            log.warn("源目录不存在,或没有要拷贝的目录文件,或是隐藏文件如(.svn)！不进行拷贝，路径是：" + sourceDir);
            return false;
        }
        File[] files = sourceFileDir.listFiles();
        if (files == null || files.length <= 0){
            return false;
        }

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                // 源文件
                File sourceFile = files[i];
                // 目标文件
                File targetFile = new File(
                        new File(targetDir).getAbsolutePath() + File.separator
                                + files[i].getName());
                copyFile(sourceFile, targetFile);
            }

            if (files[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + files[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + files[i].getName();
                copyDirectory(dir1, dir2);
            }
        }
        return true;
    }

    /**
     * 删除目录下所有指定后缀的文件名
     *
     * @param filepath 文件目录路径
     * @param ext
     * @throws IOException
     */
    public static void delExtFile(String filepath, String ext) {
        if (StringUtils.isEmpty(filepath) || StringUtils.isEmpty(ext)) {
            return;
        }
        File file = new File(filepath);
        if (!file.exists()) {
            log.error("删除文件目录不存在！");
            return;
        }
        // 判断是文件还是目录
        if (file.isDirectory()) {
            // 若目录下没有文件则返回成功
            if (file.listFiles() == null || file.listFiles().length == 0) {
                return;
            }
            // 若有则把文件放进数组，并判断是否有下级目录
            File delFile[] = file.listFiles();
            int len = file.listFiles().length;
            for (int i = 0; i < len; i++) {
                // 递归调用del方法并取得子目录路径
                if (delFile[i].isDirectory()) {
                    delExtFile(delFile[i].getAbsolutePath(), ext);
                } else {
                    // 判断扩展名进行删除
                    if (delFile[i] != null && !StringUtils.isEmpty(delFile[i].getName()) && delFile[i].getName().endsWith(ext)) {
                        delFile[i].delete();//删除文件
                    }
                }
            }
        }
    }



    public static void main(String[] args) {
        String path = ClassLoader.getSystemResource("").getPath();

        System.out.println("abc".lastIndexOf("a"));

        try {
            System.out.println( ClassLoader.getSystemResource(".").getPath());
            System.out.println(new File("").getCanonicalFile());
            System.out.println(new File("").getAbsolutePath());


            System.out.println(listFileDirector(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
