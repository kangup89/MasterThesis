package com.kangup.dvpis.api;

public class SystemInformation {
	
	/**
	 */
	private long lastLogonDate;
	/**
	 */
	private long lastLogoutDate;
	/**
	 */
	private long sessionExpirationDate;
	/**
	 */
	private String techSupportNumber;
	/**
	 */
	private String routerSerialNumber;
	/**
	 */
	private String button;
	
	/**
	 * @return
	 */
	public long getLastLogonDate() {
		return lastLogonDate;
	}
	/**
	 * @param  lastLogonDate
	 */
	public void setLastLogonDate(long lastLogonDate) {
		this.lastLogonDate = lastLogonDate;
	}
	/**
	 * @return
	 */
	public long getLastLogoutDate() {
		return lastLogoutDate;
	}
	/**
	 * @param  lastLogoutDate
	 */
	public void setLastLogoutDate(long lastLogoutDate) {
		this.lastLogoutDate = lastLogoutDate;
	}
	/**
	 * @return
	 */
	public long getSessionExpirationDate() {
		return sessionExpirationDate;
	}
	/**
	 * @param  sessionExpirationDate
	 */
	public void setSessionExpirationDate(long sessionExpirationDate) {
		this.sessionExpirationDate = sessionExpirationDate;
	}
	/**
	 * @return
	 */
	public String getTechSupportNumber() {
		return techSupportNumber;
	}
	/**
	 * @param  techSupportNumber
	 */
	public void setTechSupportNumber(String techSupportNumber) {
		this.techSupportNumber = techSupportNumber;
	}
	/**
	 * @return
	 */
	public String getRouterSerialNumber() {
		return routerSerialNumber;
	}
	/**
	 * @param  routerSerialNumber
	 */
	public void setRouterSerialNumber(String routerSerialNumber) {
		this.routerSerialNumber = routerSerialNumber;
	}
	/**
	 * @return
	 */
	public String getButton() {
		return button;
	}
	/**
	 * @param  button
	 */
	public void setButton(String button) {
		this.button = button;
	}
	
	

}
