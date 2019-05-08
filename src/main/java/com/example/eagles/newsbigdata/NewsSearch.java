package com.example.eagles.newsbigdata;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NewsSearch {

    public JSONObject makeQuery(String query, String dateFrom, String dateUntil, List<String> providerList,
                                List<String> categoryList, List<String> category_incidentList, String byline,
                                List<String> provider_subjectList, List<String> subject_infoList,
                                List<String> subject_info1List, List<String> subject_info2List,
                                List<String> subject_info3List, List<String> subject_info4List,
                                String sortField, String sortOrder, int hilight, int returnFrom, int returnSize,
                                List<String> fieldsList){
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

                JSONArray subject_info = new JSONArray();
                if(!subject_infoList.isEmpty())
                    subject_info.addAll(subject_infoList);
                argument.put("subject_info", subject_info);

                JSONArray subject_info1 = new JSONArray();
                if(!subject_info1List.isEmpty())
                    subject_info1.addAll(subject_info1List);
                argument.put("subject_info1", subject_info1);

                JSONArray subject_info2 = new JSONArray();
                if(!subject_info2List.isEmpty())
                    subject_info2.addAll(subject_info2List);
                argument.put("subject_info2", subject_info2);

                JSONArray subject_info3 = new JSONArray();
                if(!subject_info3List.isEmpty())
                    subject_info3.addAll(subject_info3List);
                argument.put("subject_info3", subject_info3);

                JSONArray subject_info4 = new JSONArray();
                if(!subject_info4List.isEmpty())
                    subject_info4.addAll(subject_info4List);
                argument.put("subject_info4", subject_info4);

                JSONObject sort = new JSONObject();
                sort.put(sortField, sortOrder);
                argument.put("sort", sort);

                argument.put("hilight", hilight);
                argument.put("return_from", returnFrom);
                argument.put("return_size", returnSize);

                JSONArray fields = new JSONArray();
                if(!fieldsList.isEmpty())
                    fields.addAll(fieldsList);

                argument.put("fields", fields);

            sendObject.put("argument",argument);
        }catch(Exception e){
            e.printStackTrace();
        }
        return sendObject;
    }


}
