package com.example.eagles.web;

import com.example.eagles.Spark.WordCount;
import com.example.eagles.newsbigdata.Bigkinds;
import com.example.eagles.newsbigdata.KeywordExtract;
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
        String title_all = "";
        String content_all = "";
        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        String resultString = "";
        Object obj = new Object();
        JSONArray documents = new JSONArray();
        JSONObject documentsElement = new JSONObject();
        NewsSearch newsSearch = new NewsSearch();
        Bigkinds bigkinds = new Bigkinds();
        KeywordExtract keywordExtract = new KeywordExtract();

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
        fields_List.add("content");
        category_List.add("정치");
        category_List.add("경제");
        category_List.add("사회");
        category_List.add("국제");
        category_List.add("IT_과학");
        String title = "";
        String content = "";
        String titleFilter = "";
        String queryString = "";
        JSONObject keyword_return_object = new JSONObject();
        JSONObject keyword_result = new JSONObject();

        try{
            queryString =  newsSearch.makeQuery("", "2018-11-15", simpleDateFormat.format(today),
                    providerList,category_List,category_incident_List, "", provider_subject_List,
                    subject_info_List, subject_info1_List, subject_info2_List, subject_info3_List,
                    subject_info4_List, "date", "desc", 200, 0, 1, fields_List).toString();
            resultString = bigkinds.postURL("http://tools.kinds.or.kr:8888/search/news",queryString);
            obj = jsonParser.parse(resultString);
            jsonObject = (JSONObject) obj;
            JSONObject return_object = new JSONObject();
            return_object = (JSONObject) jsonObject.get("return_object");
            int total_hits = (int) return_object.get("total_hits");

            for(int i = 0; i<=total_hits; i += 100){
                queryString =  newsSearch.makeQuery("", "2018-11-15", simpleDateFormat.format(today),
                        providerList,category_List,category_incident_List, "", provider_subject_List,
                        subject_info_List, subject_info1_List, subject_info2_List, subject_info3_List,
                        subject_info4_List, "date", "desc", 200, i, 99, fields_List).toString();
                resultString = bigkinds.postURL("http://tools.kinds.or.kr:8888/search/news",queryString);
                obj = jsonParser.parse(resultString);
                jsonObject = (JSONObject) obj;
                return_object = (JSONObject) jsonObject.get("return_object");
                documents = (JSONArray) return_object.get("documents");

                for(int j=0; j < documents.size() ;j++){
                    documentsElement = (JSONObject) documents.get(j);
                    title = (String) documentsElement.get("title");
                    content = (String) documentsElement.get("content");
                    titleFilter = StringReplace((String) documentsElement.get("title"));
                    //System.out.println(titleFilter);
                    title_all = title_all + " " + titleFilter + " ";
                }

            }
        }catch (ParseException e){
            e.printStackTrace();
        }

        return title_all;
    }

    private static String StringReplace(String str){
        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        //System.out.println(str);
        str =str.replaceAll(match, " ");
        //System.out.println(str);
        return str;
    }


    @GetMapping("/year-keyword")
    public String year_keyword(Model model){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String title_all = "";
        JSONObject result = new JSONObject();

        title_all = make_title_all("2019-05-01");

        List<String> wordList = Arrays.asList(title_all.split(" "));
        result.putAll(service.getCount(wordList));
        model.addAttribute("yearkeywordtest",result);

        return "year-keyword";
    }
}
