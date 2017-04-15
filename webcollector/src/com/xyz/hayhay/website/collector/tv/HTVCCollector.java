package com.xyz.hayhay.website.collector.tv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.xyz.hayhay.entirty.Channel;
import com.xyz.hayhay.entirty.Program;
import com.xyz.hayhay.entirty.TVStation;
import com.xyz.hayhay.website.collector.BasedCollector;

public class HTVCCollector extends BasedCollector {
	private static String PREFIX = "\\u";
	private List<Channel> listChannels = new ArrayList<>();
	{
		listChannels.add(new Channel(127, "FBNC HD"));
		listChannels.add(new Channel(10, "HTV1"));
		listChannels.add(new Channel(11, "HTV2"));
		listChannels.add(new Channel(12, "HTV3"));
		listChannels.add(new Channel(13, "HTV4"));
		listChannels.add(new Channel(15, "HTV7"));
		listChannels.add(new Channel(16, "HTV9"));
		listChannels.add(new Channel(17, "HTVC + (Channel B)"));
		listChannels.add(new Channel(20, "HTVC - Du lịch & Cuộc sống"));
		listChannels.add(new Channel(7, "HTVC - Gia Đình"));
		listChannels.add(new Channel(8, "HTVC - Phim"));
		listChannels.add(new Channel(132, "HTVC - Phim HD"));
		listChannels.add(new Channel(6, "HTVC - Phụ Nữ"));
		listChannels.add(new Channel(4, "HTVC - Thuần Việt"));
		listChannels.add(new Channel(5, "HTVC - Thuần Việt HD"));
		listChannels.add(new Channel(21, "HTV Thể thao"));
		listChannels.add(new Channel(53, "Cartoon Network"));
	}

	@Override
	public void processWebsiteContent() throws Exception {
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
		String date = d.format(Calendar.getInstance().getTime());
		String tokenCookie = "";
		try {
			URL url1 = new URL("http://htvc.vn/blocks/load_program_in_date");
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			Map<String, List<String>> headerFields = conn.getHeaderFields();
			for (String key : headerFields.keySet()) {
				if ("Set-Cookie".equalsIgnoreCase(key)) {
					for (String value : headerFields.get(key)) {
						if (value.contains("token_cookie")) {
							tokenCookie = value.substring(value.indexOf("token_cookie") + "token_cookie".length() + 1, "030153c2a6aa4791f900c515eefbc1cb".length());
						}
					}
				}
			}
			conn.disconnect();
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
		}
		System.out.println(tokenCookie);
		if (tokenCookie == null || tokenCookie.isEmpty())
			tokenCookie = "207ceb43c9a5fdd2726ce9e68309dc4f";

		for (Channel c : listChannels) {
			try {
				URL url = new URL("http://htvc.vn/blocks/load_program_in_date");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				connection.setRequestProperty("Cookie", "token_cookie=" + tokenCookie);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("page", "lps"));
				params.add(new BasicNameValuePair("token", tokenCookie));
				params.add(new BasicNameValuePair("date", date));
				params.add(new BasicNameValuePair("channel_id", c.getId() + ""));
				connection.setDoInput(true);
				connection.setDoOutput(true);

				// Send request
				OutputStream os = connection.getOutputStream();
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
				writer.write(getQuery(params));
				writer.flush();
				writer.close();
				os.close();
				// parse response
				BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String c1 = "";
				while (r.read() > 0) {
					c1 = r.readLine();
					c1 = c1.replace("\\n", "");
					c1 = c1.replace("\\t", "");
					c1 = c1.replace("\\\"", "\"");
				}
				String content = ascii2Native(c1).replace("\\", "");
				Source s = new Source(content);
				List<Element> programs = s.getAllElementsByClass("all-mcl");
				if (programs != null) {
					for (Element pro1 : programs) {
						if (pro1.getChildElements() != null) {
							for (Element pro : pro1.getChildElements()) {
								if (pro != null && pro.getChildElements().size() > 0 && pro.getChildElements().get(0).getChildElements().size() >= 2) {
									String time = pro.getChildElements().get(0).getChildElements().get(0).getTextExtractor().toString();
									String name = pro.getChildElements().get(0).getChildElements().get(1).getTextExtractor().toString();
									Program p = new Program(time, "");
									p.setTitle(ascii2Native(name));
									c.getPrograms().add(p);
								}
							}
						}
					}
				}

				connection.disconnect();
			} catch (Exception e) {
				log.error("", e);
				e.printStackTrace();
			}
		}
		TVStation htv = new TVStation(3, "HTVC");
		htv.setChannels(listChannels);
		storeTVProgram(htv);
	}

	public static String ascii2Native(String str) {
		StringBuilder sb = new StringBuilder();
		int begin = 0;
		int index = str.indexOf(PREFIX);
		while (index != -1) {
			sb.append(str.substring(begin, index));
			sb.append(ascii2Char(str.substring(index, index + 6)));
			begin = index + 6;
			index = str.indexOf(PREFIX, begin);
		}
		sb.append(str.substring(begin));
		return sb.toString();
	}

	private static char ascii2Char(String str) {
		if (str.length() != 6) {
			throw new IllegalArgumentException("Ascii string of a native character must be 6 character.");
		}
		if (!PREFIX.equals(str.substring(0, 2))) {
			throw new IllegalArgumentException("Ascii string of a native character must start with \"\\u\".");
		}
		String tmp = str.substring(2, 4);
		int code = Integer.parseInt(tmp, 16) << 8;
		tmp = str.substring(4, 6);
		code += Integer.parseInt(tmp, 16);
		return (char) code;
	}

	public static void main(String[] args) {
		try {
			HTVCCollector vtv = new HTVCCollector();
			vtv.run();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public String getCollectorName(){
		return "HTVCCollector";
	}

}
