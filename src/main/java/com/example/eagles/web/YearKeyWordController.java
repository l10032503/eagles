package com.example.eagles.web;

import com.example.eagles.Spark.WordCount;
import com.example.eagles.newsbigdata.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class YearKeyWordController {

    @GetMapping("/year-keyword")
    public String year_keyword(Model model,
                               @RequestParam(value = "datefrom", required = false, defaultValue = "null")String dateFrom,
                               @RequestParam(value = "dateuntil", required = false, defaultValue = "null")String dateUntil
                               ){
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
        String news_id = "";
        String searchQuery = "";
        SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
        List<String> news_ids_List = new ArrayList<String>();
        List<String> fields_List = new ArrayList<String>();
        Document document = new Document();
        JSONArray documents = new JSONArray();
        JSONObject documentsElement = new JSONObject();
        fields_List.add("title");
        fields_List.add("category");
        fields_List.add("news_id");
        int politicsIndex = 0;
        int economicsIndex = 0;
        int societyIndex = 0;
        int cultureIndex = 0;
        int worldIndex = 0;
        int ITIndex = 0;
        JSONArray categoryArray = new JSONArray();
        String elementCategory = "";

        if(dateFrom.equals("null")){
            Date today = new Date();
            SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
            Calendar cal = Calendar.getInstance();
            int year  = Integer.parseInt(yyyyMMdd.format(today).substring(0, 4));
            int month = Integer.parseInt(yyyyMMdd.format(today).substring(4, 6));
            int date  = Integer.parseInt(yyyyMMdd.format(today).substring(6, 8));
            cal.set(year, month - 1, date);
            cal.add(Calendar.DATE, -7);     // 1년 전
            dateFrom = yyyy_MM_dd.format(cal.getTime());
        }
        if(dateUntil.equals("null")){
            Date today = new Date();
            dateUntil = yyyy_MM_dd.format(today);
        }
        try{
            Date startDate = yyyy_MM_dd.parse(dateFrom);
            Date endDate = yyyy_MM_dd.parse(dateUntil);

            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);

            for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                jsonObject = issueRanking.makeIssue(yyyy_MM_dd.format(date), providerList);
                String issueQuery = bigkinds.postURL("http://tools.kinds.or.kr:8888/issue_ranking",jsonObject.toString());

                Object obj = jsonParser.parse(issueQuery);
                jsonObject = (JSONObject) obj;
                return_object = (JSONObject) jsonObject.get("return_object");

                topics = (JSONArray) return_object.get("topics");


                for(int i=0; i < 3 ;i++) {
                    topicElement = (JSONObject) topics.get(i);
                    model.addAttribute(index + i, topicElement.get("topic"));
                    news_cluster = (JSONArray) topicElement.get("news_cluster");
                    for(int j=0; j<3; j++){
                        news_id = (String) news_cluster.get(j);
                        news_ids_List.add(news_id);
                        searchQuery = bigkinds.postURL("http://tools.kinds.or.kr:8888/search/news",
                                newsSearch.makeQuery(news_ids_List,fields_List).toString());
                        news_ids_List.remove(news_id);
                        documents = document.makeDoumentElement(searchQuery);
                        if(!documents.isEmpty()){
                            documentsElement = (JSONObject) documents.get(0);
                            categoryArray = (JSONArray)  documentsElement.get("category");
                            if(!categoryArray.isEmpty()){
                                elementCategory = categoryArray.get(0).toString().split(">")[0];

                                switch (elementCategory){
                                    case "정치":
                                        model.addAttribute("politics" + politicsIndex + "title", documentsElement.get("title"));
                                        model.addAttribute("politics" + politicsIndex + "provider", news_id.substring(0,8));
                                        model.addAttribute("politics" + politicsIndex + "date", news_id.substring(9));
                                        politicsIndex++;
                                        break;
                                    case "경제":
                                        model.addAttribute("economics" + economicsIndex + "title", documentsElement.get("title"));
                                        model.addAttribute("economics" + economicsIndex + "provider", news_id.substring(0,8));
                                        model.addAttribute("economics" + economicsIndex + "date", news_id.substring(9));
                                        economicsIndex++;
                                        break;
                                    case "사회":
                                        model.addAttribute("society" + societyIndex + "title", documentsElement.get("title"));
                                        model.addAttribute("society" + societyIndex + "provider", news_id.substring(0,8));
                                        model.addAttribute("society" + societyIndex + "date", news_id.substring(9));
                                        societyIndex++;
                                        break;
                                    case "문화":
                                        model.addAttribute("culture" + cultureIndex + "title", documentsElement.get("title"));
                                        model.addAttribute("culture" + cultureIndex + "provider", news_id.substring(0,8));
                                        model.addAttribute("culture" + cultureIndex + "date", news_id.substring(9));
                                        cultureIndex++;
                                        break;
                                    case "국제":
                                        model.addAttribute("world" + worldIndex + "title", documentsElement.get("title"));
                                        model.addAttribute("world" + worldIndex + "provider", news_id.substring(0,8));
                                        model.addAttribute("world" + worldIndex + "date", news_id.substring(9));
                                        worldIndex++;
                                        break;
                                    case "IT_과학":
                                        model.addAttribute("it" + ITIndex + "title", documentsElement.get("title"));
                                        model.addAttribute("it" + ITIndex + "provider", news_id.substring(0,8));
                                        model.addAttribute("it" + ITIndex + "date", news_id.substring(9));
                                        ITIndex++;
                                        break;
                                    case "지역":
                                        break;
                                    default:
                                        System.out.println(elementCategory);
                                        System.out.println("error");
                                        break;
                                }
                            }
                        }

                    }
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }


        return "year-keyword";
    }
}