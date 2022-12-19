package com.example.visualgit.service.crawler;

import com.example.visualgit.entity.Result;

import java.io.IOException;
import java.net.MalformedURLException;

public interface CrawlerService {

    Result crawlData(String s) throws MalformedURLException, IOException;

}
