package com.xyz.hayhay.website.collector;

import java.util.ArrayList;
import java.util.List;

import com.xyz.hayhay.entirty.GiaVang;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class GoldCollector extends BasedCollector{
	public static List<GiaVang> giaVang = new ArrayList<GiaVang>();
	@Override
	public void processWebsiteContent() throws Exception {
		collectFromGiavangDoji();
	}
	private void collectFromGiavangDoji() {
		Source s;
		List<GiaVang> gv = new ArrayList<GiaVang>();
		try {
			s = getSource("http://giavang.doji.vn/trangchu.html");
			Element e = s.getElementById("block-block-1");
			Element table = e.getFirstElement("table");
			Element tbody =  table.getFirstElement("tbody");
			for(Element tr : tbody.getChildElements()){
				gv.add(new GiaVang(tr.getChildElements().get(0).getTextExtractor().toString(), tr.getChildElements().get(1).getTextExtractor().toString(), tr.getChildElements().get(2).getTextExtractor().toString()));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		GoldCollector.giaVang = gv;
	}
	@Override
	public String getCollectorName() {
		return "DojiGiaVangCollector";
	}
	public static void main(String[] args) {
		try {
			new GoldCollector().processWebsiteContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<GiaVang> getGiaVang() {
		return giaVang;
	}
}
