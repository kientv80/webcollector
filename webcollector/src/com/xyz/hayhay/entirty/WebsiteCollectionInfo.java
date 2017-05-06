package com.xyz.hayhay.entirty;

import java.util.HashMap;
import java.util.Map;

public class WebsiteCollectionInfo {
	public long getCollectorStartTime() {
		return collectorStartTime;
	}
	public void setCollectorStartTime(long collectorStartTime) {
		this.collectorStartTime = collectorStartTime;
	}
	public long getCollectorEndTime() {
		return collectorEndTime;
	}
	public void setCollectorEndTime(long collectorEndTime) {
		this.collectorEndTime = collectorEndTime;
	}
	public String getCollectorName() {
		return collectorName;
	}
	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}
	public Map<String,UrlCollectionInfo> getUrlCollectorInfo() {
		return urlCollectorInfo;
	}
	public void setUrlCollectorInfo(Map<String,UrlCollectionInfo> urlCollectorInfo) {
		this.urlCollectorInfo = urlCollectorInfo;
	}
	long collectorStartTime;
	long collectorEndTime;
	String collectorName;
	Map<String,UrlCollectionInfo> urlCollectorInfo = new HashMap<>();
}
