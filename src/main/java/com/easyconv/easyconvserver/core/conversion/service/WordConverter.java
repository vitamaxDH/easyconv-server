package com.easyconv.easyconvserver.core.conversion.service;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.easyconv.easyconvserver.core.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Slf4j
@Service
@Scope("prototype")
public class WordConverter implements Convertible {

    @Override
    public String convert(InputStream is, File outputFile) throws Exception {
        String outputPath = outputFile.getAbsolutePath();
        Document document = new Document(is);
        document.save(outputPath, SaveFormat.PDF);
        log.info("convert :: outputPath : {}" ,outputPath);
        return outputPath;
    }
}
