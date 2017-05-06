package com.xyz.hayhay.entirty;

public class GiaVang{
	String location;
	String buy;
	String salse;
	
	
	public GiaVang(String location, String buy, String salse) {
		super();
		this.location = location;
		this.buy = buy;
		this.salse = salse;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the buy
	 */
	public String getBuy() {
		return buy;
	}
	/**
	 * @param buy the buy to set
	 */
	public void setBuy(String buy) {
		this.buy = buy;
	}
	/**
	 * @return the salse
	 */
	public String getSalse() {
		return salse;
	}
	/**
	 * @param salse the salse to set
	 */
	public void setSalse(String salse) {
		this.salse = salse;
	}
	 
}