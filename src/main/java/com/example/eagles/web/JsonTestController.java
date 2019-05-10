/*package com.example.eagles.web;

import com.example.eagles.newsbigdata.Bigkinds;
import com.example.eagles.newsbigdata.IssueRanking;
import com.example.eagles.newsbigdata.NewsSearch;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@AllArgsConstructor
public class JsonTestController {
    @GetMapping("/todaytopic")
    public String todaytopic(Model model){
        IssueRanking issueRanking = new IssueRanking();
        Bigkinds bigkinds = new Bigkinds();
        JSONObject jsonObject = new JSONObject();
        List<String> providerList = new ArrayList<String>();
        JSONParser jsonParser = new JSONParser();
        JSONObject return_object = new JSONObject();
        JSONArray topics = new JSONArray();
        JSONObject topicElement = new JSONObject();
        String index = "topic";
        JSONObject result = new JSONObject();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = new Date();
        Date endDate = new Date();
        String today_topic_keyword_all = "";

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

        jsonObject = issueRanking.makeIssue("2019-01-01", providerList);
        String issueQuery = bigkinds.postURL("http://tools.kinds.or.kr:8888/issue_ranking",jsonObject.toString());
        System.out.println(issueQuery);
        try{
            Object obj = jsonParser.parse(issueQuery);
            jsonObject = (JSONObject) obj;
            return_object = (JSONObject) jsonObject.get("return_object");

            System.out.println(return_object);
            topics = (JSONArray) return_object.get("topics");
            System.out.println(topics);

            for(int i=0; i < 10 ;i++) {
                topicElement = (JSONObject) topics.get(i);
                model.addAttribute(index + i, topicElement.get("topic"));
            }

            for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                jsonObject = issueRanking.makeIssue(formatter.format(date), providerList);
                issueQuery = bigkinds.postURL("http://tools.kinds.or.kr:8888/issue_ranking",jsonObject.toString());
                obj = jsonParser.parse(issueQuery);
                jsonObject = (JSONObject) obj;
                return_object = (JSONObject) jsonObject.get("return_object");
                topics = (JSONArray) return_object.get("topics");
                for(int i=0; i < 10 ;i++){
                    topicElement = (JSONObject) topics.get(i);
                    today_topic_keyword_all = today_topic_keyword_all + topicElement.get("topic_keyword") + ",";
                }
            }

            model.addAttribute("yearkeyword0",result.toString());


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "main";
    }

    @GetMapping("/jsontest")
    public String jsontest(Model model){
        IssueRanking issueRanking = new IssueRanking();
        NewsSearch newsSearch = new NewsSearch();
        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();

        List<String> providerList = new ArrayList<String>();
        providerList.add("경향신문");
        providerList.add("조선일보");
        jsonObject = issueRanking.makeIssue("2019-01-01", providerList);

        JSONObject argument = (JSONObject) jsonObject.get("argument");
        System.out.println(argument);
        String date = (String) argument.get("date");
        model.addAttribute("date", date);

        List<String> category_List = new ArrayList<String>();
        List<String> category_incident_List = new ArrayList<String>();
        List<String> provider_subject_List = new ArrayList<String>();
        List<String> subject_info_List = new ArrayList<String>();
        List<String> subject_info1_List = new ArrayList<String>();
        List<String> subject_info2_List = new ArrayList<String>();
        List<String> subject_info3_List = new ArrayList<String>();
        List<String> subject_info4_List = new ArrayList<String>();
        List<String> fields_List = new ArrayList<String>();

        jsonObject = newsSearch.makeQuery("키워드", "2014-03-03", date,
                providerList,category_List,category_incident_List, "", provider_subject_List,
                subject_info_List, subject_info1_List, subject_info2_List, subject_info3_List,
                subject_info4_List, "date", "desc", 200, 0, 5, fields_List);
        argument = (JSONObject) jsonObject.get("argument");
        System.out.println(argument);
        JSONObject published_at = (JSONObject) argument.get("published_at");
        System.out.println(published_at);

        String argumentstr = argument.toString();
        try{
            Object obj = jsonParser.parse(argumentstr);
            JSONObject test = (JSONObject) obj;
            JSONArray provider = (JSONArray) test.get("provider");
            model.addAttribute("provider", (String) provider.get(0));
        } catch (ParseException e){
            e.printStackTrace();
        }


        return "jsontest";
    }
}*/