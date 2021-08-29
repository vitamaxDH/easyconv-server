package com.easyconv.easyconvserver.core.domain.service.word;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.easyconv.easyconvserver.core.domain.service.Convertible;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Slf4j
@Service
public class WordConverter implements Convertible {

    @Override
    public String convert(InputStream is, File outputFile) throws Exception {
        String outputPath = outputFile.getAbsolutePath();
        Document document = new Document(is);
        document.save(outputPath, SaveFormat.PDF);
        log.info("convert :: outputPath : {}", outputPath);
        return outputPath;
    }
}
