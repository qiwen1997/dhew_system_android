package com.example.vo;

import java.util.Date;

public class User {

	String phone;
	String password;
	String nickname;
	String realname;
	String sex;
	Date date;
	double height;
	double weight;
	String blood;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getBlood() {
		return blood;
	}
	public void setBlood(String blood) {
		this.blood = blood;
	}
	@Override
	public String toString() {
		return "User [phone=" + phone + ", password=" + password + ", nickname=" + nickname + ", realname=" + realname
				+ ", sex=" + sex + ", date=" + date + ", height=" + height + ", weight=" + weight + ", blood=" + blood
				+ "]";
	}
	public User(String phone, String password, String nickname, String realname, String sex, Date date, double height,
			double weight, String blood) {
		super();
		this.phone = phone;
		this.password = password;
		this.nickname = nickname;
		this.realname = realname;
		this.sex = sex;
		this.date = date;
		this.height = height;
		this.weight = weight;
		this.blood = blood;
	}
	public User() {
		super();
	}
	
	
	
}
