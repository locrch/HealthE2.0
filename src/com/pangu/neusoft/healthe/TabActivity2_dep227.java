package com.pangu.neusoft.healthe;


import com.pangu.neusoft.drugstore.Drugstore_main_activity;
import com.pangu.neusoft.healthcard.ListCardActivity;
import com.pangu.neusoft.healthinfo.HealthInfoActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TabActivity2_dep227 extends Activity
{
	ImageButton tab2_booking;
	private SharedPreferences sp;
	private Editor editor;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.tab_zhineng_dep227);
		
		
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		
		tab2_booking = (ImageButton) findViewById(R.id.tab2_booking_dep);
		
		tab2_booking.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
				if(sp.getInt("total_cards",0)==0){
					Toast.makeText(TabActivity2_dep227.this, "请先添加健康卡", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(TabActivity2_dep227.this,ListCardActivity.class);
					startActivity(intent);
					//finish();
				}else{
					Intent intent = new Intent(TabActivity2_dep227.this,
							BookingMainActivity.class);
	
					startActivity(intent);
					//finish();
				}
				//finish();

			}
		});

		
		
	}

}