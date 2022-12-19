package com.example.visualgit.service.crawler;

import com.example.visualgit.entity.Result;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;


public class CommitCrawlerServiceImpl extends AbstractCrawlerService{
    @Override
    public Result doCrawl(JsonObject object) {
        String id = object.get("url").getAsString();
        String time = object.getAsJsonObject("commit").getAsJsonObject("committer").get("date").getAsString();
        mapper.insertCommit(id,time,repository);
        return Result.ok();
    }
}
