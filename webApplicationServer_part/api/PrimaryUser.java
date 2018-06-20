package com.kangup.dvpis.api;

import java.util.ArrayList;
import java.util.List;

public class PrimaryUser {
	
	/**
	 */
	private String name;
	/**
	 */
	private List<Alarm> alarms;
	/**
	 */
	private List<Measurement> measurements;
	
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
	public List<Alarm> getAlarms() {
		return alarms;
	}
	public void setAlarms(ArrayList<Alarm> alarms) {
		this.alarms = alarms;
	}
	public List<Measurement> getMeasurements() {
		return measurements;
	}
	public void setMeasurements(ArrayList<Measurement> measurements) {
		this.measurements = measurements;
	}

	
}
