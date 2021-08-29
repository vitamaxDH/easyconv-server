package com.easyconv.easyconvserver.core.domain.service.slide;

import com.aspose.slides.Presentation;
import com.aspose.slides.SaveFormat;
import com.easyconv.easyconvserver.core.domain.service.Convertible;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Slf4j
@Service
@Scope("prototype")
public class SlidesConverter implements Convertible {

    @Override
    public String convert(InputStream is, File outputFile) {
        Presentation prest = new Presentation(is);
        String outputPath = outputFile.getAbsolutePath();
        prest.save(outputPath, SaveFormat.Pdf);
        log.info("convert :: outputPath : {}", outputPath);
        return outputPath;
    }
}
