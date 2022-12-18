package com.example.visualgit.controller;

import com.example.visualgit.entity.Result;
import com.example.visualgit.service.crawler.CommitCrawlerServiceImpl;
import com.example.visualgit.service.crawler.ContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("crawl")
public class CrawlerController {

    @GetMapping("/do_crawl")
    public Result crawlData(String url) throws IOException {
        Objects.requireNonNull(ContextFactory.getContext(url)).doCrawl(url);
        return Result.ok().code(200);
    }

}
