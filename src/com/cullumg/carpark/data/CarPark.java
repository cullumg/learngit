package com.cullumg.carpark.data;

import java.io.Serializable;
import java.lang.reflect.Field;

public class CarPark implements Serializable {
	private static final long serialVersionUID = -9070579078540221276L;
	private int carparkid;
	private String carparkname;
	private String carparkaddress;

	public CarPark(int carparkid, String carparkname, String carparkaddress) {
		this.carparkid = carparkid;
		this.carparkname = carparkname;
		this.carparkaddress = carparkaddress;
	}

	public int getCarparkid() {
		return this.carparkid;
	}

	public void setCarparkid(int carparkid) {
		this.carparkid = carparkid;
	}

	public String getCarparkname() {
		return this.carparkname;
	}

	public void setCarparkname(String carparkname) {
		this.carparkname = carparkname;
	}

	public String getCarparkaddress() {
		return this.carparkaddress;
	}

	public void setCarparkaddress(String carparkaddress) {
		this.carparkaddress = carparkaddress;
	}

	public String toString() {
		Class<? extends CarPark> cls = this.getClass();
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