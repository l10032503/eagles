package com.example.eagles.newsbigdata;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KeywordExtract {

    public JSONObject makeQuery(String title, String sub_title, String content){
        JSONObject sendObject = new JSONObject();
        JSONObject argument = new JSONObject();

        try{
            sendObject.put("access_key", "42d3584e-0c65-4b25-802d-11697fe24e9c");
            //argument
            JSONArray news_ids = new JSONArray();
            argument.put("title",title);
            argument.put("sub_title",sub_title);
            argument.put("content",content);

            sendObject.put("argument",argument);
        } catch (Exception e){
            e.printStackTrace();
        }
        return sendObject;
    }
}
