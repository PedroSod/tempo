package com.ecore.tempo.helper;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.util.ResourceUtils;

public class FileReader {

    private FileReader(){}
    public static String readFile(String relativeFilePath) throws IOException {
        return Files.readString(ResourceUtils.getFile(
            "classpath:" + relativeFilePath).toPath());
    }

}
