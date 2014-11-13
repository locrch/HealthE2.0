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

public class TabActivity2 extends Activity
{
	ImageButton tab2_booking, tab2_healthfoler, tab2_drugstore,
			tab2_moneycheck, tab2_eachother, tab2_healthinfo;
	private SharedPreferences sp;
	private Editor editor;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// 点击后加载不同的layout
		/*
		 * Intent intent = getIntent();
		 * 
		 * String Value = intent.getStringExtra("extra");
		 * 
		 * if (Value.equals("zhineng")) { setContentView(R.layout.tab_zhineng);
		 * 
		 * } else { setContentView(R.layout.tab_shuzi); }
		 */

		setContentView(R.layout.tab_zhineng);

		TextView huoqu = (TextView) findViewById(R.id.huoqu);
		huoqu.setText("欢迎你");
		huoqu.setVisibility(8);

		tab2_booking = (ImageButton) findViewById(R.id.tab2_booking);
		tab2_drugstore = (ImageButton) findViewById(R.id.tab2_drugstore);
		tab2_healthfoler = (ImageButton) findViewById(R.id.tab2_healthfoler);
		tab2_moneycheck = (ImageButton) findViewById(R.id.tab2_moneycheck);
		tab2_eachother = (ImageButton) findViewById(R.id.tab2_eachother);
		tab2_healthinfo = (ImageButton) findViewById(R.id.tab2_healthinfo);
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();

		OnClickListener development = new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "该功能正在建设中，敬请期待",
						Toast.LENGTH_SHORT).show();
			}
		};

		OnClickListener healthinfo = new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(TabActivity2.this,
						HealthInfoActivity.class));
			}
		};

		tab2_drugstore.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				startActivity(new Intent(TabActivity2.this,
						Drugstore_main_activity.class));
			}
		});

		tab2_healthfoler.setOnClickListener(development);
		tab2_moneycheck.setOnClickListener(development);
		tab2_eachother.setOnClickListener(development);
		tab2_healthinfo.setOnClickListener(healthinfo);
		// tab2_healthinfo.setOnClickListener(development);

		tab2_booking.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				/*
				 * View view = HealthActivityGroup .group
				 * .getLocalActivityManager() .startActivity("Activity2", new
				 * Intent(TabActivity2.this, BookingMainActivity.class)
				 * .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)) .getDecorView();
				 */
				/*
				 * if(sp.getInt("total_cards",0)==0){
				 * Toast.makeText(TabActivity2.this, "请先添加健康卡",
				 * Toast.LENGTH_SHORT).show(); Intent intent = new
				 * Intent(TabActivity2.this,ListCardActivity.class);
				 * startActivity(intent);
				 * 
				 * }else{ Intent intent = new Intent(TabActivity2.this,
				 * BookingMainActivity.class);
				 * 
				 * startActivity(intent);
				 * 
				 * }
				 */
				Intent intent = new Intent(TabActivity2.this,
						BookingMainActivity.class);

				startActivity(intent);

			}
		});

	}

}