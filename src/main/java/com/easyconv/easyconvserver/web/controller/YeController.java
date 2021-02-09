package com.easyconv.easyconvserver.web.controller;

import com.easyconv.easyconvserver.crawler.entity.CrawlerEntity;
import com.easyconv.easyconvserver.crawler.service.CrawlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/yeahn")
public class YeController {

    private final CrawlerService crawlerService;

    @GetMapping("/index")
    public String yeahn(){
        return "server is onnnnnn";
    }

    @GetMapping("/crawl")
    public String crawl(Model model) throws IOException {
        List<CrawlerEntity> crawlerEntityList = crawlerService.getOpentutorialsData();

        model.addAttribute("crawlerEntityList", crawlerEntityList);

        return "crawl";
    }
}
