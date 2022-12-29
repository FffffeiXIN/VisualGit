package com.example.visualgit.service.crawler;

import com.example.visualgit.entity.Result;
import com.google.gson.JsonObject;

public class ContributorCrawlerServiceImpl extends AbstractCrawlerService {
    @Override
    public Result doCrawl(JsonObject object) {
        int id = object.get("id").getAsInt();
        String repos_id = repository;
        int submission = object.get("contributions").getAsInt();
        mapper.insertDeveloper(repos_id, id, submission);
        return Result.ok();
    }
}
