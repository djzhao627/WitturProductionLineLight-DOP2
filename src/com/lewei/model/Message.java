package com.lewei.model;

/**
 * 短信发送模型
 * 
 * @author djzhao
 * @time 2015年11月3日
 */
public class Message {

	/** 信息ID */
	private int MesID;

	/** 发送短信的号码 */
	private String MesNumber;

	/** 备注信息 */
	private String MesRemark;

	/** 状态标识(默认1) */
	private int Status;

	/**
	 * @return the mesID
	 */
	public int getMesID() {
		return MesID;
	}

	/**
	 * @param mesID
	 *            the mesID to set
	 */
	public void setMesID(int mesID) {
		MesID = mesID;
	}

	/**
	 * @return the mesNumber
	 */
	public String getMesNumber() {
		return MesNumber;
	}

	/**
	 * @param mesNumber
	 *            the mesNumber to set
	 */
	public void setMesNumber(String mesNumber) {
		MesNumber = mesNumber;
	}

	/**
	 * @return the mesRemark
	 */
	public String getMesRemark() {
		return MesRemark;
	}

	/**
	 * @param mesRemark
	 *            the mesRemark to set
	 */
	public void setMesRemark(String mesRemark) {
		MesRemark = mesRemark;
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
