package com.example.visualgit.service;

import com.example.visualgit.entity.*;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public Result getDeveloperQuantity(String id){
        List<Developer> list = mapper.selectDeveloperByRepositoryId(id);
        if(list==null || list.isEmpty()) throw new DataBaseException();
        Map<String,Object> data = new HashMap<>();
        data.put("quantity",list.size());
        return Result.ok().code(200).data(data);
    }
    public HashMap<String,Developer> getRestMostActiveDeveloper(String id){
        List<Developer> list = mapper.selectDeveloperByRepositoryId(id);
        if(list==null || list.isEmpty()) throw new DataBaseException();
        list.sort((x,y)->y.getSubmission()-x.getSubmission());
        HashMap<String,Developer> res = new LinkedHashMap<>();
        for (int i = 0; i < list.size(); i++) {
            res.put("rank "+i,list.get(i));
        }
        return res;
    }

    public Result getMostActiveDeveloper(String id){
        List<Developer> list = mapper.selectDeveloperByRepositoryId(id);
        if(list==null || list.isEmpty()) throw new DataBaseException();
        list.sort((x,y)->y.getSubmission()-x.getSubmission());
        Map<String, Object> map=new HashMap<>();
        map.put("rank_list",list);
        return Result.ok().code(200).data(map);
    }

    public Result showIssue(String state,String repos_id) throws ParseException {
        List<Issue> list=mapper.selectIssueByState(state,repos_id);
        if(list==null) throw new DataBaseException();
        Map<String,Object> map=new HashMap<>();
        map.put("issue",list);
        map.put("quantity",list.size());
        DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        if(state.equals("open")){
            double avg = MathUtils.getAvg(list, obj -> {
                Date start = dateFormat.parse(MathUtils.dealDate(obj.getOpen_time()));
                Date end = new Date(System.currentTimeMillis());
                return (int) (end.getTime() - start.getTime());
            });
            double standardDeviation = MathUtils.getStandardDeviation(list, obj -> {
                Date start = dateFormat.parse(MathUtils.dealDate(obj.getOpen_time()));
                Date end = new Date(System.currentTimeMillis());
                return (int) (end.getTime() - start.getTime());
            });
            double range = MathUtils.getRange(list,obj -> {
                Date start = dateFormat.parse(MathUtils.dealDate(obj.getOpen_time()));
                Date end = new Date(System.currentTimeMillis());
                return (int) (end.getTime() - start.getTime());
            });
            map.put("average",avg);
            map.put("standardDeviation",standardDeviation);
            map.put("range",range);
        }else {
            double avg = MathUtils.getAvg(list, obj -> {
                Date start = dateFormat.parse(MathUtils.dealDate(obj.getOpen_time()));
                Date end = dateFormat.parse(MathUtils.dealDate(obj.getClose_time()));
                return (int) (end.getTime() - start.getTime());
            });
            double standardDeviation = MathUtils.getStandardDeviation(list, obj -> {
                Date start = dateFormat.parse(MathUtils.dealDate(obj.getOpen_time()));
                Date end = dateFormat.parse(MathUtils.dealDate(obj.getClose_time()));
                return (int) (end.getTime() - start.getTime());
            });
            double range = MathUtils.getRange(list,obj -> {
                Date start = dateFormat.parse(MathUtils.dealDate(obj.getOpen_time()));
                Date end = dateFormat.parse(MathUtils.dealDate(obj.getClose_time()));
                return (int) (end.getTime() - start.getTime());
            });
            map.put("average",avg);
            map.put("standardDeviation",standardDeviation);
            map.put("range",range);
        }
        return Result.ok().code(200).data(map);
    }

    public List<Issue> getRestIssues(String state,String repos_id){
        return mapper.selectIssueByState(state,repos_id);
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

    public Result showReleaseCommission(){
        List<Release> releases = mapper.selectRelease();
        List<Commit> commits = mapper.selectCommit();
        List<List<Commit>> res = new ArrayList<>();
        MathUtils.sort(releases);
//        List<List<Commit>> list = new LinkedList<>();
        long start = 0;
        long end = 0;
        for(int i=0;i<releases.size();i++){
            end = Long.parseLong(MathUtils.dealDate(releases.get(i).getRelease_time()));
            List<Commit> temp = new LinkedList<>();
            for(Commit commit:commits) {
                if (Long.parseLong(MathUtils.dealDate(commit.getCommit_time())) <= end && Long.parseLong(MathUtils.dealDate(commit.getCommit_time())) > start) {
                    temp.add(commit);
                }
            }
            MathUtils.sort(temp,1);
//            list.add(temp);
            start = end;
            res.add(temp);
        }
//        List<Commit> temp = new LinkedList<>();
//        for(Commit commit:commits) {
//            if (Long.parseLong(MathUtils.dealDate(commit.getCommit_time())) > end) {
//                temp.add(commit);
//            }
//        }
//        list.add(temp);

        Map<String,Object> map=new HashMap<>();
        map.put("release", res);
//        map.put("commits",list);
        return Result.ok().code(200).data(map);
    }

    public Map<Release,List<Commit>> showRestReleaseCommission(){
        List<Release> releases = mapper.selectRelease();
        List<Commit> commits = mapper.selectCommit();
        MathUtils.sort(releases);
        List<List<Commit>> list = new LinkedList<>();
        long start = 0;
        long end = 0;
        for(Release release:releases){
            end = Long.parseLong(MathUtils.dealDate(release.getRelease_time()));
            List<Commit> temp = new LinkedList<>();
            for(Commit commit:commits) {
                if (Long.parseLong(MathUtils.dealDate(commit.getCommit_time())) <= end && Long.parseLong(MathUtils.dealDate(commit.getCommit_time())) > start) {
                    temp.add(commit);
                }
            }
            MathUtils.sort(temp,1);
            list.add(temp);
            start = end;
        }
        List<Commit> temp = new LinkedList<>();
        for(Commit commit:commits) {
            if (Long.parseLong(MathUtils.dealDate(commit.getCommit_time())) > end) {
                temp.add(commit);
            }
        }
        list.add(temp);

        Map<Release,List<Commit>> map=new LinkedHashMap<>();
        for (int i = 0; i < releases.size(); i++) {
            map.put(releases.get(i),list.get(i));
        }
        return map;
    }

}
