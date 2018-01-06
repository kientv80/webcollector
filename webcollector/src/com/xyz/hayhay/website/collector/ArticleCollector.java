package com.xyz.hayhay.website.collector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.webcollection.A;
import com.xyz.hayhay.entirty.webcollection.HTMLElement;
import com.xyz.hayhay.entirty.webcollection.Image;
import com.xyz.hayhay.entirty.webcollection.ShotDescription;
import com.xyz.hayhay.entirty.webcollection.Title;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public abstract class ArticleCollector {
	private long repeatTime;
	private long lastTimeProcessed;
	private boolean isCollecting;
	public boolean isCollecting() {
		return isCollecting;
	}

	public void setCollecting(boolean isCollecting) {
		this.isCollecting = isCollecting;
	}

	public ArticleCollector(long repeatTime) {
		this.setRepeatTime(repeatTime);
	}
	
	public long getRepeatTime() {
		return repeatTime;
	}
	public void setRepeatTime(long repeatTime) {
		this.repeatTime = repeatTime;
	}
	
	
	public abstract List<News> collectArticle(Source source, String url, String fromWebsite);
	public abstract String[] collectedUrls();
	
	protected boolean overwrite(){
		return false;
	}
	public long getLastTimeProcessed() {
		return lastTimeProcessed;
	}

	public void setLastTimeProcessed(long lastTimeProcessed) {
		this.lastTimeProcessed = lastTimeProcessed;
	}
	public void parseElementToNews(Element e, News news, A a, Title title, Image image, ShotDescription p) {
		String value = "";
		if (news.getUrl() == null || news.getUrl().isEmpty()) {
			value = getValue(e, a);
			if (value != null && !value.isEmpty()) {
				news.setUrl(a.getDomain() + value);
			}
		}
		if (!title.isIncludeAll()) {
			if (news.getTitle() == null || news.getTitle().isEmpty()) {
				value = getValue(e, title);
				if (value != null && !value.isEmpty()) {
					news.setTitle(value);
				}
			}
		} else {
			value = getValue(e, title);
			if (value != null && !value.isEmpty()) {
				if (news.getTitle() == null) {
					news.setTitle(value);
				} else {
					news.setTitle(news.getTitle() + "<br>" + value);
				}

			}
		}

		if (news.getImageUrl() == null || news.getImageUrl().isEmpty()) {
			value = getValue(e, image);
			if (value != null && !value.isEmpty() && value.indexOf("blank") < 0 && value.indexOf("pixel") < 0) {
				news.setImageUrl(image.getDomain() + value);
			}
		}
		if (p != null && !p.isIncludeAll()) {
			if (news.getShotDesc() == null || news.getShotDesc().isEmpty()) {
				value = getValue(e, p);
				if (value != null && !value.isEmpty()) {
					news.setShotDesc(value);
				}
			}
		} else if (p != null) {
			value = getValue(e, p);
			if (value != null && !value.isEmpty()) {
				news.setShotDesc(news.getShotDesc() + "<br>" + value);
			}
		}

		if (e.getChildElements().size() > 0) {
			for (Element c : e.getChildElements()) {
				parseElementToNews(c, news, a, title, image, p);
			}
		}
	}
	public String getValue(Element e, HTMLElement html) {
		if (isMach(e, html)) {
			String value = "";
			if (html.isGetText()) {
				value = e.getTextExtractor().toString();
			} else {
				value = e.getAttributeValue(html.getValueFromAtttributeName());
			}
			if (html.getExcludedTexts() != null) {
				for (String text : html.getExcludedTexts().split(",")) {
					if (text.equals(value))
						return null;
				}
			}
			return value;
		}
		return null;
	}
	protected boolean isMach(Element e, HTMLElement htmlElement) {
		if (htmlElement == null)
			return false;

		if (e.getName().equals(htmlElement.getElementName())) {
			if (htmlElement.getElementAttribute() != null && !htmlElement.getElementAttribute().isEmpty()) {
				if ((htmlElement.getElementValue() == null && htmlElement.getElementValue() == null) || (htmlElement.getElementValue() != null && htmlElement.getElementValue().equals(e.getAttributeValue(htmlElement.getElementAttribute()))) || (e.getAttributeValue(htmlElement.getElementAttribute())) != null && e.getAttributeValue(htmlElement.getElementAttribute()).equals(htmlElement.getElementValue())) {
					return true;
				} else {
					return false;
				}
			}
			if (htmlElement.getElementAttribute() == null) {
				// this mean just check name
				return true;
			}
		}
		return false;
	}
	public void test() throws Exception{
		for(String url : collectedUrls()){
			String website = getfromWeb(url);
			Source s = getSource(url);
			List<News> articles = collectArticle(s, url, website);
			
		}
	}
	private String getfromWeb(String url) {
		String website;
		String http = "http://";
		if (url.startsWith("https://"))
			http = "https://";
		if (url.indexOf("/", http.length()) > 0)
			website = url.substring(http.length(), url.indexOf("/", http.length()));
		else
			website = url.substring(http.length());
		if (website.startsWith("m.")) {
			website = website.substring("m.".length());
		} else if (website.startsWith("www.")) {
			website = website.substring("www.".length());
		}
		return website;
	}

	private Source getSource(String url) throws Exception {
		StringBuilder content = new StringBuilder();
		try {
			Parser pa = new Parser(url);
			NodeIterator no = pa.elements();

			while (no.hasMoreNodes()) {
				content.append(no.nextNode().toHtml());
			}
		} catch (Exception e) {
			content = loadContentByCurl(url);
		}
		Source s = new Source(content);
		return s;
	}

	private StringBuilder loadContentByCurl(String url) throws Exception {
		Process process = Runtime.getRuntime().exec("curl " + url);

		StringBuilder content = new StringBuilder();
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

		String line = null;

		while ((line = input.readLine()) != null) {
			content.append(line).append("\n");
		}
		// System.out.println(content);
		return content;
	}

}
