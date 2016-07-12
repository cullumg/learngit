package com.cullumg.carpark.data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

public class Allocation implements Serializable {
	private static final long serialVersionUID = -1743732301672644498L;
	private int spaceId;
	private String spaceName;
	private String userId;
	private String userName;
	private String defaultOwnerId;
	private String defaultOwner;

	public Allocation(int spaceId, String spaceName, String userId, Date theDate) {
		this.spaceId = spaceId;
		this.spaceName = spaceName;
		this.userId = userId;
	}

	public Allocation(int spaceId, String spaceName, String userId, String userName, Date theDate,
			String defaultOwnerId, String defaultOwner) {
		this.spaceId = spaceId;
		this.spaceName = spaceName;
		this.userId = userId;
		this.userName = userName;
		this.defaultOwnerId = defaultOwnerId;
		this.defaultOwner = defaultOwner;
	}

	public int getSpaceId() {
		return this.spaceId;
	}

	public void setSpaceId(int spaceId) {
		this.spaceId = spaceId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSpaceName() {
		return this.spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDefaultOwnerId() {
		return this.defaultOwnerId;
	}

	public void setDefaultOwnerId(String defaultOwnerId) {
		this.defaultOwnerId = defaultOwnerId;
	}

	public String getDefaultOwner() {
		return this.defaultOwner;
	}

	public void setDefaultOwner(String defaultOwner) {
		this.defaultOwner = defaultOwner;
	}

	public String toString() {
		Class<? extends Allocation> cls = this.getClass();
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