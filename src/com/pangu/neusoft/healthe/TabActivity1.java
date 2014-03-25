package com.pangu.neusoft.healthe;

import com.pangu.neusoft.healthcard.ListCardActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TabActivity1 extends Activity
{
	private SharedPreferences sp;
	private Editor editor;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		
		/*Intent intent = getIntent();

		String Value = intent.getStringExtra("extra");

		if (Value.equals("zhineng"))
		{
			setContentView(R.layout.tab_zhineng);

		} else
		{
			setContentView(R.layout.tab_shuzi);
		}*/
		setContentView(R.layout.tab_zhineng);
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		
		

		ImageButton yuyue = (ImageButton) findViewById(R.id.tab2_booking);

		yuyue.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if(sp.getInt("total_cards",0)==0){
					Toast.makeText(TabActivity1.this, "请先添加健康卡", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(TabActivity1.this,ListCardActivity.class);
					startActivity(intent);
					finish();
				}else{
					Intent intent = new Intent(TabActivity1.this,
							BookingMainActivity.class);
	
					startActivity(intent);
					finish();
				}
			}
		});

	}

}