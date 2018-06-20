package com.kangup.dvpis.api;

import java.util.ArrayList;
import java.util.List;

public class Measurement {
	
	/**
	 */
	private Long date;
	/**
	 */
	private int value;
	/**
	 */
	private MeasurementType measurementType;
	
	/**
	 * @return
	 */
	public Long getDate() {
		return date;
	}
	/**
	 * @param  date
	 */
	public void setDate(Long date) {
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
	public MeasurementType getMesurementType() {
		return measurementType;
	}
	public void setMesurementTypes(MeasurementType mesurementType) {
		this.measurementType = mesurementType;
	}

}
