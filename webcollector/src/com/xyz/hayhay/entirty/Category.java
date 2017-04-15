package com.xyz.hayhay.entirty;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private List<News> news;
	private String name;
	private String cateId;
	private String backGroudColor;
	private List<CategoryInfo> subcates;


	public void addNews(News n){
		if(news == null)
			news = new ArrayList<>();
		news.add(n);
	}
	/**
	 * @return the news
	 */
	public List<News> getNews() {
		if(news == null)
			news = new ArrayList<News>();
		return news;
	}
	/**
	 * @param news the news to set
	 */
	public void setNews(List<News> news) {
		this.news = news;
	}

	public Category(String id){
		this.cateId = id;
	}
	public Category(List<News> news, String name, String cateId) {
		this.news = news;
		this.name = name;
		this.cateId = cateId;
		backGroudColor = ColorPicker.getInstance().getColor(cateId);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	int longestTileLength;
	public int getLongestTileLength(){
		if(longestTileLength != 0)
			return longestTileLength;
		
		News n0 = getNews().get(0);
		longestTileLength = n0.getTitle().length();
		for(News n : getNews()){
			if(n.getTitle().length()  > longestTileLength){
				longestTileLength = n.getTitle().length();
			}
		}
		return longestTileLength;
	}
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	public String getBackGroudColor() {
		return backGroudColor;
	}
	public void setBackGroudColor(String backGroudColor) {
		this.backGroudColor = backGroudColor;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Category) {
			Category tmp = (Category) obj;
			return this.getCateId().equals(tmp.getCateId());
		}
		return false;
	}

	public List<CategoryInfo> getSubcates() {
		return subcates;
	}
	public void setSubcates(List<CategoryInfo> subcates) {
		this.subcates = subcates;
	}
}
