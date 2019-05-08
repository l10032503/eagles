package com.example.eagles.web;

import com.example.eagles.newsbigdata.Bigkinds;
import com.example.eagles.newsbigdata.Document;
import com.example.eagles.newsbigdata.NewsSearch;
import lombok.AllArgsConstructor;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class UnivEssayController {

    @GetMapping("/sookmyung")
    public String sookmyung(){
        return "sookmyung";
    }

    @GetMapping("/sookmyung-2019-01-01")
    public String sookmyung_2019_01_01(Model model){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String title_all = "";
        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        String resultString = "";
        Object obj = new Object();
        JSONArray documents = new JSONArray();
        JSONObject documentsElement = new JSONObject();
        NewsSearch newsSearch = new NewsSearch();
        Bigkinds bigkinds = new Bigkinds();
        Document document = new Document();

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
        List<String> news_ids_List = new ArrayList<String>();
        fields_List.add("title");
        fields_List.add("news_id");
        String title = "";
        String byline = "";
        String news_id = "";
        String searchQuery="";

        int editIndex = 0;
        int straightIndex = 0;

        String queryString = newsSearch.makeQuery("민주주의", "2018-11-15", simpleDateFormat.format(today),
                providerList,category_List,category_incident_List, "", provider_subject_List,
                subject_info_List, subject_info1_List, subject_info2_List, subject_info3_List,
                subject_info4_List, "date", "desc", 200, 0, 1000, fields_List).toString();
        resultString = bigkinds.postURL("http://tools.kinds.or.kr:8888/search/news",queryString);
        System.out.println(resultString);
        try{
            documents = document.makeDoumentElement(resultString);
            System.out.println(documents.toString());
            for(int i = 0; i<documents.size(); i++){
                documentsElement = (JSONObject) documents.get(i);
                title = (String) documentsElement.get("title");
                byline = (String) documentsElement.get("byline");
                if(byline == null)
                    byline = "";
                news_id = (String) documentsElement.get("news_id");

                if(title.contains("사설") || title.contains("칼럼") || byline.contains("사설") || byline.contains("칼럼")){
                    if(editIndex < 10){

                        model.addAttribute("edit" + editIndex + "title", documentsElement.get("title"));
                        model.addAttribute("edit" + editIndex + "provider", news_id.substring(0,8));
                        model.addAttribute("edit" + editIndex + "date", news_id.substring(9));
                        editIndex++;
                    }
                } else if (straightIndex < 10 ){
                    model.addAttribute("straight" + straightIndex + "title", documentsElement.get("title"));
                    model.addAttribute("straight" + straightIndex + "provider", news_id.substring(0,8));
                    model.addAttribute("straight" + straightIndex + "date", news_id.substring(9));
                    straightIndex++;
                } else if (editIndex>=10) {
                    break;
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return "sookmyung-2019-01-01";
    }
}
