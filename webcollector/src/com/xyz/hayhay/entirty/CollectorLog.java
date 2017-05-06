package com.xyz.hayhay.entirty;

public class CollectorLog {
	
	public CollectorLog(int id, String name, int foundNews, int newNews, String url, String collectedTime, String status, String error) {
		super();
		this.id = id;
		this.name = name;
		this.foundNews = foundNews;
		this.newNews = newNews;
		this.url = url;
		this.collectedTime = collectedTime;
		this.status = status;
		this.error = error;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
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
	 * @return the foundNews
	 */
	public int getFoundNews() {
		return foundNews;
	}
	/**
	 * @param foundNews the foundNews to set
	 */
	public void setFoundNews(int foundNews) {
		this.foundNews = foundNews;
	}
	/**
	 * @return the newNews
	 */
	public int getNewNews() {
		return newNews;
	}
	/**
	 * @param newNews the newNews to set
	 */
	public void setNewNews(int newNews) {
		this.newNews = newNews;
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
	 * @return the collectedTime
	 */
	public String getCollectedTime() {
		return collectedTime;
	}
	/**
	 * @param collectedTime the collectedTime to set
	 */
	public void setCollectedTime(String collectedTime) {
		this.collectedTime = collectedTime;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
	int id;
	String name;
	int foundNews;
	int newNews;
	String url;
	String collectedTime;
	String status;
	String error;
}
