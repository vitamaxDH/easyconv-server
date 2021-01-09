package com.easyconv.easyconvserver.web.controller;

import com.easyconv.easyconvserver.core.conversion.service.PdfToHtmlConvertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class IndexController {


    @GetMapping("/index")
    public String index(){
        return "인덱스 페이지 입니다.";
    }


}
