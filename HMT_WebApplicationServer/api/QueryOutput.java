package com.kangup.dvpis.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class QueryOutput {
	/**
	 */
	@Id
	private String id;
	
	/**
	 */
	private String username;
	/**
	 */
	private String password;
	/**
	 */
	private String name;
	
	/**
	 */
	private int[] output_measurements;
	/**
	 */
	private String[] output_primaryUserNames;
	/**
	 */
	private SystemInformation systemInformation;
	/**
	 */
	private ArrayList<Alarm> output_alarms;
	
	private List<Reminder> reminders;

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param  id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param  username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param  password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

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
	public int[] getOutput_measurements() {
		return output_measurements;
	}

	/**
	 * @param  output_measurements
	 */
	public void setOutput_measurements(int[] output_measurements) {
		this.output_measurements = output_measurements;
	}

	/**
	 * @return
	 */
	public String[] getOutput_primaryUserNames() {
		return output_primaryUserNames;
	}

	/**
	 * @param  output_primaryUserNames
	 */
	public void setOutput_primaryUserNames(String[] output_primaryUserNames) {
		this.output_primaryUserNames = output_primaryUserNames;
	}

	/**
	 * @return
	 */
	public SystemInformation getSystemInformation() {
		return systemInformation;
	}

	/**
	 * @param  systemInformation
	 */
	public void setSystemInformation(SystemInformation systemInformation) {
		this.systemInformation = systemInformation;
	}

	public ArrayList<Alarm> getOutput_alarms() {
		return output_alarms;
	}

	public void setOutput_alarms(ArrayList<Alarm> output_alarms) {
		this.output_alarms = output_alarms;
	}
	
	public List<Reminder> getReminders() {
		return reminders;
	}

	public void setReminders(List<Reminder> reminders) {
		this.reminders = reminders;
	}

	
}
