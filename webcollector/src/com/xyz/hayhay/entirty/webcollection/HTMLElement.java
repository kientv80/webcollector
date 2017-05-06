package com.xyz.hayhay.entirty.webcollection;

public class HTMLElement {
	public HTMLElement() {
		// TODO Auto-generated constructor stub
	}
	public boolean isIncludeAll() {
		return includeAll;
	}
	public void setIncludeAll(boolean includeAll) {
		this.includeAll = includeAll;
	}
	private boolean includeAll = false;
	/**
	 * @return the elementName
	 */
	public String getElementName() {
		return elementName;
	}
	/**
	 * @param elementName the elementName to set
	 */
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	/**
	 * @return the elementAttribute
	 */
	public String getElementAttribute() {
		return elementAttribute;
	}
	/**
	 * @param elementAttribute the elementAttribute to set
	 */
	public void setElementAttribute(String elementAttribute) {
		this.elementAttribute = elementAttribute;
	}
	/**
	 * @return the elementValue
	 */
	public String getElementValue() {
		return elementValue;
	}
	/**
	 * @param elementValue the elementValue to set
	 */
	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}
	/**
	 * @return the getText
	 */
	public boolean isGetText() {
		return getText;
	}
	/**
	 * @param getText the getText to set
	 */
	public void setGetText(boolean getText) {
		this.getText = getText;
	}
	String elementName;
	String elementAttribute;
	String elementValue;
	String valueFromAtttributeName;
	/**
	 * @return the valueFromAtttributeName
	 */
	public String getValueFromAtttributeName() {
		return valueFromAtttributeName;
	}
	/**
	 * @param valueFromAtttributeName the valueFromAtttributeName to set
	 */
	public void setValueFromAtttributeName(String valueFromAtttributeName) {
		this.valueFromAtttributeName = valueFromAtttributeName;
	}
	boolean getText;
	String domain = "";
	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public HTMLElement(String elementName,String elementAttribute,String elementValue, boolean isGetText) {
		this.elementName = elementName;
		this.elementAttribute = elementAttribute;
		this.elementValue = elementValue;
		this.getText = isGetText;
	}
	public String getSelector() {
		return selector;
	}
	public void setSelector(String selector) {
		this.selector = selector;
	}
	public String getExcludedTexts() {
		return excludedTexts;
	}
	public void setExcludedTexts(String excludedTexts) {
		this.excludedTexts = excludedTexts;
	}
	public int getMinLength() {
		return minLength;
	}
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
	private String selector;
	private String excludedTexts;
	private int minLength;
}
