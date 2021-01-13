package com.easyconv.easyconvserver.core.conversion.service;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public abstract class Converter {

    public abstract void convert(File file) throws DocumentException, IOException;
    public abstract void convert(MultipartFile MultipartFile) throws DocumentException, IOException;
    public abstract void convert(String input, String output) throws IOException, ParserConfigurationException;

}
