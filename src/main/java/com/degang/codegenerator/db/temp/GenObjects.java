package com.degang.codegenerator.db.temp;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by degang on 2018/12/4
 */
@Slf4j
public class GenObjects {
    public static void main(String[] args) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        BufferedWriter bufferedWriter = null;
        try {
            Path templateDir = Paths.get("src/main/java/com/degang/codegenerator/db/template");
            cfg.setDirectoryForTemplateLoading(templateDir.toFile());
            cfg.setDefaultEncoding("UTF-8");

            Template template = cfg.getTemplate("model.ftl");

            Map<String, Object> root = new HashMap<>();
            String className = "Test";
            root.put("package", "com.degang.codegenerator.db.temp");
            root.put("className", className);

            Path targetPathDir = templateDir.resolveSibling("temp");
            if (!Files.exists(targetPathDir)) {
                Files.createDirectories(targetPathDir);
            }

            Path targetFile = targetPathDir.resolve(className + ".java");
            if (!Files.exists(targetFile)) {
                Files.createFile(targetFile);
            }
            bufferedWriter = Files.newBufferedWriter(targetFile);
            try {
                template.process(root, bufferedWriter);
            } catch (TemplateException e) {
                log.error("", e);
            }

            bufferedWriter.flush();

        } catch (IOException e) {
            log.error("", e);
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }

    }
}
