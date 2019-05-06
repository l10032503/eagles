package com.example.eagles.newsbigdata;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IssueRanking {

    public JSONObject makeIssue(String date, List<String> providerList){
        JSONObject sendObject = new JSONObject();
        JSONObject argument = new JSONObject();
        try {
            sendObject.put("access_key", "42d3584e-0c65-4b25-802d-11697fe24e9c");
            argument.put("date", date);
            JSONArray provider = new JSONArray();
            if(!providerList.isEmpty())
                provider.addAll(providerList);
            argument.put("provider",provider);
            sendObject.put("argument",argument);
        }catch(Exception e){
            e.printStackTrace();
        }
        return sendObject;
    }

    public JSONObject makeIssue(String date){
        JSONObject sendObject = new JSONObject();
        JSONObject argument = new JSONObject();
        JSONArray provider = new JSONArray();
        try {
            sendObject.put("access_key", "42d3584e-0c65-4b25-802d-11697fe24e9c");
            argument.put("date", date);
            argument.put("provider", provider);
            sendObject.put("argument",argument);
        }catch(Exception e){
            e.printStackTrace();
        }
        return sendObject;
    }
}
