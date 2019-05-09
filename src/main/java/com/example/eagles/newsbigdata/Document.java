package com.example.eagles.newsbigdata;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

@Repository
public class Document {

    public JSONArray makeDoumentElement(String searchQuery){
        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        JSONObject return_object = new JSONObject();

        try{
            Object obj = jsonParser.parse(searchQuery);
            jsonObject = (JSONObject) obj;
            return_object = (JSONObject) jsonObject.get("return_object");

        }catch (ParseException e){
            e.printStackTrace();
        }

        return (JSONArray) return_object.get("documents");
    }
}