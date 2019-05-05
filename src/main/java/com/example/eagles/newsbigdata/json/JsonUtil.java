package com.example.eagles.newsbigdata.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

    //Map to Json
    public static JSONObject getJsonStringFromMap( Map<String, Object> map )
    {
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }

        return jsonObject;
    }

    //Map to JsonArray
    public static JSONArray getJsonArrayFromList( List<Map<String, Object>> list )
    {
        JSONArray jsonArray = new JSONArray();
        for( Map<String, Object> map : list ) {
            jsonArray.add( getJsonStringFromMap( map ) );
        }

        return jsonArray;
    }

    //List<Map> to String
    public static String getJsonStringFromList( List<Map<String, Object>> list )
    {
        JSONArray jsonArray = getJsonArrayFromList( list );
        return jsonArray.toString();
    }

    //JsonObject to Map<String, String>
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromJsonObject( JSONObject jsonObj )
    {
        Map<String, Object> map = null;

        try {

            map = new ObjectMapper().readValue(jsonObj.toString(), Map.class) ;

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    //JsonArray to List<Map<String, String>>
    public static List<Map<String, Object>> getListMapFromJsonArray( JSONArray jsonArray )
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if( jsonArray != null )
        {
            int jsonSize = jsonArray.size();
            for( int i = 0; i < jsonSize; i++ )
            {
                Map<String, Object> map = JsonUtil.getMapFromJsonObject( ( JSONObject ) jsonArray.get(i) );
                list.add( map );
            }
        }

        return list;
    }

}
