package com.kangup.dvpis.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class SecondaryUser {
	
	@Id
	private String id;	
	private String username;
	private String password;
	private String name;
	private List<PrimaryUser> primaryUsers;
	private SystemInformation systemInformation;
	private List<MeasurementsOutput> measurementsOutput;
	private List<Reminder> reminders;
		
	public String getId(){
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public List<PrimaryUser> getPrimaryUsers() {
		return primaryUsers;
	}
	public void setPrimaryUsers(List<PrimaryUser> primaryUsers) {
		this.primaryUsers = primaryUsers;
	}
	
	public SystemInformation getSystemInformation() {
		return systemInformation;
	}
	
	public void setSystemInformation(SystemInformation systemInformation) {
		this.systemInformation = systemInformation;
	}
	public List<MeasurementsOutput> getMeasurementsOutput() {
		return measurementsOutput;
	}
	public void setMeasurementsOutput(List<MeasurementsOutput> measurementsOutput) {
		this.measurementsOutput = measurementsOutput;
	}   
	public List<Reminder> getReminders() {
		return reminders;
	}

	public void setReminders(List<Reminder> reminders) {
		this.reminders = reminders;
	}
	
	public void inputReminder(Reminder reminder){
		if(reminders == null) reminders = new ArrayList<Reminder>();
		reminders.add(reminder);
	}

}
