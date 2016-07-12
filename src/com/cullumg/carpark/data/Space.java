package com.cullumg.carpark.data;

import java.io.Serializable;
import java.lang.reflect.Field;

public class Space implements Serializable {
	private static final long serialVersionUID = -1659997935219841890L;
	private int spaceId;
	private String spaceName;
	private String description;
	private int carparkid;
	private String defaultownerid;

	public Space(int spaceId, String spaceName, String description) {
		this.spaceId = spaceId;
		this.spaceName = spaceName;
		this.description = description;
	}

	public Space(int spaceId, String spaceName, String description, int carparkid, String defaultownerid) {
		this.spaceId = spaceId;
		this.spaceName = spaceName;
		this.description = description;
		this.carparkid = carparkid;
		this.defaultownerid = defaultownerid;
	}

	public int getSpaceId() {
		return this.spaceId;
	}

	public void setSpaceId(int spaceId) {
		this.spaceId = spaceId;
	}

	public String getSpaceName() {
		return this.spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCarparkid() {
		return this.carparkid;
	}

	public void setCarparkid(int carparkid) {
		this.carparkid = carparkid;
	}

	public String getDefaultownerid() {
		return this.defaultownerid;
	}

	public void setDefaultownerid(String defaultownerid) {
		this.defaultownerid = defaultownerid;
	}

	public String toString() {
		Class<? extends Space> cls = this.getClass();
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