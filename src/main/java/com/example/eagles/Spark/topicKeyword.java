package com.example.eagles.Spark;

import com.example.eagles.newsbigdata.Bigkinds;
import com.example.eagles.newsbigdata.IssueRanking;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class topicKeyword {

    public String make_year_topic_keyword_all(Calendar start, Calendar end, IssueRanking issueRanking, Bigkinds bigkinds,
                                               JSONParser jsonParser, SimpleDateFormat formatter){
        String year_topic_keyword_all = "";
        JSONObject jsonObject = new JSONObject();
        String issueQuery = "";
        Object obj = new Object();
        JSONArray topics = new JSONArray();
        JSONObject topicElement = new JSONObject();
        List<String> providerList = new ArrayList<String>();

        try{
            for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                jsonObject = issueRanking.makeIssue(formatter.format(date), providerList);
                issueQuery = bigkinds.postURL("http://tools.kinds.or.kr:8888/issue_ranking",jsonObject.toString());
                obj = jsonParser.parse(issueQuery);
                jsonObject = (JSONObject) obj;
                JSONObject return_object = new JSONObject();
                return_object = (JSONObject) jsonObject.get("return_object");
                topics = (JSONArray) return_object.get("topics");
                for(int i=0; i < 10 ;i++){
                    topicElement = (JSONObject) topics.get(i);
                    year_topic_keyword_all = year_topic_keyword_all + topicElement.get("topic_keyword") + ",";
                }
            }
        }catch (ParseException e){
            e.printStackTrace();
        }

        return year_topic_keyword_all;
    }
}
