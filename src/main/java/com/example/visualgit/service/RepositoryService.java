package com.example.visualgit.service;

import com.example.visualgit.entity.Developer;
import com.example.visualgit.entity.Issue;
import com.example.visualgit.entity.Repository;
import com.example.visualgit.entity.Result;
import com.example.visualgit.exception.DataBaseException;
import com.example.visualgit.mapper.RepositoryMapper;
import com.example.visualgit.utils.MathUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

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

    public Result showIssue(String state,String repos_id){
        List<Issue> list=mapper.selectIssueByState(state,repos_id);
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

    public Result dealData() throws IOException {
        String s = "https://pokeapi.co/api/v2/pokemon/pikachu/";
//        String s = "https://api.github.com/repos/msgpack/msgpack-c/issues";
        URL url = new URL(s);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        InputStream stream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
//            System.out.println(line);
//        }
            stringBuilder.append(line);
        }
        String json = stringBuilder.toString();
        JsonParser jsonParser = new JsonParser();
        JsonObject object = (JsonObject) jsonParser.parse(json);
//    Name: pikachu
//    Height: xxx
//    Weight: xxx
//    Abilities: [xxx, xxx, ...]
        if(s.endsWith("commits")){

        }

//        System.out.println("Name: " + object.get("name").getAsString());
//        System.out.println("Height: " + object.get("height").getAsString());
//        System.out.println("Weight: " + object.get("weight").getAsString());
        JsonArray array = object.get("abilities").getAsJsonArray();
        ArrayList<String> arrayList = new ArrayList<>();
        for (JsonElement i : array) {
            String ab = i.getAsJsonObject().get("ability").getAsJsonObject().get("name").getAsString();
            arrayList.add(ab);
        }
        System.out.println("Abilities:"+ arrayList.toString());
        return Result.ok().code(200);
    }
}
