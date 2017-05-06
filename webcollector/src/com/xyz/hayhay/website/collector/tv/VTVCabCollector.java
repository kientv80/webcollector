package com.xyz.hayhay.website.collector.tv;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.xyz.hayhay.entirty.Channel;
import com.xyz.hayhay.entirty.Program;
import com.xyz.hayhay.entirty.TVStation;
import com.xyz.hayhay.website.collector.BasedCollector;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

public class VTVCabCollector extends BasedCollector {

	@Override
	public void processWebsiteContent() throws Exception {
		Source source = new Source(new URL("http://www.vtvcab.vn/lich-phat-song"));
		Element channelElement = source.getElementById("channel");
		List<Element> channelList = channelElement.getChildElements();
		List<Channel> channels = new ArrayList<>();
		String day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"";
		if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) < 10)
			day = "0"+day;
		String month = (Calendar.getInstance().get(Calendar.MONTH) + 1)+"" ;
		if((Calendar.getInstance().get(Calendar.MONTH) + 1)<10)
			month = "0" + month;
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for (Element channal : channelList) {
			Channel c = new Channel();
			c.setName(channal.getContent().getTextExtractor().toString());
			c.setId(Integer.valueOf(channal.getAttributeValue("value")));
			channels.add(c);
				Source source1 = new Source(new URL("http://www.vtvcab.vn/lich-phat-song?day=" + day + "&month=" + month + "&year=" + year + "&channel=" + c.getId() + ""));
				List<Element> tbPrograms = source1.getAllElements(HTMLElementName.TBODY).get(0).getChildElements();
				for (Element row : tbPrograms) {
					if (row.getChildElements().size() == 3) {
						Program p = new Program(row.getChildElements().get(0).getTextExtractor().toString(), row.getChildElements().get(2).getTextExtractor().toString());
						p.setTitle(row.getChildElements().get(1).getTextExtractor().toString());
						c.getPrograms().add(p);
					}
				}

		}
		TVStation vtvCab = new TVStation(2, "VTVCab");
		vtvCab.setChannels(channels);

		storeTVProgram(vtvCab);
	}

	public static void main(String[] args) {
		try {
			VTVCabCollector vtv = new VTVCabCollector();
			vtv.run();
		} catch (Exception ex) {

		}
	}

	@Override
	public String getCollectorName() {
		return "VTVCabCollector";
	}

}
