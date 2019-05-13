package com.example.eagles.web;

import com.example.eagles.newsbigdata.Bigkinds;
import com.example.eagles.newsbigdata.IssueRanking;
import com.example.eagles.newsbigdata.KeywordExtract;
import com.example.eagles.newsbigdata.NewsSearch;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;


@RestController
public class WebRestController {

    @GetMapping("/irtest")
    public String irtest(Model model,
                         @RequestParam(value = "date", required = false, defaultValue = "null")String date,
                         @RequestParam(value = "provider", required = false, defaultValue = "null")String[] provider) {
        IssueRanking issueRanking = new IssueRanking();
        List<String> providerList = new ArrayList<String>();
        if(date.equals("null")){
            Date today = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = simpleDateFormat.format(today);
        }
        providerList = ArrayToList(provider, providerList);

        String issuerankingjsontest = issueRanking.makeIssue(date, providerList).toString();
        return issuerankingjsontest;
    }


    @GetMapping("/nstest")
    public String nstest(Model model,
                         @RequestParam(value = "query", required = false)String query,
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
                         @RequestParam(value = "returnSize", required = false, defaultValue = "5")String returnSize,
                         @RequestParam(value = "fields", required = false, defaultValue = "null")String[] fields
                         ) {
        NewsSearch newsSearch = new NewsSearch();
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
        if(dateUntil.equals("null")){
            Date today = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateUntil = simpleDateFormat.format(today);
        }

        String nsjsontest = newsSearch.makeQuery(query, dateFrom, dateUntil,
                providerList,category_List,category_incident_List, byline, provider_subject_List,
                subject_info_List, subject_info1_List, subject_info2_List, subject_info3_List,
                subject_info4_List, sortField, sortOrder, hilightInt, returnFromInt, returnSizeInt, fields_List).toString();

        return nsjsontest;
    }

    @GetMapping("/test")
    public String test() {
        IssueRanking issueRanking = new IssueRanking();
        Bigkinds bigkinds = new Bigkinds();
        String posttest = issueRanking.makeIssue("2019-05-13").toString();
        String post = bigkinds.postURL("http://tools.kinds.or.kr:8888/issue_ranking",posttest);
        return post;
    }

    @GetMapping("/searchtest")
    public String searchtest() {
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
        fields_List.add("news_id");
        fields_List.add("hilight");
        fields_List.add("provider");
        category_List.add("정치");
        String posttest = newsSearch.makeQuery("키워드", "2019-01-01", "2019-05-07",
                providerList,category_List,category_incident_List, "", provider_subject_List,
                subject_info_List, subject_info1_List, subject_info2_List, subject_info3_List,
                subject_info4_List, "date", "desc", 200, 0, 5, fields_List).toString();
        System.out.println(posttest);
        String post = bigkinds.postURL("http://tools.kinds.or.kr:8888/search/news",posttest);
        return post;
    }

    @GetMapping("/searchtest2")
    public String searchtest2() {
        NewsSearch newsSearch = new NewsSearch();
        Bigkinds bigkinds = new Bigkinds();
        List<String> news_id_List = new ArrayList<String>();
        List<String> fields_List = new ArrayList<String>();
        news_id_List.add("01100101.20190314120127001");
        fields_List.add("title");
        fields_List.add("published_at");
        fields_List.add("provider");
        fields_List.add("category");
        fields_List.add("subject_info");
        fields_List.add("subject_info1");
        fields_List.add("subject_info2");
        fields_List.add("subject_info3");
        fields_List.add("subject_info4");
        String posttest = newsSearch.makeQuery(news_id_List,fields_List).toString();
        System.out.println(posttest);
        String post = bigkinds.postURL("http://tools.kinds.or.kr:8888/search/news",posttest);
        return post;
    }

    @GetMapping("/keywordextracttest")
    public String keywordextract(){
        Bigkinds bigkinds = new Bigkinds();
        KeywordExtract keywordExtract = new KeywordExtract();
        String queryString = keywordExtract.makeQuery("태풍 솔릭'에 항공기 대규모 결항…오후 10시까지 총 532편 결항","","태풍 솔릭'에 항공기 대규모 결항…오후 10시까지 총 532편 결항").toString();
        System.out.println(queryString);
        String post = bigkinds.postURL("http://tools.kinds.or.kr:8888/keyword", queryString);
        return post;
    }


    private List<String> ArrayToList (String[] array, List<String> listString){
        if(!array[0].equals("null")){
            for(int i = 0; i<array.length; i++){
                listString.add(array[i]);
            }
        }

        return listString;
    }

        /*@GetMapping("/wordcounttest")
        public Map<String, Long> wordcounttest() {
            String words = "Siddhant,Agnihotry,Technocrat,Siddhant,Sid";
            List<String> wordList = Arrays.asList(words.split(","));
            return service.getCount(wordList);

        }*/
}
