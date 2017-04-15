package com.xyz.hayhay.website.collector.tv;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xyz.hayhay.entirty.Channel;
import com.xyz.hayhay.entirty.Program;
import com.xyz.hayhay.entirty.TVStation;
import com.xyz.hayhay.website.collector.BasedCollector;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

public class VTVProgramCollector extends BasedCollector {

	@Override
	public void processWebsiteContent() throws Exception {
		Source source = new Source(new URL("http://vtv.vn/lich-phat-song.htm"));
		List<Element> luElements = source.getAllElements(HTMLElementName.UL);
		List<String> listChannels = new ArrayList<>();
		Map<Integer, List<Program>> map = new HashMap<Integer, List<Program>>();
		int count = 0;
		for (Element channals : luElements) {
			if ("programs".equals(channals.getAttributeValue("class"))) {
				List<Program> pros = new ArrayList<>();
				for (Element program : channals.getChildElements()) {
					if(program.getChildElements().size() == 3){
						Program p = new Program(null, null);
						p.setTime(program.getChildElements().get(0).getContent().getTextExtractor().toString());
						p.setTitle(program.getChildElements().get(1).getContent().getTextExtractor().toString());
						p.setProgramName(program.getChildElements().get(2).getContent().getTextExtractor().toString());
						
						pros.add(p);
					}
					
				}
				map.put(count, pros);
				count++;

			} else if ("list-channel".equals(channals.getAttributeValue("class"))) {
				List<Element> channal = channals.getAllElements(HTMLElementName.A);
				for (Element ch : channal) {
					listChannels.add(ch.getAttributeValue("title"));
				}
			}
		}
		List<Channel> channels = new ArrayList<>();
		String a = "";
		for(int i = 0; i < listChannels.size();i++){
			Channel c = new Channel();
			c.setId(i);
			a = listChannels.get(i);
			c.setName(a.substring(a.indexOf("VTV"),a.length()));
			c.setPrograms(map.get(i));
			channels.add(c);
		}
		TVStation vtvStation = new TVStation(0, "VTV");
		vtvStation.setChannels(channels);
		storeTVProgram(vtvStation);
	}

	public static void main(String[] args) {
		try {
			VTVProgramCollector vtv = new VTVProgramCollector();
			vtv.run();
		} catch (Exception ex) {
			
		}
	}

	@Override
	public String getCollectorName() {
		return "VTVProgramCollector";
	}

}
