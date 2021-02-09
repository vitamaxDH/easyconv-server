package com.easyconv.easyconvserver.crawler.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@Getter
public class CrawlerEntity {

    private String MeunName; //href
    private String MeunHref; //href
}
