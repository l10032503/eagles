package com.example.eagles.web;

import com.example.eagles.Spark.WordCount;
import com.example.eagles.Spark.topicKeyword;
import com.example.eagles.newsbigdata.Bigkinds;
import com.example.eagles.newsbigdata.IssueRanking;
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

    public String make_year_topic_keyword_all(Calendar start, Calendar end, IssueRanking issueRanking, Bigkinds bigkinds,
                                              JSONParser jsonParser, SimpleDateFormat formatter){
        String year_topic_keyword_all = "";
        JSONObject jsonObject = new JSONObject();
        String issueQuery = "";
        Object obj = new Object();
        JSONArray topics = new JSONArray();
        JSONObject topicElement = new JSONObject();
        List<String> providerList = new ArrayList<String>();

        try{
            for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                jsonObject = issueRanking.makeIssue(formatter.format(date), providerList);
                issueQuery = bigkinds.postURL("http://tools.kinds.or.kr:8888/issue_ranking",jsonObject.toString());
                obj = jsonParser.parse(issueQuery);
                jsonObject = (JSONObject) obj;
                JSONObject return_object = new JSONObject();
                return_object = (JSONObject) jsonObject.get("return_object");
                topics = (JSONArray) return_object.get("topics");
                for(int i=0; i < 10 ;i++){
                    topicElement = (JSONObject) topics.get(i);
                    year_topic_keyword_all = year_topic_keyword_all + topicElement.get("topic_keyword") + ",";
                }
            }
        }catch (ParseException e){
            e.printStackTrace();
        }

        return year_topic_keyword_all;
    }

    @GetMapping("/year-keyword")
    public String year_keyword(Model model){
        IssueRanking issueRanking = new IssueRanking();
        Bigkinds bigkinds = new Bigkinds();
        JSONParser jsonParser = new JSONParser();
        JSONObject result = new JSONObject();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String year_topic_keyword_all = "";
        Date startDate = new Date();
        Date endDate = new Date();
        try{
            startDate = formatter.parse("2018-11-15");
        } catch (Exception e){
            e.printStackTrace();
        }
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        end.add(Calendar.DATE, -1);

        year_topic_keyword_all = make_year_topic_keyword_all(start, end, issueRanking, bigkinds, jsonParser, formatter);

        List<String> wordList = Arrays.asList(year_topic_keyword_all.split(","));
        result.putAll(service.getCount(wordList));
        model.addAttribute("yearkeywordtest",result.toString());

        return "year-keyword";
    }
}
