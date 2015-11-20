/**
 * 
 */
package com.lewei.model;

/**
 * 邮件发送模型。
 * 
 * @author djzhao
 * @time 2015年11月3日
 */
public class Email {

	/** 邮箱ID */
	private int EmailID;

	/** 邮箱地址 */
	private String Email;

	/** 备注信息 */
	private String EmailRemark;

	/** 状态标识(默认1) */
	private int Status;

	/**
	 * @return the emailID
	 */
	public int getEmailID() {
		return EmailID;
	}

	/**
	 * @param emailID
	 *            the emailID to set
	 */
	public void setEmailID(int emailID) {
		EmailID = emailID;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return Email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		Email = email;
	}

	/**
	 * @return the emailRemark
	 */
	public String getEmailRemark() {
		return EmailRemark;
	}

	/**
	 * @param emailRemark
	 *            the emailRemark to set
	 */
	public void setEmailRemark(String emailRemark) {
		EmailRemark = emailRemark;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return Status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		Status = status;
	}
}
