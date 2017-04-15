package com.xyz.hayhay.website.collector;

public class GoldChart {
	/**
	 * @return the nyGold
	 */
	public String getNyGold() {
		return nyGold;
	}
	/**
	 * @param nyGold the nyGold to set
	 */
	public void setNyGold(String nyGold) {
		this.nyGold = nyGold;
	}
	/**
	 * @return the gold
	 */
	public String getGold() {
		return gold;
	}
	/**
	 * @param gold the gold to set
	 */
	public void setGold(String gold) {
		this.gold = gold;
	}
	String nyGold;
	String gold;
	public GoldChart() {
		nyGold = "http://www.kitco.com/images/live/nygold.gif?" + System.nanoTime();
		gold = "http://www.kitco.com/images/live/gold.gif?" + System.nanoTime();
	}
}
