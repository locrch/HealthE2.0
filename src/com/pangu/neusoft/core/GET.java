package com.pangu.neusoft.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public interface GET
{
	public static final String Aucode = "QWERTYUIOP";
	//联调请更改此处
	
	public static final String URL ="http://202.103.160.153:1001/HealthCarePlatform.asmx";
	//public static final String URL ="http://192.168.0.202:1001/HealthCarePlatform.asmx";
	public static final String NAMESPACE = "http://tempuri.org/";
	//public static final String cityId="440600";
	// 改变方法名即可调用接口内其他方法，且同时改变输入参数名和参数值，否则返回报错
	public static final HashMap<String, String> MethodName = new HashMap<String, String>();
	
	public static class index
	{

		public static final String GetMethodName(String i)
		{
			MethodName.put("预约查询", "Booking_CheckBooking");

			MethodName.put("booking", "Booking_ExecBooking");

			MethodName.put("getAreaList", "Booking_GetAreaList");

			MethodName.put("getHospitalList", "Booking_GetHospitalList");
			
			MethodName.put("getHospitalInfo", "Booking_GetHospitalInfo");

			MethodName.put("getDepartmentInfo", "Booking_GetDepartmentInfo");

			MethodName.put("getDepartmentList", "Booking_GetDepartmentList");

			MethodName.put("getDoctorInfo", "Booking_GetDoctorInfo");

			MethodName.put("getDoctorList", "Booking_GetDoctorList");

			MethodName.put("getSchedulingList", "Booking_GetScheduling");

			MethodName.put("会员认证", "Booking_GetUserInfo");

			MethodName.put("cancleBooking", "Booking_HandleBooking");
			
			MethodName.put("userRegister", "RegisterMember");
			
			MethodName.put("userLogin", "LoginMember");
			
			MethodName.put("addMemberCard", "AddMemberMedicalCard");
			
			MethodName.put("deleteMemberCard", "RemoveMemberMedicalCard");
			
			MethodName.put("getMemberCardList", "GetMemberMedicalCard");
			
			MethodName.put("findDoctorList", "Booking_FindDoctorList");
			
			MethodName.put("findHospitalList", "Booking_FindHospitalList");

			MethodName.put("GetCAPTCHA", "GetCAPTCHA");
			
			MethodName.put("ResetPassword", "ResetPassword");
			
			MethodName.put("GetMemberInfo", "GetMemberInfo");
			
			MethodName.put("ModifyMemberInfo", "ModifyMemberInfo");
			
			MethodName.put("GetDrugCompany", "DS_GetDrugCompany");
			
			MethodName.put("GetDrupShop", "DS_GetDrupShop");
			
			MethodName.put("GetPreferentialNews", "DS_GetPreferentialNews");
			
			MethodName.put("ModifyUserName", "ModifyUserName");
			
			return MethodName.get(i);
		}
		
		
		
	}

}
