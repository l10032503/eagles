package com.example.eagles.web;

import com.example.eagles.Spark.WordCount;
import com.example.eagles.Spark.topicKeyword;
import com.example.eagles.newsbigdata.Bigkinds;
import com.example.eagles.newsbigdata.IssueRanking;
import com.example.eagles.newsbigdata.NewsSearch;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class YearKeyWordController {
    @Autowired
    WordCount service;

    public String make_title_all(String dateFrom){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String year_topic_keyword_all = "";
        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        String resultString = "";
        Object obj = new Object();
        JSONArray documents = new JSONArray();
        JSONObject documentsElement = new JSONObject();
        NewsSearch newsSearch = new NewsSearch();
        Bigkinds bigkinds = new Bigkinds();
        List<String> providerList = new ArrayList<String>();
        List<String> category_List = new ArrayList<String>();
        List<String> category_incident_List = new ArrayList<String>();
        List<String> provider_subject_List = new ArrayList<String>();
        List<String> subject_info_List = new ArrayList<String>();
        List<String> subject_info1_List = new ArrayList<String>();
        List<String> subject_info2_List = new ArrayList<String>();
        List<String> subject_info3_List = new ArrayList<String>();
        List<String> subject_info4_List = new ArrayList<String>();
        List<String> fields_List = new ArrayList<String>();
        fields_List.add("title");

        String queryString = newsSearch.makeQuery("", "2018-11-15", simpleDateFormat.format(today),
                providerList,category_List,category_incident_List, "", provider_subject_List,
                subject_info_List, subject_info1_List, subject_info2_List, subject_info3_List,
                subject_info4_List, "date", "desc", 200, 0, 5, fields_List).toString();
        resultString = bigkinds.postURL("http://tools.kinds.or.kr:8888/search/news",queryString);

        try{
            obj = jsonParser.parse(resultString);
            jsonObject = (JSONObject) obj;
            JSONObject return_object = new JSONObject();
            return_object = (JSONObject) jsonObject.get("return_object");

            documents = (JSONArray) return_object.get("document");
            for(int i=0; i < 10 ;i++){
                documentsElement = (JSONObject) documents.get(i);
            }
        }catch (ParseException e){
            e.printStackTrace();
        }

        return year_topic_keyword_all;
    }

    @GetMapping("/year-keyword")
    public String year_keyword(Model model){
        JSONParser jsonParser = new JSONParser();
        JSONObject result = new JSONObject();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String title_all = "";

        title_all = make_title_all("2018-11-15");

        List<String> wordList = Arrays.asList(title_all.split(" "));
        result.putAll(service.getCount(wordList));
        model.addAttribute("yearkeywordtest",result.toString());

        return "year-keyword";
    }
}
