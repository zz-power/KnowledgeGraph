package com.cetc28.seu.loading.theme.model;

import java.util.HashMap;

/**
 * @author yaosheng
 *
 */
public class Property {
	private HashMap<String,String> basePro; //基础属性类型 （属性名，值）
	private String parent;
	private HashMap<String,String> childObj; // 对象属性 (属性名 ， id)
	private HashMap<String,String> arrayObj; // 对象数组属性 (属性名 ， id集合)
	private String oid;
	private String type;

	public Property(HashMap<String, String> basePro, String parent, HashMap<String, String> childObj,
			HashMap<String, String> arrayObj, String oid,String type) {
		super();
		this.basePro = basePro;
		this.parent = parent;
		this.childObj = childObj;
		this.arrayObj = arrayObj;
		this.oid = oid;
		this.type=type;
	}
	public HashMap<String, String> getBasePro() {
		return basePro;
	}
	public void setBasePro(HashMap<String, String> basePro) {
		this.basePro = basePro;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public HashMap<String, String> getChildObj() {
		return childObj;
	}
	public void setChildObj(HashMap<String, String> childObj) {
		this.childObj = childObj;
	}
	public HashMap<String, String> getArrayObj() {
		return arrayObj;
	}
	public void setArrayObj(HashMap<String, String> arrayObj) {
		this.arrayObj = arrayObj;
	}
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((basePro == null) ? 0 : basePro.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Property other = (Property) obj;
		if (basePro == null) {
			if (other.basePro != null)
				return false;
		} else if (!basePro.equals(other.basePro))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
}
