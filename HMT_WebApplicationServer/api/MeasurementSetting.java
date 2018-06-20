package com.kangup.dvpis.api;

import org.springframework.data.annotation.Id;

public class MeasurementSetting {
	
	/*@Id
	int group;
	String description;
	long startDate;
	long lastDate;
	
	public int getGroup() {
		return group;
	}
	public void setGroup(int id) {
		this.group = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getStartDate() {
		return startDate;
	}
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	public long getLastDate() {
		return lastDate;
	}
	public void setLastDate(long lastDate) {
		this.lastDate = lastDate;
	}
*/
	
	/**
	 */
	int[] groups;
	/**
	 */
	String[] descriptions;
	/**
	 */
	long[] startDates;
	/**
	 */
	long[] lastDates;
	
	public MeasurementSetting(int size){
		groups = new int[size];
		descriptions = new String[size];
		startDates = new long[size];
		lastDates = new long[size];
	}
	
	/**
	 * @return
	 */
	public int[] getGroups() {
		return groups;
	}
	/**
	 * @param  groups
	 */
	public void setGroups(int[] groups) {
		this.groups = groups;
	}
	/**
	 * @return
	 */
	public String[] getDescriptions() {
		return descriptions;
	}
	/**
	 * @param  descriptions
	 */
	public void setDescriptions(String[] descriptions) {
		this.descriptions = descriptions;
	}
	/**
	 * @return
	 */
	public long[] getStartDates() {
		return startDates;
	}
	/**
	 * @param  startDates
	 */
	public void setStartDates(long[] startDates) {
		this.startDates = startDates;
	}
	/**
	 * @return
	 */
	public long[] getLastDates() {
		return lastDates;
	}
	/**
	 * @param  lastDates
	 */
	public void setLastDates(long[] lastDates) {
		this.lastDates = lastDates;
	}
	
}
