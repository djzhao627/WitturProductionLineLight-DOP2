package com.lewei.model;

/**
 * ���߼ƻ�ģ��
 * 
 * @author djzhao
 * @time 2015��9��25��
 */
public class TPPlan {

	/** �����ƻ������� */
	private int TPPlanID;

	/** �ܲ�����Ĭ���趨Ϊ100�� */
	private int TotalNum;
	
	/** ������ */
	private String TPLineName;

	/** ��Σ�Ĭ��Ϊ0��0Ϊ��࣬1Ϊ�а࣬2Ϊ��� */
	private int Ranger;

	/** ����ʱ�� */
	private String CreateTme;

	/** ������ */
	private String CreatePeople;

	/** ״̬�У�Ĭ��״̬����Ϊ1�� */
	private int Status;

	public int getTPPlanID() {
		return TPPlanID;
	}

	public void setTPPlanID(int tPPlanID) {
		TPPlanID = tPPlanID;
	}

	public int getTotalNum() {
		return TotalNum;
	}

	public void setTotalNum(int totalNum) {
		TotalNum = totalNum;
	}

	public String getTPLineName() {
		return TPLineName;
	}

	public void setTPLineName(String tPLineName) {
		TPLineName = tPLineName;
	}

	public int getRanger() {
		return Ranger;
	}

	public void setRanger(int ranger) {
		Ranger = ranger;
	}

	public String getCreateTme() {
		return CreateTme;
	}

	public void setCreateTme(String createTme) {
		CreateTme = createTme;
	}

	public String getCreatePeople() {
		return CreatePeople;
	}

	public void setCreatePeople(String createPeople) {
		CreatePeople = createPeople;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

}
