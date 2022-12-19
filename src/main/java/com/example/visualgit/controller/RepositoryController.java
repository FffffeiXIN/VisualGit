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
    @GetMapping("/developer_quantity")
    public Result getDeveloperQuantityByRepositoryId(String id){
        return service.getDeveloperQuantity(id);
    }

    @GetMapping("developer_rank")
    public Result getDeveloperRankByRepositoryId(String id){
        return service.getMostActiveDeveloper(id);
    }

    @GetMapping("openissue")
    public Result getOpenIssue(String repos){
        return service.showIssue("open",repos);
    }

    @GetMapping("closeissue")
    public Result getCloseIssue(String repos){
        return service.showIssue("close",repos);
    }
}
