package com.pangu.neusoft.healthe;

import java.net.ContentHandler;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.mobstat.StatService;
import com.baidu.platform.comapi.map.r;
import com.hp.hpl.sparta.xpath.ThisNodeTest;
import com.pangu.neusoft.drugstore.Drugstore_main_activity;
import com.pangu.neusoft.healthcard.LoginActivity;
import com.pangu.neusoft.healthcard.RegisterActivity;
import com.pangu.neusoft.service.ListenService;
import com.pangu.neusoft.tools.DensityUtil;
import com.pangu.neusoft.tools.SysApplication;
import com.pangu.neusoft.tools.update.UpdateOperation;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class FristActivity extends Activity
{
	ImageButton zhineng, shuzi, phone;

	Button zhuce, denglu;
	TextView frist_welcome_content, frist_menu_reg_button,
			frist_menu_login_button, frist_menu_index_button,
			frist_menu_question_button, frist_menu_drugstore_button,
			frist_menu_more_button;
	private SharedPreferences sp;
	private Editor editor;
	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.activity_frist);

		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY, Setting.apikey);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_frist);

		frist_welcome_content = (TextView) findViewById(R.id.frist_welcome_content);

		phone = (ImageButton) findViewById(R.id.phone);

		zhineng = (ImageButton) findViewById(R.id.zhineng);

		shuzi = (ImageButton) findViewById(R.id.shuzi);

		zhuce = (Button) findViewById(R.id.zhuce);

		denglu = (Button) findViewById(R.id.denglu);

		frist_menu_reg_button = (TextView) findViewById(R.id.frist_menu_reg_button);

		frist_menu_login_button = (TextView) findViewById(R.id.frist_menu_login_button);

		frist_menu_index_button = (TextView) findViewById(R.id.frist_menu_index_button);

		frist_menu_question_button = (TextView) findViewById(R.id.frist_menu_question_button);

		frist_menu_drugstore_button = (TextView) findViewById(R.id.frist_menu_drugstore_button);

		frist_menu_more_button = (TextView) findViewById(R.id.frist_menu_more_button);

		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();

		Context ctx = FristActivity.this;
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		Integer height = metric.heightPixels;
		SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("height_screen", height);
		editor.commit();

		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		if (sp.getInt("fontsize", 0) == 0)
		{
			editor.putInt("fontsize", 16);
			editor.commit();
		}

		zhuce.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(FristActivity.this,
						RegisterActivity.class));
			}
		});
		Islogin();
		denglu.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				if (denglu.getText().toString().equals("登录"))
				{
					Intent intent = new Intent();
					intent.setClass(FristActivity.this, LoginActivity.class);
					startActivity(intent);
				} else
				{
					logoutDialog(FristActivity.this);
				}
			}
		});

		zhineng.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
				if(FristActivity.this.sp.getString("username", "").equals("")){
					Toast.makeText(FristActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
				}else{
				
				Intent intent = new Intent();

				intent.putExtra("extra", "zhineng");

				intent.setClass(FristActivity.this, TabHostActivity.class);

				startActivity(intent);
				}
			}
		});

		shuzi.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				Toast.makeText(getApplicationContext(), "正在建设中，敬请期待",
						Toast.LENGTH_LONG).show();

				/*
				 * Intent intent = new Intent();
				 * 
				 * intent.putExtra("extra", "shuzi");
				 * 
				 * intent.setClass(FristActivity.this,TabHostActivity.class);
				 * 
				 * startActivity(intent);
				 */
			}
		});

		phone.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				try
				{
					Intent tell = new Intent(Intent.ACTION_CALL, Uri
							.parse("tel:" + "12320"));

					FristActivity.this.startActivity(tell);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "请允许拨打电话请求！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		SysApplication.getInstance().addActivity(this);

		UpdateOperation update = new UpdateOperation(FristActivity.this);

		update.checkUpdate();

		this.NetWorkStatus(this);

		startListener();
		Setting.showupdate = true;

		preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
		int count = preferences.getInt("count", 0);
		// 判断程序与第几次运行，如果是第一次运行则跳转到引导页面
		if (count == 0)
		{
			Dialog alertDialog = new AlertDialog.Builder(this)
					.setTitle(R.string.welcome_dialog_title)
					.setMessage(R.string.welcome_dialog_content).create();
			alertDialog.show();

		}
		Editor ed = preferences.edit();
        //存入数据
        ed.putInt("count", ++count);
        //提交修改
        ed.commit();
        
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
						denglu.setText("登录");
						Islogin();
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

	private void Islogin()
	{
		// 判断登录状态
		//Toast.makeText(FristActivity.this, "username:"+sp.getString("username", ""),Toast.LENGTH_SHORT).show();
		
		if (sp.getString("username", "").equals(""))
		{
			denglu.setText("登录");
			frist_welcome_content.setText("尊敬的用户,您好！");
		} else
		{
			denglu.setText("注销");
			frist_welcome_content.setText("尊敬的"
					+ sp.getString("card" + sp.getString("defaultcardno", "")
							+ "_" + "owner", "用户") + ",您好！");
		}
	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
		Islogin();
	}

	@Override
	protected void onRestart()
	{
		// TODO Auto-generated method stub
		super.onRestart();
		Islogin();
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
		Islogin();
		// PushManager.resumeWork(getApplicationContext());
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);

	}

	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();

		// PushManager.stopWork(this);
	}

	private long exitTime = 1;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if ((System.currentTimeMillis() - exitTime) > 2000)
			{
				Toast.makeText(getApplicationContext(), "再按一次退出佛山e院",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else
			{
				finish();
				System.exit(0);
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public void NetWorkStatus(Context context)
	{
		/*
		 * 本方法实现判断网络连接功能，并可点击跳转到网络设置
		 */
		ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo mobNetInfo = connectMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiNetInfo = connectMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected())
		{
			Builder b = new AlertDialog.Builder(this).setTitle("没有可用的网络")
					.setMessage("是否对网络进行设置？");
			b.setPositiveButton("是", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{

					Intent intent = null;
					// 判断手机系统的版本 即API大于10 就是3.0或以上版本
					if (android.os.Build.VERSION.SDK_INT > 10)
					{
						intent = new Intent(
								android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					} else
					{
						intent = new Intent();
						ComponentName component = new ComponentName(
								"com.android.settings",
								"com.android.settings.WirelessSettings");
						intent.setComponent(component);
						intent.setAction("android.intent.action.VIEW");
					}
					startActivity(intent);
				}
			}).setNeutralButton("否", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{
					dialog.cancel();
				}
			}).show();
			// unconnect network
		} else
		{
			// connect network
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

	}

	public void startListener()
	{
		Intent i = new Intent();
		i.setClass(FristActivity.this, ListenService.class);
		this.startService(i);
	}
}
