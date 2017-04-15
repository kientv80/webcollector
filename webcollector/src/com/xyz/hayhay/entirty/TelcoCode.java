package com.xyz.hayhay.entirty;

public class TelcoCode {
	
	public TelcoCode(int id, String telco, String code, int value) {
		super();
		this.id = id;
		this.telco = telco;
		this.code = code;
		this.value = value;
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
	 * @return the telco
	 */
	public String getTelco() {
		return telco;
	}
	/**
	 * @param telco the telco to set
	 */
	public void setTelco(String telco) {
		this.telco = telco;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	int id;
	String telco;
	String code;
	int value;
}
