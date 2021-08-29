package com.easyconv.easyconvserver.core.domain.service;

import com.easyconv.easyconvserver.core.util.FileUtils;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public interface Convertible {

    default void convert(MultipartFile inputFile) throws Exception {
        File outputFile = FileUtils.getOutputFile(null, inputFile.getOriginalFilename());
        String pdfPath = convert(inputFile.getInputStream(), outputFile);
    }

    default String convert(String inputFilePath, String outputFilePath) throws Exception {
        String fileName = FileNameUtils.getBaseName(inputFilePath);
        File outputFile = FileUtils.getOutputFile(null, fileName);
        return convert(new FileInputStream(fileName), outputFile);
    }

    String convert(InputStream is, File outputFile) throws Exception;

}
