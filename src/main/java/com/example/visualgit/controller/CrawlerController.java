package com.example.visualgit.controller;

import com.example.visualgit.entity.Result;
import com.example.visualgit.service.crawler.ContextFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Objects;

@Controller
@CrossOrigin
@RequestMapping("crawl")
public class CrawlerController {

    public static boolean isFinished = false;
    @GetMapping("/do_crawl")
    public Result crawlData(String url) throws IOException {
        String s = url;
        int k=0;
        while (!isFinished) {
            k=k+1;
            if(url.contains("issues")) {
                url = s + "?per_page=100&state=all&page=" + k;
            }else {
                url = s + "?per_page=100&page=" + k;
            }
            Objects.requireNonNull(ContextFactory.getContext(url)).doCrawl(url);
        }
        isFinished=false;
        return Result.ok().code(200);
    }

}
