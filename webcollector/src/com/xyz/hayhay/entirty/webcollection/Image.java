package com.xyz.hayhay.entirty.webcollection;

public class Image extends HTMLElement{
	public Image(String elementName, String elementAttribute, String elementValue, boolean isGetText) {
		super(elementName, elementAttribute, elementValue, isGetText);
	}
	public Image() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getElementName() {
		if(elementName == null)
			return "img";
		else 
			return elementName;
	}
	@Override
	public String getValueFromAtttributeName() {
		if(valueFromAtttributeName == null)
			return "src";
		else
			return valueFromAtttributeName;
	}
}
