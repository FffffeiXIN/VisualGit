package com.example.visualgit.controller;
import com.example.visualgit.entity.Result;
import com.example.visualgit.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("repository")
public class RepositoryController {

    @Autowired
    RepositoryService service;
    @GetMapping
    public Result getDeveloperQuantityByRepositoryId(int id){
        return service.getDeveloperQuantity(id);
    }

    public Result getDeveloperRankByRepositoryId(int id){
        return service.getMostActiveDeveloper(id);
    }
    public Result getOpenIssue(){
        return service.showIssue("open");
    }
    public Result getCloseIssue(){
        return service.showIssue("close");
    }


}
