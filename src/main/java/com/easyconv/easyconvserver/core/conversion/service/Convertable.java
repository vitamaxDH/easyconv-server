package com.easyconv.easyconvserver.core.conversion.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface Convertable {

    public void convert(File file) throws IOException;
    public void convert(MultipartFile MultipartFile) throws IOException;
    public void convert(String input, String output) throws IOException;
}
