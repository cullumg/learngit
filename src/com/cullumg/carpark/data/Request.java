package com.cullumg.carpark.data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Date;

public class Request implements Serializable {
	private static final long serialVersionUID = -791560519331315628L;
	private int requestid;
	private int carparkid;
	private int spaceid;
	private Date requestedDate;
	private String requestorUserId;
	private String ownerUserId;

	public Request(int requestid, int carparkid, int spaceid, String requestorUserId, Date requestedDate,
			String ownerUserId) {
		this.setRequestid(requestid);
		this.carparkid = carparkid;
		this.spaceid = spaceid;
		this.requestorUserId = requestorUserId;
		this.ownerUserId = ownerUserId;
		this.requestedDate = requestedDate;
	}

	public int getCarparkid() {
		return this.carparkid;
	}

	public void setCarparkid(int carparkid) {
		this.carparkid = carparkid;
	}

	public int getSpaceid() {
		return this.spaceid;
	}

	public void setSpaceid(int spaceid) {
		this.spaceid = spaceid;
	}

	public String getRequestorUserId() {
		return this.requestorUserId;
	}

	public void setRequestorUserId(String requestorUserId) {
		this.requestorUserId = requestorUserId;
	}

	public String getOwnerUserId() {
		return this.ownerUserId;
	}

	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public int getRequestid() {
		return this.requestid;
	}

	public void setRequestid(int requestid) {
		this.requestid = requestid;
	}

	public Date getRequestedDate() {
		return this.requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String toString() {
		Class<? extends Request> cls = this.getClass();
		StringBuilder sb = new StringBuilder();
		Field[] f = cls.getDeclaredFields();

		for (int i = 0; i < f.length; ++i) {
			try {
				sb.append(f[i].getName() + " = " + f[i].get(this) + "\n");
			} catch (IllegalAccessException arg5) {
				;
			}
		}

		return sb.toString();
	}
}