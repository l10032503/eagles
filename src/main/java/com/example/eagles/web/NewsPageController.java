package com.example.eagles.web;

import com.example.eagles.newsbigdata.Bigkinds;
import com.example.eagles.newsbigdata.NewsSearch;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsPageController {

    @GetMapping("/newspage")
    public String newspage(Model model,
                           @RequestParam(value = "codeprovider", required = false, defaultValue = "01500701")String code_provider,
                           @RequestParam(value = "codedate", required = false, defaultValue = "2015083110018412570")String code_date){
        NewsSearch newsSearch = new NewsSearch();
        List<String> news_ids_List = new ArrayList<String>();
        List<String> fields_List = new ArrayList<String>();
        Bigkinds bigkinds = new Bigkinds();
        JSONObject jsonObject = new JSONObject();
        JSONObject return_object = new JSONObject();
        JSONArray documents = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        JSONObject documentsElement = new JSONObject();

        news_ids_List.add(code_provider + "." + code_date);
        fields_List.add("title");
        fields_List.add("provider");
        fields_List.add("byline");
        fields_List.add("published_at");
        fields_List.add("images");
        fields_List.add("content");
        fields_List.add("subject_info");
        fields_List.add("subject_info1");
        fields_List.add("subject_info2");
        fields_List.add("subject_info3");
        fields_List.add("subject_info4");
        String searchQuery = bigkinds.postURL("http://tools.kinds.or.kr:8888/search/news",
                newsSearch.makeQuery(news_ids_List,fields_List).toString());
        System.out.println(searchQuery);
        try{
            Object obj = jsonParser.parse(searchQuery);
            jsonObject = (JSONObject) obj;
            return_object = (JSONObject) jsonObject.get("return_object");
            documents = (JSONArray) return_object.get("documents");

            documentsElement = (JSONObject) documents.get(0);
            model.addAttribute("title", documentsElement.get("title"));
            model.addAttribute("provider", documentsElement.get("provider"));
            model.addAttribute("published_at", documentsElement.get("published_at").toString().substring(0,10));
            model.addAttribute("byline", documentsElement.get("byline"));
            model.addAttribute("content", documentsElement.get("content"));
            model.addAttribute("images", documentsElement.get("images"));
            JSONArray subject_info_Array = (JSONArray) documentsElement.get("subject_info");
            model.addAttribute("subject_info", subject_info_Array.get(0));
            model.addAttribute("subject_info1", documentsElement.get("subject_info1"));
            model.addAttribute("subject_info2", documentsElement.get("subject_info2"));
            model.addAttribute("subject_info3", documentsElement.get("subject_info3"));
            model.addAttribute("subject_info4", documentsElement.get("subject_info4"));


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "newspage";
    }
}
