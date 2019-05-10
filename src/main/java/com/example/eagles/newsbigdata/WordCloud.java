package com.example.eagles.newsbigdata;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WordCloud {

    public JSONObject makeQuery(String query, String dateFrom, String dateUntil, List<String> providerList,
                                List<String> categoryList, List<String>category_incidentList, String byline, List<String> provider_subjectList){
        JSONObject sendObject = new JSONObject();
        JSONObject argument = new JSONObject();
        try {
            sendObject.put("access_key", "42d3584e-0c65-4b25-802d-11697fe24e9c");

            //argument
            argument.put("query", query);

            JSONObject published_at = new JSONObject();
            published_at.put("from", dateFrom);
            published_at.put("until", dateUntil);
            argument.put("published_at", published_at);

            JSONArray provider = new JSONArray();
            if(!providerList.isEmpty())
                provider.addAll(providerList);
            argument.put("provider",provider);

            JSONArray category = new JSONArray();
            if(!categoryList.isEmpty())
                category.addAll(categoryList);
            argument.put("category", category);

            JSONArray category_incident = new JSONArray();
            if(!category_incidentList.isEmpty())
                category_incident.addAll(category_incidentList);
            argument.put("category_incident", category_incident);

            argument.put("byline", byline);

            JSONArray provider_subject = new JSONArray();
            if(!provider_subjectList.isEmpty())
                provider_subject.addAll(provider_subjectList);
            argument.put("provider_subject", provider_subject);

            sendObject.put("argument",argument);
        }catch(Exception e){
            e.printStackTrace();
        }
        return sendObject;
    }
}
