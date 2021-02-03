package com.easyconv.easyconvserver.core.util;

import com.easyconv.easyconvserver.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.io.FilenameUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Slf4j
public class FileUtils {

    public static final Tika TIKA = new Tika();
    private static final String FORMAT_YYYYMMDD = "yyyy/MM/dd";

    public static File createFile(String filePath) throws IOException {
        return createFile(filePath, false);
    }

    public static File createFile(String filePath, boolean fromToday) throws IOException {
        String tempPath = getBasePath();
        Objects.requireNonNull(filePath);
        File newFile = fromToday ? createFileToday(FilenameUtils.getName(filePath)) : new File(tempPath, filePath);
        if (!newFile.exists()){
            Files.createDirectories(newFile.getParentFile().toPath());
        }
        return newFile;
    }

    public static File createFileToday(String fileName){
        String basePath = getBasePath();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYYMMDD);
        return new File(basePath + sdf.format(new Date()), fileName);
    }

    public static byte[] readFileToByteArray(final File file) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToByteArray(file);
    }

    private static String getBasePath(){
        return Config.getProperty("com.easyconv.pdf.file.output");
    }

    public static boolean isExcel(String extension){
        if (!StringUtils.hasLength(extension)){
            log.warn("isExcel :: the given extension is null");
            return false;
        }
        return extension.contains(ExtensionType.XLS.getValue());
    }
}
