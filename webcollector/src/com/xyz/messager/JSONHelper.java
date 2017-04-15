package com.xyz.messager;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
//import org.json.JSONObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONHelper {
	public static JSONObject toJSONObject(Object obj) throws JSONException{
		Gson gson =  new GsonBuilder().setPrettyPrinting().create();
		JSONObject json = null;
		try {
			json = (JSONObject)new JSONParser().parse(gson.toJson(obj));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return json;
	}
	public static JSONArray toJSONArray(Collection<?> list) throws JSONException{
		Gson gson =  new GsonBuilder().create();
		JSONArray ja = new JSONArray(gson.toJson(list));
		return ja;
	}
	public static Object fromJSONObject(Class<?> clazz, JSONObject json){
		Gson gson = new Gson();
        Object obj = gson.fromJson(json.toString(), clazz);
        return obj;
	}
	public static Object fromJSONObject(Class<?> clazz, String json){
		Gson gson = new Gson();
        Object obj = gson.fromJson(json, clazz);
        return obj;
	}
//	
//	public static List<?> fromJSONArray1(Class<?> clazz, JSONArray json) throws JSONException{
//		Gson gson = new Gson();
//		List objs = new ArrayList();
//		for(int i = 0;i<json.length();i++){
//			objs.add(fromJSONObject(clazz, json.getJSONObject(i)));
//		}
//        return objs;
//	}
}
