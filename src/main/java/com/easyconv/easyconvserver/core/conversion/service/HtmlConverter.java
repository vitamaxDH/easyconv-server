package com.easyconv.easyconvserver.core.conversion.service;

import com.aspose.html.HTMLDocument;
import com.aspose.html.rendering.HtmlRenderer;
import com.aspose.html.rendering.pdf.PdfDevice;
import com.aspose.html.rendering.pdf.PdfRenderingOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Slf4j
@Service
@Scope("prototype")
public class HtmlConverter implements Convertible {

    @Override
    public String convert(InputStream is, File outputFile) {
        String outputPath = outputFile.getAbsolutePath();
        HTMLDocument html = new HTMLDocument("https://naver.com");
        HtmlRenderer renderer = new HtmlRenderer();
        renderer.render(new PdfDevice(new PdfRenderingOptions(), outputPath), html);
        return outputPath;
    }
}
