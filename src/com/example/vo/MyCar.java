package com.example.vo;

import java.io.Serializable;

public class MyCar implements Serializable{

	Integer car_id;
	String user_phone;
	String brand;
	String plate;
	String type;
	Integer Default;
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public Integer getCar_id() {
		return car_id;
	}
	public void setCar_id(Integer car_id) {
		this.car_id = car_id;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public Integer getDefault() {
		return Default;
	}
	public void setDefault(Integer default1) {
		Default = default1;
	}
	public MyCar() {
		super();
		Default=0;
	}
	public MyCar(String brand, String plate, String type) {
		super();
		this.brand = brand;
		this.plate = plate;
		this.type = type;
		Default=0;
	}
	
	
	
	
}
