package com.easyconv.easyconvserver.web.controller;

import com.easyconv.easyconvserver.core.domain.service.pdf.PdfConvertService;
import com.easyconv.easyconvserver.core.dto.BaseResourceDto;
import com.easyconv.easyconvserver.core.util.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class IndexController extends BaseController {

    private final PdfConvertService pdfConvertService;

    @GetMapping("/index")
    public ResponseEntity<Boolean> index(){
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PostMapping(value = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> convert(@RequestBody MultipartFile multipartFile, HttpServletRequest req, HttpServletResponse res) throws Throwable {
        log.info("convertAndSend :: multipartFile {}", multipartFile);

        if (ObjectUtils.isEmpty(multipartFile)){
            return ResponseEntity.badRequest().body(null);
        } else {
            BaseResourceDto dto = BaseResourceDto.builder()
                                                    .multipartFile(multipartFile)
                                                    .ip(WebUtils.getIp(req))
                                                    .build();
            pdfConvertService.convert(dto);
            return ResponseEntity.ok("OK");
        }
    }

    @GetMapping(value = "/file/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable Long fileId, HttpServletRequest req, HttpServletResponse res){

        return null;
    }
}
