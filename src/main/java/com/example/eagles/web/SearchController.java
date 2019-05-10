/*package com.example.eagles.web;

import com.example.eagles.newsbigdata.Bigkinds;
import com.example.eagles.newsbigdata.Document;
import com.example.eagles.newsbigdata.NewsSearch;
import com.example.eagles.newsbigdata.WordCloud;

import jdk.nashorn.internal.runtime.ParserException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
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
                         @RequestParam(value = "datefrom", required = false, defaultValue = "null")String dateFrom,
                         @RequestParam(value = "dateuntil", required = false, defaultValue = "null")String dateUntil,
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
                         @RequestParam(value = "hilight", required = false, defaultValue = "100")String hilight,
                         @RequestParam(value = "returnFrom", required = false, defaultValue = "0")String returnFrom,
                         @RequestParam(value = "returnSize", required = false, defaultValue = "100")String returnSize,
                         @RequestParam(value = "fields", required = false, defaultValue = "null")String[] fields){
        NewsSearch newsSearch = new NewsSearch();
        Bigkinds bigkinds = new Bigkinds();
        WordCloud wordCloud = new WordCloud();
        JSONParser jsonParser = new JSONParser();
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
        JSONObject return_object =  new JSONObject();
        JSONArray nodes = new JSONArray();
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
        String titlestr = "";
        String bylinestr = "";
        String published_at = "";
        int editIndex = 0;
        int straightIndex = 0;
        try{

            JSONObject jsonObject = wordCloud.makeQuery(query, dateFrom, dateUntil,
                    providerList,category_List,category_incident_List, byline, provider_subject_List);

            String searchQuery = bigkinds.postURL("http://tools.kinds.or.kr:8888/word_cloud",
                    jsonObject.toString());

            Object obj = jsonParser.parse(searchQuery);
            jsonObject = (JSONObject) obj;
            return_object = (JSONObject) jsonObject.get("return_object");
            nodes = (JSONArray) return_object.get("nodes");
            System.out.println(nodes);

            for(int i = 0; i<nodes.size(); i++){
                documentsElement = (JSONObject) nodes.get(i);
                model.addAttribute("keyword"+i, documentsElement.get("name"));
            }


            jsonObject = newsSearch.makeQuery(query, dateFrom, dateUntil,
                    providerList,category_List,category_incident_List, byline, provider_subject_List,
                    subject_info_List, subject_info1_List, subject_info2_List, subject_info3_List,
                    subject_info4_List, sortField, sortOrder, hilightInt, returnFromInt, returnSizeInt, fields_List);

            searchQuery = bigkinds.postURL("http://tools.kinds.or.kr:8888/search/news",
                    jsonObject.toString());

            documents = document.makeDoumentElement(searchQuery);

            for(int i = 0; i<documents.size(); i++){
                documentsElement = (JSONObject) documents.get(i);
                titlestr = (String) documentsElement.get("title");
                bylinestr = (String) documentsElement.get("byline");

                if(bylinestr == null)
                    bylinestr = "";
                news_id = (String) documentsElement.get("news_id");

                if(titlestr.contains("사설") || bylinestr.contains("사설") ||
                        titlestr.contains("칼럼") || bylinestr.contains("칼럼") ||
                        titlestr.contains("논설") || bylinestr.contains("논설") ||
                        titlestr.contains("논평") || bylinestr.contains("논평") ||
                        titlestr.contains("시평") || bylinestr.contains("시평") ||
                        titlestr.contains("시론") || bylinestr.contains("시론") ||
                        titlestr.contains("주석") || bylinestr.contains("주석") ||
                        titlestr.contains("논설위원") || bylinestr.contains("논설위원")){
                    if(editIndex < 10){
                        System.out.println(editIndex);
                        model.addAttribute("edit" + editIndex + "title", documentsElement.get("title"));
                        model.addAttribute("edit" + editIndex + "provider", news_id.substring(0,8));
                        model.addAttribute("edit" + editIndex + "date", news_id.substring(9));
                        model.addAttribute("edit" + editIndex + "newsprovider", documentsElement.get("provider"));
                        published_at = (String)documentsElement.get("published_at");
                        model.addAttribute("edit" + editIndex + "published_at", published_at.substring(0,10));
                        editIndex++;
                    }
                } else if (straightIndex < 10 ){
                    model.addAttribute("straight" + straightIndex + "title", documentsElement.get("title"));
                    model.addAttribute("straight" + straightIndex + "provider", news_id.substring(0,8));
                    model.addAttribute("straight" + straightIndex + "date", news_id.substring(9));
                    model.addAttribute("straight" + editIndex + "newsprovider", documentsElement.get("provider"));
                    published_at = (String)documentsElement.get("published_at");
                    model.addAttribute("straight" + editIndex + "published_at", published_at.substring(0,10));
                    straightIndex++;
                } else if (editIndex>=10) {
                    break;
                }

            }



            /*
            for(int i=0; i<documents.size(); i++){
                documentsElement = (JSONObject) documents.get(i);
                titlestr = (String) documentsElement.get("title");
                bylinestr = (String) documentsElement.get("byline");
                if(bylinestr == null)
                    bylinestr = "";

                model.addAttribute("title" + i, documentsElement.get("title"));
                model.addAttribute("title" + i + "provider", documentsElement.get("provider"));
                model.addAttribute("title" + i + "published_at", documentsElement.get("published_at").toString().substring(0,10));
                hilightFilter = (String) documentsElement.get("byline");
                model.addAttribute("title" + i + "byline", hilightFilter);
                model.addAttribute("title" + i + "hilight", documentsElement.get("hilight"));
                news_id = (String) documentsElement.get("news_id");
                model.addAttribute("title" + i + "codeprovider", news_id.substring(0,8));
                model.addAttribute("title" + i + "codedate", news_id.substring(9));*/

        } catch (ParseException e){
            e.printStackTrace();
        }


        return "search2";
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
*/