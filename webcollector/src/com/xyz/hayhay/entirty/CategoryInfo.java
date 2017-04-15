package com.xyz.hayhay.entirty;

import java.util.ArrayList;
import java.util.List;


public class CategoryInfo {
	
	public CategoryInfo(String name, String url, String image, String title, String desc, String keywords) {
		super();
		this.name = name;
		this.url = url;
		this.image = image;
		this.title = title;
		this.desc = desc;
		this.keywords = keywords;
		setWebsites(new ArrayList<WebsiteInfo>());
	}
	private List<WebsiteInfo> websites;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}
	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public List<WebsiteInfo> getWebsites() {
		return websites;
	}
	public void setWebsites(List<WebsiteInfo> websites) {
		this.websites = websites;
	}
	String name;
	String url;
	String image;
	String title;
	String desc;
	String keywords;
	
}
