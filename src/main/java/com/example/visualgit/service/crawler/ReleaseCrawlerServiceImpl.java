package com.example.visualgit.service.crawler;

import com.example.visualgit.entity.Result;
import com.google.gson.JsonObject;

public class ReleaseCrawlerServiceImpl extends AbstractCrawlerService {
    @Override
    public Result doCrawl(JsonObject object) {
        String id = object.get("url").getAsString();
        String time = object.get("created_at").getAsString();
        mapper.insertRelease(id, time, repository);
        return Result.ok().code(200);
    }
}
