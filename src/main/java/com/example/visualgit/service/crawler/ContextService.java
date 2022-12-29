package com.example.visualgit.service.crawler;

import java.io.IOException;

public class ContextService {
    CrawlerService crawlerService;

    public ContextService(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    public void doCrawl(String url) throws IOException {
        crawlerService.crawlData(url);
    }

}
