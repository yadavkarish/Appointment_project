package com.dollop.appointment.model;

public class BookAppoinment {

	private Integer AppTime;
	private Integer AppDate;
	
	//-------------Setter and Getter method for BookAppoinment--------------//
	
	public Integer getAppTime() {
		return AppTime;
	}
	
	public void setAppTime(Integer appTime) {
		AppTime = appTime;
	}
	
	
	public Integer getAppDate() {
		return AppDate;
	}
	/**
	 * @param appDate the appDate to set
	 */
	public void setAppDate(Integer appDate) {
		AppDate = appDate;
	}	
}
