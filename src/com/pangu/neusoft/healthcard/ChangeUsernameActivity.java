package com.pangu.neusoft.healthcard;

import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.ChangePassReq;
import com.pangu.neusoft.core.models.GetCAPTCHAReg;
import com.pangu.neusoft.core.models.MemberReg;
import com.pangu.neusoft.healthe.FatherActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.tools.DialogShow;
import com.pangu.neusoft.tools.StringMethods;

public class ChangeUsernameActivity extends FatherActivity{
	
	EditText oldname,newname,ver;
	Button getver,confirm;
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
      oldname = (EditText)findViewById(R.id.changeusername_oldname);
      newname = (EditText)findViewById(R.id.changeusername_newname);
      getver = (Button)findViewById(R.id.changeusername_getver_btn);
      confirm = (Button)findViewById(R.id.changeusername_confirm_btn);
      
	}
}
