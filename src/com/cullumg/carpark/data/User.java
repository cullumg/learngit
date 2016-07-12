package com.cullumg.carpark.data;

import java.io.Serializable;
import java.lang.reflect.Field;

public class User implements Serializable {
	private static final long serialVersionUID = -6129605791429096530L;
	private String userId;
	private String userName;
	private String telephone;
	private String password;
	private int defaultcarparkid;
	private int isAdmin;

	public User() {
	}

	public User(String userId, String userName, String telephone, int defaultcarparkid, String password, int isAdmin) {
		this.userId = userId;
		this.userName = userName;
		this.telephone = telephone;
		this.password = password;
		this.defaultcarparkid = defaultcarparkid;
		this.isAdmin = isAdmin;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDefaultcarparkid() {
		return this.defaultcarparkid;
	}

	public void setDefaultcarparkid(int defaultcarparkid) {
		this.defaultcarparkid = defaultcarparkid;
	}

	public int isAdmin() {
		return this.isAdmin;
	}

	public void setAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String toString() {
		Class<? extends User> cls = this.getClass();
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