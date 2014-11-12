package com.pangu.neusoft.healthe;

import com.baidu.mobstat.StatService;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pangu.neusoft.healthcard.ChangeUsernameActivity;
import com.pangu.neusoft.healthe.R.drawable;
import com.pangu.neusoft.healthe.R.id;



import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class TabHostActivity extends ActivityGroup
{
	/** Called when the activity is first created. */
	private TabHost tabHost;
	private LayoutInflater mInflater = null;
	TextView tab2_text,main_slidingmenu_2;
	Button back_index, back_back;
	SlidingMenu menu;
	private SharedPreferences sp;
	private Editor editor;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.acitivty_tabhost);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_tabhost);
		/*
		 * back_back = (Button)findViewById(R.id.back_back); back_index =
		 * (Button)findViewById(R.id.back_index); back_index.setVisibility(8);
		 * back_back.setVisibility(8);
		 */

		/*
		 * if (Value.equals("zhineng")) { tab2_text.setText("智能健康"); } else {
		 * tab2_text.setText("数字医院"); }
		 */
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		mInflater = LayoutInflater.from(this);
		tabHost = (TabHost) findViewById(R.id.mytabhost);
		tabHost.setup(this.getLocalActivityManager());
		Intent intent;

		intent = new Intent(this, TabActivity2.class);

		View tab1Spec = mInflater.inflate(R.layout.tab1_spec, null);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(tab1Spec)
				.setContent(intent));

		tab1Spec.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				/*
				 * Intent intent = new Intent(TabHostActivity.this,
				 * FristActivity.class);
				 * intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 * startActivity(intent);
				 */
				finish();

			}
		});

		intent = new Intent(this, TabActivity2.class);
		// intent = new Intent(this, TabActivity2_dep227.class);

		View tab2Spec = mInflater.inflate(R.layout.tab2_spec, null);
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(tab2Spec)
				.setContent(intent));

		intent = new Intent(this, TabActivity4.class);
		View tab4Spec = mInflater.inflate(R.layout.tab4_spec, null);
		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator(tab4Spec)
				.setContent(intent));

		intent = new Intent(this, TabActivity3.class);
		View tab3Spec = mInflater.inflate(R.layout.tab3_spec, null);
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(tab3Spec)
				.setContent(intent));

		intent = new Intent(this, TabActivity5.class);
		View tab5Spec = mInflater.inflate(R.layout.tab5_spec, null);
		tabHost.addTab(tabHost.newTabSpec("tab5").setIndicator(tab5Spec)
				.setContent(intent));

		tabHost.setCurrentTab(1);// 设置默认显示第二个tab
		tab4Spec.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "该功能正在建设中，敬请期待",
						Toast.LENGTH_SHORT).show();

			}
		});

		/* 侧滑菜单 */
		// 滑动模式，左滑还是右滑
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT); // 设置菜单
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.slidingmenu_shadowWidth);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_behindOffset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		
		menu.setBehindWidth(200);
		
		if (sp.getString("username", "").equals(""))
		{
			menu.setMenu(R.layout.main_slidingmenu_logout);
		} else
		{
			menu.setMenu(R.layout.main_slidingmenu_login);
			
			main_slidingmenu_2 = (TextView)findViewById(R.id.main_slidingmenu_2);
			
			main_slidingmenu_2.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					startActivity(new Intent(TabHostActivity.this, ChangeUsernameActivity.class));
				}
			});
		}
		
		
		
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}
}