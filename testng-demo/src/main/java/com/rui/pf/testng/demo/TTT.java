package com.rui.pf.testng.demo;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;

import java.io.File;
import java.util.List;

/**
 * @author wuchengrui
 * @Description: \\TODO
 * @date 2020/6/23 15:53
 */
public class TTT {

    private static String path = "/Users/crwu/IdeaProjects/tools-dev-collection/tools-testng";
    private static String replacePath = "/Users/crwu/IdeaProjects/tools-dev-collection/tools-testng";
    private static String targetPath = "/Users/crwu/tools-testng";

    public static void main(String[] args) {

        List<File> files = FileUtil.loopFiles(path);
        files.forEach(file->{
            String str = FileReader.create(file).readString();
            String s = str.replaceAll("rui", "rui");
            String replace = file.getAbsolutePath().replace(replacePath, targetPath).replace("rui","rui");
            FileWriter.create(new File(replace)).write(s);
        });

    }

}
