package com.example.visualgit.service.crawler;

public class ContextFactory {

    public static ContextService getContext(String url) {
        if (url.contains("issues")) {
            return new ContextService(new IssueCrawlerServiceImpl());
        }
        if (url.contains("commits")) {
            return new ContextService(new CommitCrawlerServiceImpl());
        }
        if (url.contains("contributors")) {
            return new ContextService(new ContributorCrawlerServiceImpl());
        }
        if (url.contains("releases")) {
            return new ContextService(new ReleaseCrawlerServiceImpl());
        }
        return null;
    }


}
