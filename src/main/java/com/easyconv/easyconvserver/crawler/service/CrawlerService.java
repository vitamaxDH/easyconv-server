package com.easyconv.easyconvserver.crawler.service;

import com.easyconv.easyconvserver.crawler.entity.CrawlerEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CrawlerService {
    private static String OPENTUTORIALS_URL = "http://opentutorials.org/course/1";

    @PostConstruct
    public List<CrawlerEntity> getOpentutorialsData() throws IOException {

        List<CrawlerEntity> crawlerList = new ArrayList<>();
        Document doc = Jsoup.connect(OPENTUTORIALS_URL).get();
        Elements contents = doc.select("ul > li > div.public");

        for(Element content : contents){
            Elements hrefContents = content.select("a");

            CrawlerEntity crawlerEntity = CrawlerEntity.builder()
                .MeunName(hrefContents.get(0).text())
                .MeunHref(hrefContents.attr("href"))
                .build();

            crawlerList.add(crawlerEntity);

            //String html = Jsoup.connect(hrefContents.attr("href")).get().html();
            //System.out.println(html);
        }

        return crawlerList;
    }
}