package com.xyz.hayhay.entirty;

import java.util.List;

public class Website {
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
	 * @return the news
	 */
	public List<News> getNews() {
		return news;
	}
	/**
	 * @param news the news to set
	 */
	public void setNews(List<News> news) {
		this.news = news;
	}
	public boolean isOverwrite() {
		return overwrite;
	}
	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}
	String name;
	List<News> news;
	private boolean overwrite;
}
