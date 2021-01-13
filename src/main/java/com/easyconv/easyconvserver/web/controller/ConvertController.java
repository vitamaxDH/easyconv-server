package com.easyconv.easyconvserver.web.controller;

import com.easyconv.easyconvserver.config.ConfigUtil;
import com.easyconv.easyconvserver.core.conversion.service.ToPdfConverService;
import com.easyconv.easyconvserver.core.conversion.service.FromPdfConvertService;
import com.easyconv.easyconvserver.core.resource.ResourceFile;
import com.easyconv.easyconvserver.core.util.FileUtils;
import com.easyconv.easyconvserver.core.util.GsonUtils;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/convert")
public class ConvertController {

    private final FromPdfConvertService fromPdfConvertService;
    private final ToPdfConverService toPdfConverService;
    private final ConfigUtil configUtil;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> test(HttpServletRequest req, HttpServletResponse res, MultipartFile multipartFile) throws IOException, DocumentException {

        log.info("type -> {}", FileUtils.tika.detect(multipartFile.getInputStream()));
//        log.info("propertiesConfig.key -> {}", configUtil.getKey());
        fromPdfConvertService.toImage(multipartFile);
//        htmlToPdfConverService.convert(multipartFile);
        return ResponseEntity.ok("OK");
    }

    @PostMapping({"","/"})
    public String convert(@RequestBody String body, HttpServletRequest req, HttpServletResponse res) throws IOException, ParserConfigurationException {

        ResourceFile resourceFile = GsonUtils.gson.fromJson(body, ResourceFile.class);

        fromPdfConvertService.convert(resourceFile.getFilePath(), "D:/sample/output/test.html");

        log.info("convert :: filePath -> {}", resourceFile.getFilePath());
        return resourceFile.getFilePath();
    }

}
