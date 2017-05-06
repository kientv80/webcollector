package com.xyz.hayhay.entirty;

import java.util.ArrayList;
import java.util.List;


public class Channel {
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
	 * @return the programs
	 */
	public List<Program> getPrograms() {
		return programs;
	}
	/**
	 * @param programs the programs to set
	 */
	public void setPrograms(List<Program> programs) {
		this.programs = programs;
	}
	int id;
	String name;
	List<Program> programs = new ArrayList<>();
	public Channel(){}
	public Channel(int id, String name){
		this.id = id;
		this.name = name;
	}
}
