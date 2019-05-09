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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class SearchController {

    @GetMapping("/search")
    public String search(Model model, @RequestParam(value = "query", required = false)String query,
                         @RequestParam(value = "dateFrom", required = false, defaultValue = "null")String dateFrom,
                         @RequestParam(value = "dateUntil", required = false, defaultValue = "null")String dateUntil,
                         @RequestParam(value = "provider", required = false, defaultValue = "null")String[] provider,
                         @RequestParam(value = "category", required = false, defaultValue = "null")String[] category,
                         @RequestParam(value = "category-incident", required = false, defaultValue = "null")String[] category_incident,
                         @RequestParam(value = "byline", required = false)String byline,
                         @RequestParam(value = "provider-subject", required = false, defaultValue = "null")String[] provider_subject,
                         @RequestParam(value = "subject-info", required = false, defaultValue = "null")String[] subject_info,
                         @RequestParam(value = "subject-info1", required = false, defaultValue = "null")String[] subject_info1,
                         @RequestParam(value = "subject-info2", required = false, defaultValue = "null")String[] subject_info2,
                         @RequestParam(value = "subject-info3", required = false, defaultValue = "null")String[] subject_info3,
                         @RequestParam(value = "subject-info4", required = false, defaultValue = "null")String[] subject_info4,
                         @RequestParam(value = "sortField", required = false, defaultValue = "date")String sortField,
                         @RequestParam(value = "sortOrder", required = false, defaultValue = "desc")String sortOrder,
                         @RequestParam(value = "hilight", required = false, defaultValue = "200")String hilight,
                         @RequestParam(value = "returnFrom", required = false, defaultValue = "0")String returnFrom,
                         @RequestParam(value = "returnSize", required = false, defaultValue = "10")String returnSize,
                         @RequestParam(value = "fields", required = false, defaultValue = "null")String[] fields){
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
        providerList = ArrayToList(provider, providerList);
        category_List = ArrayToList(category, category_List);
        category_incident_List = ArrayToList(category_incident, category_incident_List);
        provider_subject_List = ArrayToList(provider_subject, provider_subject_List);
        subject_info_List = ArrayToList(subject_info, subject_info_List);
        subject_info1_List = ArrayToList(subject_info1, subject_info1_List);
        subject_info2_List = ArrayToList(subject_info2, subject_info2_List);
        subject_info3_List = ArrayToList(subject_info, subject_info3_List);
        subject_info4_List = ArrayToList(subject_info, subject_info4_List);
        fields_List = ArrayToList(fields, fields_List);
        int hilightInt = Integer.parseInt(hilight);
        int returnFromInt = Integer.parseInt(returnFrom);
        int returnSizeInt = Integer.parseInt(returnSize);
        JSONArray documents = new JSONArray();
        JSONObject documentsElement = new JSONObject();
        Document document = new Document();
        //default값을 1년동안으로 설정
        if(dateFrom.equals("null")){
            Date today = new Date();
            SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
            Calendar cal = Calendar.getInstance();
            int year  = Integer.parseInt(yyyyMMdd.format(today).substring(0, 4));
            int month = Integer.parseInt(yyyyMMdd.format(today).substring(4, 6));
            int date  = Integer.parseInt(yyyyMMdd.format(today).substring(6, 8));
            cal.set(year, month - 1, date);
            cal.add(Calendar.YEAR, -1);     // 1년 전
            SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
            dateFrom = yyyy_MM_dd.format(cal.getTime());
        }
        //default값을 1년동안으로 설정
        if(dateUntil.equals("null")){
            Date today = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateUntil = simpleDateFormat.format(today);
        }
        fields_List.add("title");
        fields_List.add("provider");
        fields_List.add("byline");
        fields_List.add("published_at");
        fields_List.add("hilight");
        fields_List.add("news_id");
        String news_id = "";
        String hilightFilter = "";

        JSONObject jsonObject = newsSearch.makeQuery(query, dateFrom, dateUntil,
                providerList,category_List,category_incident_List, byline, provider_subject_List,
                subject_info_List, subject_info1_List, subject_info2_List, subject_info3_List,
                subject_info4_List, sortField, sortOrder, hilightInt, returnFromInt, returnSizeInt, fields_List);

        String searchQuery = bigkinds.postURL("http://tools.kinds.or.kr:8888/search/news",
               jsonObject.toString());

        try{
            documents = document.makeDoumentElement(searchQuery);
            for(int i=0; i<documents.size(); i++){
                documentsElement = (JSONObject) documents.get(i);
                model.addAttribute("title" + i, documentsElement.get("title"));
                model.addAttribute("title" + i + "provider", documentsElement.get("provider"));
                model.addAttribute("title" + i + "published_at", documentsElement.get("published_at").toString().substring(0,10));
                hilightFilter = (String) documentsElement.get("byline");
                model.addAttribute("title" + i + "byline", hilightFilter);
                model.addAttribute("title" + i + "hilight", documentsElement.get("hilight"));
                news_id = (String) documentsElement.get("news_id");
                model.addAttribute("title" + i + "codeprovider", news_id.substring(0,8));
                model.addAttribute("title" + i + "codedate", news_id.substring(9));
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        return "search";
    }

    private List<String> ArrayToList (String[] array, List<String> listString){
        if(!array[0].equals("null")){
            for(int i = 0; i<array.length; i++){
                listString.add(array[i]);
            }
        }

        return listString;
    }
}
