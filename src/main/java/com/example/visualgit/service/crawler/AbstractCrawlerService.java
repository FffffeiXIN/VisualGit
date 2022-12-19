package com.example.visualgit.service.crawler;

import com.example.visualgit.entity.Result;
import com.example.visualgit.mapper.RepositoryMapper;
import com.example.visualgit.utils.GetBeanUtil;
import com.google.gson.JsonArray;
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

public abstract class AbstractCrawlerService implements CrawlerService{
    RepositoryMapper mapper = GetBeanUtil.getBean(RepositoryMapper.class);

    String repository;

    @Override
    public Result crawlData(String s) throws IOException {
//        String s = "https://api.github.com/repos/msgpack/msgpack-c/issues";
        repository = s.substring(0,s.lastIndexOf("/"));
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
        JsonArray array= (JsonArray) jsonParser.parse(json);
        for(int i=0;i<array.size();++i) {
            doCrawl(array.get(i).getAsJsonObject());
        }
        return Result.ok().code(200);
    }

    public abstract Result doCrawl(JsonObject object);
}
