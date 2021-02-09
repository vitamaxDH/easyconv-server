package com.easyconv.easyconvserver.crawler.service;

import com.easyconv.easyconvserver.crawler.entity.CrawlerEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerServiceTest {

    @Autowired
    CrawlerService crawlerService;

    @Test
    public void getOpentutorialsData_동작테스트() throws IOException {

        // given
        List<CrawlerEntity> crawlerEntityList = new ArrayList<>();

        // when
        crawlerEntityList =crawlerService.getOpentutorialsData();

        // then
        assertThat(crawlerEntityList.get(0).getMeunName()).isEqualTo("WEB");
        assertThat(crawlerEntityList.get(0).getMeunHref()).isEqualTo("https://opentutorials.org/course/3083");
    }
}
