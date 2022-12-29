package com.example.visualgit.controller;

import com.example.visualgit.entity.*;
import com.example.visualgit.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/repository")
public class RestfulAPIController {
    @Autowired
    RepositoryService service;

    @GetMapping("/{owner}/{repos}/developer_ranklist")
    public HashMap<String, Developer> getDeveloperQuantityByRepositoryId(@PathVariable(value = "owner") String owner, @PathVariable(value = "repos") String repos) {
        String id = "https://api.github.com/repos/" + owner + "/" + repos;
//        System.out.println(id);
        return service.getRestMostActiveDeveloper(id);
    }

    @GetMapping("/{owner}/{repos}/issues")
    public HashMap<String, List<Issue>> getIssuesByRepositoryId(@PathVariable(value = "owner") String owner, @PathVariable(value = "repos") String repos) {
        String id = "https://api.github.com/repos/" + owner + "/" + repos;
//        System.out.println(id);
        HashMap<String, List<Issue>> res = new LinkedHashMap<>();
        List<Issue> open = service.getRestIssues("open", id);
        List<Issue> close = service.getRestIssues("close", id);
        res.put("open issues", open);
        res.put("close issues", close);
        return res;
    }

    @GetMapping("/{owner}/{repos}/release_commit")
    public Map<Release, List<Commit>> getReleaseInfoByRepositoryId(@PathVariable(value = "owner") String owner, @PathVariable(value = "repos") String repos) {
        String id = "https://api.github.com/repos/" + owner + "/" + repos;
//        System.out.println(id);
        return service.showRestReleaseCommission(id);
    }
}
