package com.example.eagles.newsbigdata;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentElement {

    public JSONObject makeDoumentElement(String searchQuery){
        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        JSONObject return_object = new JSONObject();
        JSONArray documents = new JSONArray();

        try{
            Object obj = jsonParser.parse(searchQuery);
            jsonObject = (JSONObject) obj;
            return_object = (JSONObject) jsonObject.get("return_object");
            documents = (JSONArray) return_object.get("documents");


        }catch (ParseException e){
            e.printStackTrace();
        }

        return (JSONObject) documents.get(0);
    }
}
