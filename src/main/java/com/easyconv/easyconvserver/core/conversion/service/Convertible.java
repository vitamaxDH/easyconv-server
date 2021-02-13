package com.easyconv.easyconvserver.core.conversion.service;

import org.springframework.web.multipart.MultipartFile;

public interface Convertible {
    void convert(MultipartFile inputFile) throws Exception;
    void convert(String inputFilePath) throws Exception;
    void convert(String inputFilePath, String outputFilePath) throws Exception;
}
