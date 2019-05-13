package com.example.eagles.web;


import com.example.eagles.newsbigdata.Bigkinds;
import com.example.eagles.newsbigdata.Document;
import com.example.eagles.newsbigdata.IssueRanking;
import com.example.eagles.newsbigdata.NewsSearch;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class WebController {
    //@Autowired
    //WordCount service;


    @GetMapping("/")
    public String main(Model model) {
        IssueRanking issueRanking = new IssueRanking();
        Bigkinds bigkinds = new Bigkinds();
        NewsSearch newsSearch = new NewsSearch();
        JSONObject jsonObject = new JSONObject();
        List<String> providerList = new ArrayList<String>();
        JSONParser jsonParser = new JSONParser();
        JSONObject return_object = new JSONObject();
        JSONArray topics = new JSONArray();
        JSONObject topicElement = new JSONObject();
        String index = "topic";
        JSONArray news_cluster = new JSONArray();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String todaystr = "";
        String[] title = new String[3];
        String news_id = "";
        String searchQuery = "";
        List<String> news_ids_List = new ArrayList<String>();
        List<String> fields_List = new ArrayList<String>();
        Document document = new Document();
        JSONArray documents = new JSONArray();
        JSONObject documentsElement = new JSONObject();
        fields_List.add("title");

        try{
            todaystr = formatter.format(today);
        } catch (Exception e){
            e.printStackTrace();
        }

        jsonObject = issueRanking.makeIssue(todaystr, providerList);
        String issueQuery = bigkinds.postURL("http://tools.kinds.or.kr:8888/issue_ranking",jsonObject.toString());

        try{
            Object obj = jsonParser.parse(issueQuery);
            jsonObject = (JSONObject) obj;
            return_object = (JSONObject) jsonObject.get("return_object");

            topics = (JSONArray) return_object.get("topics");

            for(int i=0; i < 3 ;i++) {
                topicElement = (JSONObject) topics.get(i);
                System.out.println(i + " " +topicElement.get("topic"));
                model.addAttribute("topic" + i, topicElement.get("topic"));
                //model.addAttribute(index + i, topicElement.get("topic"));
                news_cluster = (JSONArray) topicElement.get("news_cluster");
                for(int j=0; j<3; j++){
                    news_id = (String) news_cluster.get(j);
                    model.addAttribute("topic" + i + "provider"+j, news_id.substring(0,8));
                    model.addAttribute("topic" + i + "date"+j, news_id.substring(9));
                    news_ids_List.add(news_id);
                    searchQuery = bigkinds.postURL("http://tools.kinds.or.kr:8888/search/news",
                            newsSearch.makeQuery(news_ids_List,fields_List).toString());
                    news_ids_List.remove(news_id);
                    documents = document.makeDoumentElement(searchQuery);
                    documentsElement = (JSONObject) documents.get(0);
                    model.addAttribute("topic" + i + "title"+j, documentsElement.get("title"));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "main";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/temp")
    public String temp(){
        return "temp";
    }

}