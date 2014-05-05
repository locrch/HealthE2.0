package com.pangu.neusoft.healthcard;

import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.CardListReq;
import com.pangu.neusoft.core.models.ChangePassReq;
import com.pangu.neusoft.core.models.ChangeUsernameReq;
import com.pangu.neusoft.core.models.GetCAPTCHAReg;
import com.pangu.neusoft.core.models.MemberChangeReg;
import com.pangu.neusoft.core.models.MemberReg;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.db.People;
import com.pangu.neusoft.healthe.FatherActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.tools.DialogShow;
import com.pangu.neusoft.tools.StringMethods;

public class ChangeUsernameActivity extends FatherActivity{
	
	EditText oldname,newname,ver;
	Button getver,confirm;
	
	private SharedPreferences sp;
	private Editor editor;
	private ProgressDialog mProgressDialog; 
	
	private WebService service;
	String UserName;
	String PhoneNumber;
	String MemberName;
	String Sex;
	String IDCardNo;
	String RegTime;
	String LastLoginTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_username);
		setactivitytitle("修改用户名");
		init();
		
	}
	
	private void init()
	{
		// TODO Auto-generated method stub
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
      oldname = (EditText)findViewById(R.id.changeusername_oldname);
      newname = (EditText)findViewById(R.id.changeusername_newname);
      ver = (EditText)findViewById(R.id.changeusername_ver);
      getver = (Button)findViewById(R.id.changeusername_getver_btn);
      confirm = (Button)findViewById(R.id.changeusername_confirm_btn);
      mProgressDialog = new ProgressDialog(ChangeUsernameActivity.this);   
      mProgressDialog.setMessage("正在加载数据...");   
      mProgressDialog.setIndeterminate(false);  
      mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
      mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      service=new WebService();
      oldname.setText(sp.getString("username", ""));
      getver.setOnClickListener(getVer);
      confirm.setOnClickListener(changeUsername);
	}
	
	
	OnClickListener getVer=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final String phone=newname.getText().toString();
			if(!newname.getText().toString().equals("")){
				SendCaptcha sendAction=new SendCaptcha(phone,mProgressDialog,service,ChangeUsernameActivity.this,getver);
				sendAction.sendData();
			}else{
				String msg="新用户名不能为空\n";
				newname.setHint(msg);
				newname.setHintTextColor(Color.RED);
			}
		}
		
	};
	
	
	public String checkData(){
		String msg="";
		if(newname.getText().toString().equals("")){
			msg+="新用户名不能为空\n";
			newname.setHint(msg);
			newname.setHintTextColor(Color.RED);
			return msg;
		}	
		if(ver.getText().toString().equals("")){
			msg+="验证码不能为空\n";
			ver.setHint(msg);
			ver.setHintTextColor(Color.RED);
			return msg;
		}	
		return msg;
	}
		
	 OnClickListener changeUsername=new OnClickListener(){//修改信息
			@Override
			public void onClick(View v) {
				String msg=checkData();
				if(msg.equals("")){
					new AsyncTask<Void, Void, Boolean>(){
						String msg="修改用户名失败";
					    @SuppressWarnings("deprecation")
						@Override  
				        protected void onPreExecute() {   
				            super.onPreExecute();   
				            mProgressDialog.show();
				        }			
						@Override
						protected Boolean doInBackground(Void... params){
							
							
							ChangeUsernameReq req=new ChangeUsernameReq();
							req.setUserName(oldname.getText().toString());
							req.setNewPhoneNumber(newname.getText().toString());
							 req.setCAPTCHA(ver.getText().toString());
							 req.setAucode(GET.Aucode);
							 
							 SoapObject obj= service.modifyUsername(req,"ModifyUserName");
							 if(obj!=null){
								String IsModifySuccess=obj.getProperty("IsModifySuccess").toString();//0000成功1111报错
								String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
								msg=obj.getProperty("msg").toString();//返回的信息

								if(IsModifySuccess.equals("true")){
									
									return true;
								}else{
									return false;
								}
								
							}
							else{
								msg="修改用户名失败";
								return false;
							}
							
						}
						
						@SuppressWarnings("deprecation")
						@Override
						protected void onPostExecute(Boolean result){
							super.onPostExecute(result);
							
							if(mProgressDialog.isShowing()){
								mProgressDialog.dismiss();
							}
							DialogShow.showDialog(ChangeUsernameActivity.this, msg);
							if(result){
								editor.putString("username", newname.getText().toString());
								
								for(int i=0;i<5;i++)
									editor.remove("card"+i+"_"+"owner");				
						    	editor.remove("card" + sp.getString("defaultcardno", "")+ "_" + "owner");
								editor.remove("defaultcardno");				
						    	editor.commit();
//								DBManager mgr=new DBManager(ChangeUsernameActivity.this);
//								People person= mgr.queryByName(oldname.getText().toString());
//								person.setPhone(newname.getText().toString());
//								mgr.update(person);
							}
						}
						@SuppressWarnings("deprecation")
						@Override
						protected void onCancelled()
						{
							super.onCancelled();
							if(mProgressDialog.isShowing()){
								mProgressDialog.dismiss();
							}
						}
					}.execute();
				}
//				else{
//					DialogShow.showDialog(RegisterActivity.this, msg);
//				}
			}
	  };
	
}
