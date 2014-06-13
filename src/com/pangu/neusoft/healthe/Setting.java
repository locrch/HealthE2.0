package com.pangu.neusoft.healthe;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.pangu.neusoft.core.models.BookingInfos;

public class Setting {
	
	public static boolean showupdate=true;
	public final static int fontsizel=15;
	public static int fontsizex=12;
	public static int fontsizes=10;
	public static int fontsizey=8;
	public final static String spfile="healthE";
	public final static int dialogtimeout=2000;
	public final static int noticetime=30000;//标题栏显示通知的时间
	public final static String defaultnotice="通知：";//默认通知 
	public final static String catche_dir="/mnt/sdcard/healthE/";
	public final static String image_="pangu";
	//public final static String default_pic_url="http://publimedic.mx/wp-content/themes/directorypress/thumbs/hospital_icon-1.jpg";
	public final static String DEFAULT_DOC_url="http://cdn-img.easyicon.net/png/122/12279.png";//默认图片
	public final static String TEST_url="http://cdn-img.easyicon.net/png/120/12012.png";//测试图片
	
	public final static int countDownGetCaptcha=180;//获取验证码倒数时间
	
	public static final String parcelableKey = "com.pangu.neusoft.healthe"; 
	//public static final int schedule_show_num=6;//not use
	
	//public static final String mapkey="36a773d43634b75464e03e17f65dd3bf";
	public static final String mapkey="849b6fcf128aaff2549fda5df98dff56";//地图key
	public static final String apikey="WUXMvjgF3megBkFlYiF9CVnq";//api key
	public static final double default_lat=23.026947*1E6;//默认经纬度
	public static final double default_lon=113.129067*1E6;
	public static final int map_zoom=16;//默认地图缩放比例
	
	public static String check_url = "http://202.103.160.158:678/V1/HealthE.apk";//软件更新apk
	//public static String SerialNumber;
	//public static String link="http://219.130.221.120:10804/smjkfw/wsyygh/pages/jkw_login_dx.jsp?redirect=http://202.103.160.158:1001/MedicalCardPage/receive.aspx&wxid=";
	public static String link="http://219.130.221.120/smjkfw/wsyygh/pages/jkw_login_dx.jsp?redirect=http://202.103.160.153:1001/MedicalCardPage/receive.aspx&wxid=";
	public static int cols=3;//一行显示多少个 医生排班日期
	public static String state="booking";
	public static BookingInfos bookingdata;
	public static int history_list_show=10;//历史记录一次显示10行
	public static int bookingmain_scroll_height;
	public static void setDefaultCardNumber(SharedPreferences sp,Editor editor){
		
			editor.putString("owner", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"owner",""));
			editor.putString("cardnum", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"cardnum",""));
			editor.putString("cardtype", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"cardtype",""));
			editor.putString("idnumber", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"idnumber",""));
			editor.putString("idtype", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"idtype",""));
			editor.putString("phonenumber", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"phonenumber",""));
			editor.commit();
	}
	public static String getDefaultCardNumber(SharedPreferences sp,Editor editor){
		
		String owner=sp.getString("card"+sp.getString("defaultcardno","")+"_"+"owner","");
		String cardtype= sp.getString("card"+sp.getString("defaultcardno","")+"_"+"cardtype","");
		String type="";
		if(cardtype.equals("1")){
			type=("(佛山健康卡)");
		}else{
			type=("(居民健康卡)");
		}
		if(owner.equals("")){
			return "未添加健康卡";
		}
		return owner+type;
	}
	public static void setListViewBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {
        // pre-condition  
            return;  
        }  
        int totalHeight = 0;  
        int maxWidth = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {  
            View listItem = listAdapter.getView(i, null, listView);  
            if(listItem!=null){
	            //listItem.measure(0, 0);  
	            totalHeight += listItem.getMeasuredHeight();  
	            int width = listItem.getMeasuredWidth();
	            if(width>maxWidth)maxWidth = width;
            }
        }  
   
        ViewGroup.LayoutParams params = listView.getLayoutParams();  
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
        params.width = maxWidth;
        listView.setLayoutParams(params);  
    }
	//Px转Dp
	public static int Px2Dp(Context context, float px) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (px / scale + 0.5f); 
	}
	//Dp转Px
	public static int Dp2Px(Context context, float dp) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (dp * scale + 0.5f); 
	} 
}
