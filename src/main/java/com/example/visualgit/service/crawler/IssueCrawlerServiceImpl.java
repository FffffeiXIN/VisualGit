package com.example.visualgit.service.crawler;

import com.example.visualgit.entity.Result;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class IssueCrawlerServiceImpl extends AbstractCrawlerService {
    @Override
    public Result doCrawl(JsonObject object) {
        int rank = object.get("id").getAsInt();
        String state = object.get("state").getAsString();
        String open_time = object.get("created_at").getAsString();
        JsonElement jsonObject = object.get("closed_at");
        String close_time;
        if (state.equals("open")) {
            close_time = "";
        } else {
            close_time = jsonObject.getAsString();
        }
        mapper.insertIssue(rank, repository, state, open_time, close_time);
        return Result.ok();
    }
}
