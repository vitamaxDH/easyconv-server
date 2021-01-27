package com.easyconv.easyconvserver.web.controller;

import com.easyconv.easyconvserver.core.conversion.service.PdfConvertService;
import com.easyconv.easyconvserver.core.dto.GenericResourceDto;
import com.easyconv.easyconvserver.core.util.WebUtils;
import com.itextpdf.text.DocumentException;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class IndexController {

    private final PdfConvertService pdfConvertService;

    @GetMapping("/index")
    public String index(){
        return "인덱스 페이지 입니다.";
    }

    @PostMapping(value = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/octet-stream; charset=utf-8")
    public ResponseEntity<GenericResourceDto> convertAndSend(@NotNull MultipartFile multipartFile, HttpServletRequest req, HttpServletResponse res) throws IOException, DocumentException {
        GenericResourceDto dto = GenericResourceDto.create()
                                                    .setMultipartFile(multipartFile)
                                                    .setIp(WebUtils.getIp(req));
        dto = pdfConvertService.convert(dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/file/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable Long fileId, HttpServletRequest req, HttpServletResponse res){


        return null;
    }
}
