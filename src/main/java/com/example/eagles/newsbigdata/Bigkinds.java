package com.example.eagles.newsbigdata;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Repository
public class Bigkinds {

    public String postURL(String sendUrl, String jsonValue) throws IllegalStateException{
        String inputLine = null;
        StringBuffer outResult = new StringBuffer();
        try{
            System.out.println("REST APT Start");
            URL url = new URL(sendUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            OutputStream os = conn.getOutputStream();
            os.write(jsonValue.getBytes("UTF-8"));
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((inputLine = in.readLine()) != null){
                outResult.append(inputLine);
            }

            conn.disconnect();
            System.out.println("REST API END");
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return outResult.toString();
    }

    /*public JSONObject makeSearchQuery(String test){
        JSONObject sendObject = new JSONObject();
        JSONObject argument = new JSONObject();

       try{
           argument.put("query","검색 키워드");
           JSONObject published_at = new JSONObject();
                published_at.put("from", "2015-01-01");
                published_at.put("until", "2016-01-31");
           argument.put("published_at",published_at);
           JSONArray provider = new JSONArray();
                provider.add("경향신문");
       }catch (Exception e){
           e.printStackTrace();
       }

        return sendObject;
    }

    public String newsSearchController(String jsonValue) throws IllegalStateException{
        String inputLine = null;
        StringBuffer outResult = new StringBuffer();

        try{
            System.out.println("REST APT Start");
            URL url = new URL("http://tools.kinds.or.kr:8888/search/news");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            OutputStream os = conn.getOutputStream();
            os.write(jsonValue.getBytes("UTF-8"));
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((inputLine = in.readLine()) != null){
                outResult.append(inputLine);
            }

            conn.disconnect();
            System.out.println("REST API END");
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return outResult.toString();
    }*/

}
