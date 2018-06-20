package com.kangup.dvpis.api;

import org.springframework.data.annotation.Id;

public class MeasurementType {

	
	//private int id;
	/**
	 */
	private int group;
	/**
	 */
	private String name;
	/**
	 */
	private String description;
	/**
	 */
	private String measurementUnits;
	
	/*public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}*/
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param  name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param  description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return
	 */
	public String getMeasurementUnits() {
		return measurementUnits;
	}
	/**
	 * @param  measurementUnits
	 */
	public void setMeasurementUnits(String measurementUnits) {
		this.measurementUnits = measurementUnits;
	}
	/**
	 * @return
	 */
	public int getGroup() {
		return group;
	}
	/**
	 * @param  group
	 */
	public void setGroup(int group) {
		this.group = group;
	}
	
	 
}
