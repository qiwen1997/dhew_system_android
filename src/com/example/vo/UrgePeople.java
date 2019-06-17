package com.example.vo;

import java.io.Serializable;

public class UrgePeople implements Serializable{

	Integer eme_id; //紧急联系人信息id
    String user_phone; //用户手机号
    String contact_name; //紧急联系人姓名
    String contact_phone; //紧急联系人手机号
    Integer default_phone;
    public UrgePeople() {
    }

    public UrgePeople(String user_phone, String contact_name, String contact_phone) {
        this.user_phone = user_phone;
        this.contact_name = contact_name;
        this.contact_phone = contact_phone;
        this.default_phone=0;
    }

    public UrgePeople(Integer eme_id, String user_phone, String contact_name, String contact_phone) {
        this.eme_id = eme_id;
        this.user_phone = user_phone;
        this.contact_name = contact_name;
        this.contact_phone = contact_phone;
        this.default_phone=0;
    }

    
    //新添加
    public UrgePeople(String user_phone, String contact_name, String contact_phone,
			Integer default_phone) {
		super();
		
		this.user_phone = user_phone;
		this.contact_name = contact_name;
		this.contact_phone = contact_phone;
		this.default_phone = default_phone;
	}

	public Integer getEme_id() {
        return eme_id;
    }

    public void setEme_id(Integer eme_id) {
        this.eme_id = eme_id;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
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

    
    
    public Integer getDefault_phone() {
		return default_phone;
	}

	public void setDefault_phone(Integer default_phone) {
		this.default_phone = default_phone;
	}

	@Override
    public String toString() {
        return "UrgePeople{" +
                "eme_id=" + eme_id +
                ", user_phone='" + user_phone + '\'' +
                ", contact_name='" + contact_name + '\'' +
                ", contact_phone='" + contact_phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrgePeople)) return false;

        UrgePeople that = (UrgePeople) o;

        if (eme_id != null ? !eme_id.equals(that.eme_id) : that.eme_id != null) return false;
        if (user_phone != null ? !user_phone.equals(that.user_phone) : that.user_phone != null) return false;
        if (contact_name != null ? !contact_name.equals(that.contact_name) : that.contact_name != null) return false;
        return contact_phone != null ? contact_phone.equals(that.contact_phone) : that.contact_phone == null;
    }

    @Override
    public int hashCode() {
        int result = eme_id != null ? eme_id.hashCode() : 0;
        result = 31 * result + (user_phone != null ? user_phone.hashCode() : 0);
        result = 31 * result + (contact_name != null ? contact_name.hashCode() : 0);
        result = 31 * result + (contact_phone != null ? contact_phone.hashCode() : 0);
        return result;
    }
	
	
}
