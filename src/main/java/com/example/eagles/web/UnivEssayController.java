/*package com.example.eagles.web;

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
///@AllArgsConstructor
public class UnivEssayController {

    @GetMapping("/sookmyung")
    public String sookmyung(){
        return "sookmyung";
    }


    @GetMapping("/seogang")
    public String seogang(){
        return "seogang";
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
        String title = "";
        String byline = "";
        String news_id = "";
        String searchQuery="";

        fields_List.add("title");
        fields_List.add("news_id");
        category_List.add("정치>북한");
        category_List.add("정치>선거");
        category_List.add("정치>외교");
        category_List.add("정치>국회_정당");
        category_List.add("정치>행정_자치");
        category_List.add("정치>청와대");
        category_List.add("정치>정치일반");
        category_List.add("경제>무역");
        category_List.add("경제>외환");
        category_List.add("경제>유통");
        category_List.add("경제>자원");
        category_List.add("경제>산업_기업");
        category_List.add("경제>취업_창업");
        category_List.add("경제>국제경제");
        category_List.add("경제>경제일반");
        category_List.add("사회>여성");
        category_List.add("사회>환경");
        category_List.add("사회>교육_시험");
        category_List.add("사회>노동_복지");
        category_List.add("사회>사건_사고");
        category_List.add("사회>의료_건강");
        category_List.add("사회>미디어");
        category_List.add("사회>장애인");
        category_List.add("사회>사회일반");
        category_List.add("문화>생활");
        category_List.add("문화>영화");
        category_List.add("문화>음악");
        category_List.add("문화>종교");
        category_List.add("문화>출판");
        category_List.add("문화>미술_건축");
        category_List.add("문화>전시_공연");
        category_List.add("문화>문화일반");
        category_List.add("국제");

        int editIndex = 0;
        int straightIndex = 0;

        String queryString = newsSearch.makeQuery("민주주의 OR (다수의 AND 원칙) OR (소수의 AND 권리) OR 다수결 OR 투표 OR (토론의 AND 장) OR 공론장 OR 포퓰리즘",
                "2010-01-01", simpleDateFormat.format(today),
                providerList,category_List,category_incident_List, "", provider_subject_List,
                subject_info_List, subject_info1_List, subject_info2_List, subject_info3_List,
                subject_info4_List, "date", "desc", 200, 0, 100, fields_List).toString();
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

                if(title.contains("사설") || title.contains("칼럼") || byline.contains("사설") || byline.contains("칼럼") ||
                        title.contains("논설") || byline.contains("논설") ||
                        title.contains("논평") || byline.contains("논평") ||
                        title.contains("시평") || byline.contains("시평") ||
                        title.contains("시론") || byline.contains("시론") ||
                        title.contains("주석") || byline.contains("주석") ||
                        title.contains("논설위원") || byline.contains("논설위원")){
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

    @GetMapping("/seogang-2019-mock-test")
    public String seognag_2019_mock_test(Model model){
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
        String title = "";
        String byline = "";
        String news_id = "";
        String searchQuery="";

        fields_List.add("title");
        fields_List.add("news_id");
        category_List.add("정치>북한");
        category_List.add("정치>선거");
        category_List.add("정치>외교");
        category_List.add("정치>국회_정당");
        category_List.add("정치>행정_자치");
        category_List.add("정치>청와대");
        category_List.add("정치>정치일반");
        category_List.add("경제>무역");
        category_List.add("경제>외환");
        category_List.add("경제>유통");
        category_List.add("경제>자원");
        category_List.add("경제>산업_기업");
        category_List.add("경제>취업_창업");
        category_List.add("경제>국제경제");
        category_List.add("경제>경제일반");
        category_List.add("사회>여성");
        category_List.add("사회>환경");
        category_List.add("사회>교육_시험");
        category_List.add("사회>노동_복지");
        category_List.add("사회>사건_사고");
        category_List.add("사회>의료_건강");
        category_List.add("사회>미디어");
        category_List.add("사회>장애인");
        category_List.add("사회>사회일반");
        category_List.add("국제");
        category_List.add("지역");

        int editIndex = 0;
        int straightIndex = 0;


        String queryString = newsSearch.makeQuery("최저임금 OR 소득주도성장 OR 근로시간 OR 소득분배 OR (근로시간 AND 단축) OR 추가수당 OR 52시간 OR 부소득자 OR 소비자물가 OR 유가상승",
                "2010-01-01", simpleDateFormat.format(today),
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

                if(title.contains("사설") || title.contains("칼럼") || byline.contains("사설") || byline.contains("칼럼") ||
                        title.contains("논설") || byline.contains("논설") ||
                        title.contains("논평") || byline.contains("논평") ||
                        title.contains("시평") || byline.contains("시평") ||
                        title.contains("시론") || byline.contains("시론") ||
                        title.contains("주석") || byline.contains("주석") ||
                        title.contains("논설위원") || byline.contains("논설위원")){
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

        return "seogang-2019-01-01";
    }
}
*/