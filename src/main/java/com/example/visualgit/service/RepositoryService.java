package com.example.visualgit.service;

import com.example.visualgit.entity.Developer;
import com.example.visualgit.entity.Issue;
import com.example.visualgit.entity.Repository;
import com.example.visualgit.entity.Result;
import com.example.visualgit.exception.DataBaseException;
import com.example.visualgit.mapper.RepositoryMapper;
import com.example.visualgit.utils.MathCalculator;
import com.example.visualgit.utils.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class RepositoryService {

    @Autowired
    RepositoryMapper mapper;
    public Result getRepository(int id){
        Repository repository = mapper.selectById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("repository",repository);

        if(repository==null) throw new DataBaseException();
        else return Result.ok().code(200).data(map);
    }

    public Result getDeveloperQuantity(int id){
        List<Developer> list = mapper.selectDeveloperByRepositoryId(id);
        if(list==null || list.isEmpty()) throw new DataBaseException();
        Map<String,Object> data = new HashMap<>();
        data.put("quantity",list.size());
        return Result.ok().code(200).data(data);
    }

    public Result getMostActiveDeveloper(int id){
        List<Developer> list = mapper.selectDeveloperByRepositoryId(id);
        if(list==null || list.isEmpty()) throw new DataBaseException();
        list.sort((x,y)->y.getSubmission()-x.getSubmission());
        Map<String, Object> map=new HashMap<>();
        map.put("rank_list",list);
        return Result.ok().code(200).data(map);
    }

    public Result showIssue(String state){
        List<Issue> list=mapper.selectIssueByState(state);
        if(list==null || list.isEmpty()) throw new DataBaseException();
        Map<String,Object> map=new HashMap<>();
        map.put("issue",list);
        map.put("quantity",list.size());
        if(state.equals("close")){
            double avg = MathUtils.getAvg(list, obj -> obj.getEndTime()-obj.getStartTime());
            double standardDeviation = MathUtils.getStandardDeviation(list, obj -> obj.getEndTime()-obj.getStartTime());
            double range = MathUtils.getRange(list,obj -> obj.getEndTime()-obj.getStartTime());
            map.put("average",avg);
            map.put("standardDeviation",standardDeviation);
            map.put("range",range);
        }
        map.put("average",-1);
        map.put("standardDeviation",-1);
        map.put("range",-1);
        return Result.ok().code(200).data(map);
    }

    public Result getSubmissionByRepositoryDeveloper(int id,String name){
        List<Integer> list =mapper.selectSubmissionRepositoryDeveloper(id,name);
        if(list==null || list.isEmpty()) throw new DataBaseException();
        Map<String,Object> map=new HashMap<>();
        map.put("submission",list);
        return Result.ok().code(200).data(map);
    }

}
