package com.xyz.hayhay.website.nonearticle.parser;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class XoSoKienThietParser{

	@SuppressWarnings("unchecked")
	public JSONArray collectArticle(Source source, String url, String fromWebsite) {
		Element kq = source.getAllElementsByClass("box-ketqua").get(0);
		List<Element> rows = kq.getAllElements("tr");
		JSONArray results = new JSONArray();
		int index = 0;
		
		for (Element row : rows) {
			if (row.getChildElements().size() > 3) {
				String result = row.getChildElements().get(1).getTextExtractor().toString();
				if(result != null && !result.trim().isEmpty()){
					JSONObject j = new JSONObject();
					j.put("name", row.getChildElements().get(0).getTextExtractor().toString());
					j.put("result", row.getChildElements().get(1).getTextExtractor().toString());
					j.put("first", row.getChildElements().get(2).getTextExtractor().toString());
					j.put("last", row.getChildElements().get(3).getTextExtractor().toString());
					results.add(index, j);
					index++;
				}
			}
		}
		return results;
	}

}
