package com.xyz.hayhay.entirty;

import java.util.Calendar;

public class Program {
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
	String title;
	
	/**
	 * @return the time
	 */
	
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}
	/**
	 * @param programName the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	String time;
	String programName;
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
	 * @return the channelId
	 */
	public int getChannelId() {
		return channelId;
	}
	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	int id;
	int channelId;
	public Program(String time, String programName) {
		this.time = time;
		this.programName = programName;
	}
	String channelName;

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}
	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public boolean getRunning(){
		String hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + "" ;
		if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 10)
			hour = "0"+hour;
		if(time.startsWith(hour)){
			return true;
		}
		return false;
	}
}
