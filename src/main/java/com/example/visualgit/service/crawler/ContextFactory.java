package com.example.visualgit.service.crawler;

public class ContextFactory {

    public static ContextService getContext(String url){
        if(url.endsWith("issues")) return new ContextService(new IssueCrawlerServiceImpl());
        if(url.endsWith("commits")) return new ContextService(new CommitCrawlerServiceImpl());
        if(url.endsWith("contributors")) return new ContextService(new ContributorCrawlerServiceImpl());
        if(url.endsWith("releases")) return new ContextService(new ReleaseCrawlerServiceImpl());
        return null;
    }



}
