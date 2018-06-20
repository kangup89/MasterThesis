package com.kangup.dvpis.api;

import java.util.ArrayList;
import java.util.List;

public class Alarm {
	
	/**
	 */
	private long date;
	/**
	 */
	private int value;
	/**
	 */
	private int status;
	/**
	 */
	private String obs;
	
	/**
	 */
	private MeasurementType measurementType;

	/**
	 * @return
	 */
	public long getDate() {
		return date;
	}

	/**
	 * @param  date
	 */
	public void setDate(long date) {
		this.date = date;
	}

	/**
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param  value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param  status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return
	 */
	public String getObs() {
		return obs;
	}

	/**
	 * @param  obs
	 */
	public void setObs(String obs) {
		this.obs = obs;
	}

	/**
	 * @return
	 */
	public MeasurementType getMeasurementType() {
		return measurementType;
	}

	/**
	 * @param  measurementType
	 */
	public void setMeasurementType(MeasurementType measurementType) {
		this.measurementType = measurementType;
	}

	
}
