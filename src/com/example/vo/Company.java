package com.example.vo;

public class Company {

	String phone;
	String comname;
	String department;
	String identity;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getComname() {
		return comname;
	}
	public void setComname(String comname) {
		this.comname = comname;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	@Override
	public String toString() {
		return "Company [phone=" + phone + ", comname=" + comname + ", department=" + department + ", identity="
				+ identity + "]";
	}
	public Company(String phone, String comname, String department, String identity) {
		super();
		this.phone = phone;
		this.comname = comname;
		this.department = department;
		this.identity = identity;
	}
	public Company() {
		super();
	}
	
	
	
	
}
