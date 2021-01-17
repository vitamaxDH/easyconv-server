package com.easyconv.easyconvserver.web.controller;

import com.easyconv.easyconvserver.core.conversion.service.PdfConvertService;
import com.itextpdf.text.DocumentException;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/convert")
public class ConvertController {

    private final PdfConvertService pdfConvertService;

    @PostMapping(value = {"", "/"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<? extends Resource> convertAndSend(@NotNull MultipartFile multipartFile, HttpServletRequest req, HttpServletResponse res) throws IOException, DocumentException {
        return pdfConvertService.getByteArrayResource(multipartFile);
    }

}
