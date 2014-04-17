package com.pangu.neusoft.core.models;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class ChangeUsernameReq  implements KvmSerializable{
	private String UserName;
	private String NewPhoneNumber;
	private String CAPTCHA;	
	private String Aucode;
	
	
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	
	public String getNewPhoneNumber() {
		return NewPhoneNumber;
	}
	public void setNewPhoneNumber(String newPhoneNumber) {
		NewPhoneNumber = newPhoneNumber;
	}
	public String getCAPTCHA() {
		return CAPTCHA;
	}
	public void setCAPTCHA(String cAPTCHA) {
		CAPTCHA = cAPTCHA;
	}
	public String getAucode() {
		return Aucode;
	}
	public void setAucode(String aucode) {
		Aucode = aucode;
	}
	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
		case 0:
			return UserName;
		case 1:
			return NewPhoneNumber;
		case 2:
			return CAPTCHA;
		case 3:
			return Aucode;
		}
		return null;
	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 4;
	}
	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		switch (index) {
		case 0:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "UserName";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "NewPhoneNumber";
			break;	
		case 2:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "CAPTCHA";
			break;	
		case 3:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "Aucode";
			break;	
		default:
			break;
		}
	}
	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			UserName = value.toString();
			break;
		case 1:
			NewPhoneNumber=value.toString();
			break;
		case 2:
			CAPTCHA = value.toString();
			break;
		case 3:
			Aucode = value.toString();
			break;	
		default:
			break;
		}
	}
	 
}
