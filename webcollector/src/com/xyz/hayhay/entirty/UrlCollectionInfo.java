package com.xyz.hayhay.entirty;

public class UrlCollectionInfo {
	public UrlCollectionInfo(String url, int collectedCount, int storedCount, long collectedTime, String status) {
		super();
		this.url = url;
		this.collectedCount = collectedCount;
		this.storedCount = storedCount;
		this.collectedTime = collectedTime;
		this.status = status;
		
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getCollectedCount() {
		return collectedCount;
	}
	public void setCollectedCount(int collectedCount) {
		this.collectedCount = collectedCount;
	}
	public int getStoredCount() {
		return storedCount;
	}
	public void setStoredCount(int storedCount) {
		this.storedCount = storedCount;
	}
	public long getCollectedTime() {
		return collectedTime;
	}
	public void setCollectedTime(long collectedTime) {
		this.collectedTime = collectedTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	String url;
	int collectedCount;
	int storedCount;
	long collectedTime;
	String status;
	String errorMsg;
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
