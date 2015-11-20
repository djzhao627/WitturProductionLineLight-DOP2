package com.lewei.model;

import java.util.Date;

public class Warning {
	private int id;
	private String customerCode;
	private String warningSite;
	private String warningTime;
	private String warningType;
	private String warningHandler;
	private String warningPic;
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getWarningSite() {
		return warningSite;
	}

	public void setWarningSite(String warningSite) {
		this.warningSite = warningSite;
	}

	
	
	public String getWarningTime() {
		return warningTime;
	}

	public void setWarningTime(String warningTime) {
		this.warningTime = warningTime;
	}

	public String getWarningType() {
		return warningType;
	}

	public void setWarningType(String warningType) {
		this.warningType = warningType;
	}

	public String getWarningHandler() {
		return warningHandler;
	}

	public void setWarningHandler(String warningHandler) {
		this.warningHandler = warningHandler;
	}

	public String getWarningPic() {
		return warningPic;
	}

	public void setWarningPic(String warningPic) {
		this.warningPic = warningPic;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
