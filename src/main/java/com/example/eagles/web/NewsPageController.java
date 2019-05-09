package com.example.eagles.web;

import com.example.eagles.newsbigdata.Bigkinds;
import com.example.eagles.newsbigdata.Document;
import com.example.eagles.newsbigdata.NewsSearch;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
        Document document = new Document();
        List<String> news_ids_List = new ArrayList<String>();
        List<String> fields_List = new ArrayList<String>();
        Bigkinds bigkinds = new Bigkinds();
        JSONArray documents = new JSONArray();
        JSONObject documentsElement = new JSONObject();
        JSONArray category = new JSONArray();

        news_ids_List.add(code_provider + "." + code_date);
        fields_List.add("title");
        fields_List.add("provider");
        fields_List.add("byline");
        fields_List.add("published_at");
        fields_List.add("images");
        fields_List.add("content");
        fields_List.add("category");
        String searchQuery = bigkinds.postURL("http://tools.kinds.or.kr:8888/search/news",
                newsSearch.makeQuery(news_ids_List,fields_List).toString());
        System.out.println(searchQuery);
        try{
            documents = document.makeDoumentElement(searchQuery);
            documentsElement = (JSONObject) documents.get(0);
            model.addAttribute("title", documentsElement.get("title"));
            model.addAttribute("provider", documentsElement.get("provider"));
            model.addAttribute("published_at", documentsElement.get("published_at").toString().substring(0,10));
            model.addAttribute("byline", documentsElement.get("byline"));
            model.addAttribute("content", documentsElement.get("content"));
            model.addAttribute("images", documentsElement.get("images"));
            category = (JSONArray) documentsElement.get("category");
            for(int i = 0; i<category.size(); i++){
                model.addAttribute("category" + i, category.get(i));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "newspage";
    }
}