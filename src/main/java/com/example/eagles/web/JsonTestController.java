/*package com.example.eagles.web;

import com.example.eagles.newsbigdata.IssueRanking;
import com.example.eagles.newsbigdata.NewsSearch;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class JsonTestController {

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
}
*/