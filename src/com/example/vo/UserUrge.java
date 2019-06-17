package com.example.vo;

public class UserUrge {

	String masterphone;
	String urgename;
	String urgephone;
	public String getMasterphone() {
		return masterphone;
	}
	public void setMasterphone(String masterphone) {
		this.masterphone = masterphone;
	}
	public String getUrgename() {
		return urgename;
	}
	public void setUrgename(String urgename) {
		this.urgename = urgename;
	}
	public String getUrgephone() {
		return urgephone;
	}
	public void setUrgephone(String urgephone) {
		this.urgephone = urgephone;
	}
	@Override
	public String toString() {
		return "UserUrge [masterphone=" + masterphone + ", urgename=" + urgename + ", urgephone=" + urgephone + "]";
	}
	public UserUrge(String masterphone, String urgename, String urgephone) {
		super();
		this.masterphone = masterphone;
		this.urgename = urgename;
		this.urgephone = urgephone;
	}
	public UserUrge() {
		super();
	}
	
	
	
	
}
