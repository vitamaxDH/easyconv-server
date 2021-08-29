package com.easyconv.easyconvserver.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.tika.Tika;
import org.apache.tika.io.FilenameUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class FileUtils {

    public static final Tika TIKA = new Tika();
    private static final String YYYYMMDD_FORMAT = "yyyy/MM/dd";
    public static final DateTimeFormatter YYYYMMDD_FORMATTER = DateTimeFormatter.ofPattern(YYYYMMDD_FORMAT);
    
    public static File createFile(String filePath) throws IOException {
        return createFile(filePath, false);
    }

    public static File createFile(String filePath, boolean fromToday) throws IOException {
        String baseDir = getBasePath();
        Objects.requireNonNull(filePath);
        File newFile = fromToday ? createFileToday(FilenameUtils.getName(filePath)) : new File(baseDir, filePath);
        if (!newFile.exists()) {
            Files.createDirectories(newFile.getParentFile().toPath());
        }
        return newFile;
    }

    public static File createFileToday(String fileName) {
        String basePath = getBasePath();
        return new File(basePath + YYYYMMDD_FORMATTER.format(LocalDateTime.now()), fileName);
    }

    public static byte[] readFileToByteArray(final File file) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToByteArray(file);
    }

    public static File getOutputFile(String outputPath, String fileName) throws IOException {
        fileName = FileNameUtils.getBaseName(fileName);
        if (ObjectUtils.isEmpty(outputPath)) {
            return createFile(fileName + ExtensionType.PDF_WITH_DOT.getValue(), Boolean.TRUE);
        } else {
            return createFile(outputPath);
        }
    }

    private static String getBasePath() {
        return ConfigUtils.getProperty("com.easyconv.pdf.file.output");
    }

    public static File multiFileToFile(MultipartFile multipartFile) throws IOException {
        return multiFileToFile(multipartFile, null);
    }

    public static File multiFileToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File convFile;
        if (!StringUtils.hasLength(fileName)) {
            convFile = new File(getBasePath(), UUID.randomUUID().toString());
        } else {
            convFile = createFileToday(fileName);
        }
        multipartFile.transferTo(convFile.toPath());
        return convFile;
    }

    public static boolean isExcel(String extension) {
        if (ObjectUtils.isEmpty(extension)) {
            log.warn("isExcel :: the given extension is null");
            return false;
        }
        return extension.contains(ExtensionType.XLS.getValue());
    }

    public static boolean isXls(String extension) {
        if (ObjectUtils.isEmpty(extension)) {
            log.warn("isXls :: the given extension is null");
            return false;
        }
        return extension.endsWith(ExtensionType.XLS.getValue());
    }

    public static boolean isXlsx(String extension) {
        if (ObjectUtils.isEmpty(extension)) {
            log.warn("isXlsx :: the given extension is null");
            return false;
        }
        return extension.endsWith(ExtensionType.XLSX.getValue());
    }
}
