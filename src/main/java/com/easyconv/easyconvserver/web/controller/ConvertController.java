package com.easyconv.easyconvserver.web.controller;

import com.easyconv.easyconvserver.core.conversion.service.PdfToHtmlConvertService;
import com.easyconv.easyconvserver.core.resource.ResourceFile;
import com.easyconv.easyconvserver.core.util.GsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/convert")
public class ConvertController {

    private final PdfToHtmlConvertService pdfToHtmlConvertService;

    @PostMapping({"","/"})
    public String convert(@RequestBody String body, HttpServletRequest req, HttpServletResponse res) throws IOException, ParserConfigurationException {

        ResourceFile resourceFile = GsonUtil.gson.fromJson(body, ResourceFile.class);

        pdfToHtmlConvertService.convert(resourceFile.getFilePath(), "D:/sample/output/test.html");

        log.info("convert :: filePath -> {}", resourceFile.getFilePath());
        return resourceFile.getFilePath();
    }

}
