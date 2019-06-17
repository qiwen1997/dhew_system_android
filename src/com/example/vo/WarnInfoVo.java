package com.example.vo;

import java.util.Date;

public class WarnInfoVo {
	Integer warn_id; //预警信息id
    String user_phone; //用户名手机号
    String user_name; //用户昵称
    String contact_name; //紧急联系人姓名
    String  contact_phone; //紧急联系人电话
    String warn_loc; //位置信息
    String warn_data; //预警信息
    Date warn_time; //预警时间
	public WarnInfoVo() {
		super();
	}
	public WarnInfoVo(Integer warn_id, String user_phone, String user_name, String contact_name, String contact_phone,
			Date warn_time) {
		super();
		this.warn_id = warn_id;
		this.user_phone = user_phone;
		this.user_name = user_name;
		this.contact_name = contact_name;
		this.contact_phone = contact_phone;
		this.warn_time = warn_time;
	}
	public WarnInfoVo(Integer warn_id, String user_phone, String user_name, String contact_name, String contact_phone,
			String warn_loc, String warn_data, Date warn_time) {
		super();
		this.warn_id = warn_id;
		this.user_phone = user_phone;
		this.user_name = user_name;
		this.contact_name = contact_name;
		this.contact_phone = contact_phone;
		this.warn_loc = warn_loc;
		this.warn_data = warn_data;
		this.warn_time = warn_time;
	}
	public Integer getWarn_id() {
		return warn_id;
	}
	public void setWarn_id(Integer warn_id) {
		this.warn_id = warn_id;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getWarn_loc() {
		return warn_loc;
	}
	public void setWarn_loc(String warn_loc) {
		this.warn_loc = warn_loc;
	}
	public String getWarn_data() {
		return warn_data;
	}
	public void setWarn_data(String warn_data) {
		this.warn_data = warn_data;
	}
	public Date getWarn_time() {
		return warn_time;
	}
	public void setWarn_time(Date warn_time) {
		this.warn_time = warn_time;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contact_name == null) ? 0 : contact_name.hashCode());
		result = prime * result + ((contact_phone == null) ? 0 : contact_phone.hashCode());
		result = prime * result + ((user_name == null) ? 0 : user_name.hashCode());
		result = prime * result + ((user_phone == null) ? 0 : user_phone.hashCode());
		result = prime * result + ((warn_data == null) ? 0 : warn_data.hashCode());
		result = prime * result + ((warn_id == null) ? 0 : warn_id.hashCode());
		result = prime * result + ((warn_loc == null) ? 0 : warn_loc.hashCode());
		result = prime * result + ((warn_time == null) ? 0 : warn_time.hashCode());
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
		WarnInfoVo other = (WarnInfoVo) obj;
		if (contact_name == null) {
			if (other.contact_name != null)
				return false;
		} else if (!contact_name.equals(other.contact_name))
			return false;
		if (contact_phone == null) {
			if (other.contact_phone != null)
				return false;
		} else if (!contact_phone.equals(other.contact_phone))
			return false;
		if (user_name == null) {
			if (other.user_name != null)
				return false;
		} else if (!user_name.equals(other.user_name))
			return false;
		if (user_phone == null) {
			if (other.user_phone != null)
				return false;
		} else if (!user_phone.equals(other.user_phone))
			return false;
		if (warn_data == null) {
			if (other.warn_data != null)
				return false;
		} else if (!warn_data.equals(other.warn_data))
			return false;
		if (warn_id == null) {
			if (other.warn_id != null)
				return false;
		} else if (!warn_id.equals(other.warn_id))
			return false;
		if (warn_loc == null) {
			if (other.warn_loc != null)
				return false;
		} else if (!warn_loc.equals(other.warn_loc))
			return false;
		if (warn_time == null) {
			if (other.warn_time != null)
				return false;
		} else if (!warn_time.equals(other.warn_time))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "WarnInfoVo [warn_id=" + warn_id + ", user_phone=" + user_phone + ", user_name=" + user_name
				+ ", contact_name=" + contact_name + ", contact_phone=" + contact_phone + ", warn_loc=" + warn_loc
				+ ", warn_data=" + warn_data + ", warn_time=" + warn_time + "]";
	}
    
    
}
