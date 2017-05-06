package com.xyz.hayhay.website.collector.tv;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import com.xyz.hayhay.entirty.Channel;
import com.xyz.hayhay.entirty.Program;
import com.xyz.hayhay.entirty.TVStation;
import com.xyz.hayhay.website.collector.BasedCollector;

public class HaNoiTVCollector extends BasedCollector {

	@Override
	public void processWebsiteContent() throws Exception {
		Source source = getSource("http://hanoitv.vn/Lich-phat-song/76.htv");
		Element channelElement = source.getElementById("ctl00_bodyContent_ddlChanel");
		List<Element> channelList = channelElement.getChildElements();
		List<Channel> channels = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		String containerId = sdf.format(Calendar.getInstance().getTime());
		for (Element channal : channelList) {
			if ("Truyền hình HTV1".equals(channal.getTextExtractor().toString()) || "Truyền hình HTV2".equals(channal.getTextExtractor().toString())) {
				Channel c = new Channel();
				c.setName(channal.getContent().getTextExtractor().toString());
				c.setId(0);
				channels.add(c);
				try {
					Source source1 = getSource("http://hanoitv.vn/"+channal.getAttributeValue("value"));
					Element div = source1.getElementById("t" + containerId);
					List<Element> tbPrograms = div.getAllElements(HTMLElementName.TR);
					String time = "";
					String proName = "";
					for (Element row : tbPrograms) {
						List<Element> pros = row.getAllElements(HTMLElementName.P);
						if (pros != null && pros.size() > 0) {
							for (Element p : pros) {
								if(p.getTextExtractor().toString().indexOf(":") > 0){
									proName = "";
									time = p.getTextExtractor().toString().substring(0, p.getTextExtractor().toString().indexOf(":"));
									time= time.replace("h", ":");
									if(time.length()==4){
										time = "0"+time;
									}
									if(p.getTextExtractor().toString().indexOf(":") + 1 < p.getTextExtractor().toString().length()){
										proName = p.getTextExtractor().toString().substring(p.getTextExtractor().toString().indexOf(":") + 1, p.getTextExtractor().toString().length());
									}else{
										proName = p.getTextExtractor().toString();
									}
									if(proName != null && !proName.isEmpty()){
										Program p2 = new Program(time, "");
										p2.setTitle(proName);
										c.getPrograms().add(p2);
									}
								}
							}
						}
					}
				} catch (MalformedURLException e) {
					log.error("", e);
				} catch (IOException e) {
					log.error("", e);
				}

			}
		}
		TVStation hntv = new TVStation(2, "HNTV");
		hntv.setChannels(channels);
		storeTVProgram(hntv);
	}

	@Override
	public String getCollectorName(){
		return "HaNoiTVCollector";
	}
public static void main(String[] args) {
	try {
		new HaNoiTVCollector().processWebsiteContent();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
