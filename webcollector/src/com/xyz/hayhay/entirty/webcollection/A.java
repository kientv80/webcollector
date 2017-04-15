package com.xyz.hayhay.entirty.webcollection;

public class A extends HTMLElement{
	public A(String elementName, String elementAttribute, String elementValue, boolean isGetText) {
		super(elementName, elementAttribute, elementValue, isGetText);
	}
	public A() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getElementName() {
		return "a";
	}
	@Override
	public String getValueFromAtttributeName() {
		return "href";
	}
}
