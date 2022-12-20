package com.example.visualgit.controller;
import com.example.visualgit.entity.Result;
import com.example.visualgit.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("repository")
public class RepositoryController {

    @Autowired
    RepositoryService service;

    @GetMapping("/developer_quantity")
    public Result getDeveloperQuantityByRepositoryId(String id){
        return service.getDeveloperQuantity(id);
    }

    @GetMapping("developer_rank")
    public Result getDeveloperRankByRepositoryId(String id){
        return service.getMostActiveDeveloper(id);
    }

    @GetMapping("openissue")
    public Result getOpenIssue(String repos) throws ParseException {
        return service.showIssue("open",repos);
    }

    @GetMapping("closeissue")
    public Result getCloseIssue(String repos) throws ParseException {
        return service.showIssue("close",repos);
    }

    @GetMapping("release")
    public Result showReleaseCommission(String id){
        return service.showReleaseCommission(id);
    }

    @GetMapping("analyseCommitByDay")
    public Result analyseCommitByDay(String id){
        return service.analyseCommitByDay(id);
    }

    @GetMapping("analyseCommitByWeek")
    public Result analyseCommitByWeek(String id){
        return service.analyseCommitByWeek(id);
    }

    @GetMapping("getAllRepo")
    public Result getAllRepo(){
        return service.getAllRepo();
    }

    @GetMapping("analyseCommitByHour")
    public Result analyseCommitByHour(String id){
        return service.analyseCommitByHour(id);
    }

}
