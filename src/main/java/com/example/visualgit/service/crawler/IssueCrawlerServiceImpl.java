package com.example.visualgit.service.crawler;

import com.example.visualgit.entity.Result;
import com.google.gson.JsonObject;

public class IssueCrawlerServiceImpl extends AbstractCrawlerService{
    @Override
    public Result doCrawl(JsonObject object) {
        int rank = object.get("id").getAsInt();
        String url = object.get("url").getAsString();
        String state = object.get("state").getAsString();
        String open_time = object.get("created_at").getAsString();
        String close_time = object.get("closed_at").getAsString();
        mapper.insertIssue(rank,url,state,open_time,close_time);
        return Result.ok();
    }
}
