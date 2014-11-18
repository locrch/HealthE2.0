package com.pangu.neusoft.healthe;

import com.baidu.mobstat.StatActivity;
import com.baidu.mobstat.StatService;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pangu.neusoft.CustomView.PopMenu;
import com.pangu.neusoft.healthcard.CardInfoActivity;
import com.pangu.neusoft.healthcard.ChangePassActivity;
import com.pangu.neusoft.healthcard.ChangeUsernameActivity;
import com.pangu.neusoft.healthcard.ConnectListActivity;
import com.pangu.neusoft.healthcard.ListCardActivity;
import com.pangu.neusoft.healthcard.LoginActivity;
import com.pangu.neusoft.healthcard.RegisterActivity;
import com.pangu.neusoft.healthcard.ShowHistoryActivity;

import com.pangu.neusoft.healthe.R;

import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
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
	TextView tab2_text;
	Button back_index, back_back,title_menu_btn;
	SlidingMenu menu;
	private SharedPreferences sp;
	private Editor editor;

	View contView;
	PopMenu popMenu;
	TextView menu_login_reg,menu_logout_login,menu_logot_setting,pop_menu_login_ch_user,pop_menu_login_logout,
	menu_login_ch_ps,menu_login_card,menu_login_history,menu_login_payment,menu_login_favorite,menu_login_setting;
	
	private void Init_login_menu(View v)
	{
		// TODO Auto-generated method stub
		popMenu = new PopMenu(TabHostActivity.this,R.layout.main_pop_menu_login);
		
		popMenu.showPopupWindow(v);
		contView = popMenu.getContentView();
		
		pop_menu_login_logout = (TextView)contView.findViewById(R.id.main_pop_menu_login_logout); 
		pop_menu_login_ch_user = (TextView)contView.findViewById(R.id.main_pop_menu_login_ch_user);
		menu_login_ch_ps = (TextView)contView.findViewById(R.id.main_pop_menu_login_ch_ps);
		menu_login_card= (TextView)contView.findViewById(R.id.main_pop_menu_login_card);
		menu_login_history= (TextView)contView.findViewById(R.id.main_pop_menu_login_history);
		menu_login_payment= (TextView)contView.findViewById(R.id.main_pop_menu_login_payment);
		menu_login_favorite= (TextView)contView.findViewById(R.id.main_pop_menu_login_favorite);
		menu_login_setting= (TextView)contView.findViewById(R.id.main_pop_menu_login_setting);
		
		
		pop_menu_login_logout.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				logoutDialog(TabHostActivity.this);
			}
		});
		
		pop_menu_login_ch_user.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(TabHostActivity.this,ChangeUsernameActivity.class));
			}
		});
		
		menu_login_ch_ps.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(TabHostActivity.this,ChangePassActivity.class));
			}
		});
		menu_login_card.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(TabHostActivity.this,ListCardActivity.class));
			}
		});
		menu_login_history.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(TabHostActivity.this,ShowHistoryActivity.class));
			}
		});
		menu_login_payment.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Toast.makeText(getApplication(), "该功能暂未开放！", Toast.LENGTH_SHORT)
						.show();
			}
		});
		menu_login_favorite.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(TabHostActivity.this,ConnectListActivity.class));
			}
		});
		
		menu_login_setting.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	
	private void Init_logout_menu(View v)
	{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				popMenu = new PopMenu(TabHostActivity.this,R.layout.main_pop_menu_logout);
				
				popMenu.showPopupWindow(v);
				contView = popMenu.getContentView();
				
				menu_login_reg = (TextView)contView.findViewById(R.id.main_pop_menu_logout_reg);
				menu_logout_login = (TextView)contView.findViewById(R.id.main_pop_menu_logout_login);
				menu_logot_setting = (TextView)contView.findViewById(R.id.main_pop_menu_logout_setting);
				
				menu_login_reg.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						startActivity(new Intent(TabHostActivity.this,RegisterActivity.class));
					}
				});
				
				menu_logout_login.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						startActivity(new Intent(TabHostActivity.this,LoginActivity.class));
					}
				});
				menu_logot_setting.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						
					}
				});
	}
	
	private boolean Islogin(View v)
	{
		
		if (!sp.getString("username", "").equals(""))
		{
			Init_login_menu(v);
			return true;
		} else
		{
			Init_logout_menu(v);
			return false;
		}
	}
	
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
		
		title_menu_btn = (Button)findViewById(R.id.title_menu_btn);
		
		title_menu_btn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Islogin(v);
			}
		});
		
		
		
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
		/*menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT); // 设置菜单
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.slidingmenu_shadowWidth);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_behindOffset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setBehindWidth(200);
		*/
		

		
	}

	
	
	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
		if (popMenu!=null)
		{
			popMenu.dismiss();
		}
		
	}
	
	@Override
	protected void onRestart()
	{
		// TODO Auto-generated method stub
		super.onRestart();
		if (popMenu!=null)
		{
			popMenu.dismiss();
		}
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
		
	}
	
	private void logoutDialog(Context context)
	{
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage("确认要注销吗？");
		builder.setTitle("提示");

		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
						// 注销
						for (int i = 0; i < 5; i++)
							editor.remove("card" + i + "_" + "owner");
						editor.remove("username");
						editor.remove("password");
						editor.remove("loginsuccess");
						editor.remove("card" + sp.getString("defaultcardno", "")+ "_" + "owner");
						editor.remove("defaultcardno");
						editor.commit();
						
						if (popMenu!=null)
						{
							popMenu.dismiss();
						}
					}
				});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
		builder.create().show();
	}
	
	
	
}