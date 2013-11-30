package com.pangu.neusoft.healthe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.core.models.ScheduleButton;
import com.pangu.neusoft.healthcard.BookingAction;
import com.pangu.neusoft.healthcard.ListCardActivity;
import com.pangu.neusoft.healthcard.LoginActivity;
import com.pangu.neusoft.tools.SortListByItem;

public class SchedultLayout {

	public LinearLayout layout;
	private Activity activity;
	private List<Schedule> scheduleList;
	SortListByItem sort;
	
	 LinearLayout scheduleDetailLayout;//from out
	 TableLayout scheduleLayout;//from out
	 TextView scheduleText;
	 
	  List<LinearLayout> alllayout;
	  LayoutInflater inflater ;
	  View nowButton;
	  boolean showing=false;
	  
	  SharedPreferences sp;
	 	Editor editor;
	  
	  public SchedultLayout(Activity activity,List<Schedule> scheduleList,LinearLayout scheduleDetailLayout,TableLayout scheduleLayout,TextView scheduleText){
		  sp = activity.getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
          editor=sp.edit();
		  sort=new SortListByItem();
		  inflater= activity.getLayoutInflater();
		  alllayout=new ArrayList<LinearLayout>();
		  this.activity=activity;
		  this.scheduleList=scheduleList;
		  this.scheduleDetailLayout=scheduleDetailLayout;
		  this.scheduleLayout=scheduleLayout;
		  this.scheduleText=scheduleText;
	  } 
	  
	  
	public void getView(){
		
		
		
		 if(scheduleList!=null&&scheduleList.size()>0){
         	
         	
         	//Map<String,String> maps=new LinkedHashMap<String,String>();
         
         	List<String> daylist=new ArrayList<String>();
         	
	            for(Schedule schedule:scheduleList){
	            	String day=schedule.getOutcallDate();
//	            	String number=schedule.getAvailableNum();
//	            	String now=maps.get(day);
//	            	if(now!=null){
//	            		int res=Integer.parseInt(now)+Integer.parseInt(number);
//	            		number=res+"";
//	            	}
//	            	maps.put(day, number);
	            	daylist.add(day);
	            }
	            //maps.clear();//暂时不在列表显示排班表
	            
	            
//	            TreeMap<String,String> treemap = new TreeMap<String,String>(maps);
//	            String[] alldays=new String[maps.size()];
//	            Iterator it = treemap.entrySet().iterator();
//	            int dayx=0;
//	           
//	    		  while (it.hasNext()){ 
//		    		   Map.Entry entry =(Map.Entry) it.next();
//		    		   String key = (String) entry.getKey();
//		    		   alldays[dayx]=key;
//		    		   dayx++;
//	    		  }
	            
	         
	            HashSet h  =   new  HashSet(daylist); 
	            daylist.clear(); 
	            daylist.addAll(h); 
	            daylist=sort.sortScheduleByTime(daylist);
	            
	            int totaldays=daylist.size();
	            
	            
	            int totalrows=totaldays/Setting.cols;
	            if(totalrows%Setting.cols!=0){
	            	totalrows+=1;
	            }
	            if(totalrows==0)
	            	totalrows=1;
	            
	            int count=0;
	            for(int i=0;i<totalrows;i++){
	            	TableRow row = new TableRow(activity);
	            	row.setId(i);  
	            	for(int j=0;j<Setting.cols;j++){
	            		if(count<daylist.size()){	            		
	            			String day=daylist.get(count);
	            			
	    	            	//TextView days=new TextView(getContext());
	            			  
	 		                View days = inflater.inflate(R.layout.schedule_day, null); 
	 		                
	 		                TextView dayview=(TextView) days.findViewById(R.id.schedule_day);
	 		                
	 		                dayview.setText(day);
	    	            	
	    	            	
	    	            	final ScheduleButton newButtonInfo=new ScheduleButton();
	   		                newButtonInfo.setScheduleList(scheduleList);
	   		                newButtonInfo.setDay(day);
	   		                newButtonInfo.setScheduleDetailLayout(scheduleDetailLayout);
	   		                alllayout.add((LinearLayout)days.findViewById(R.id.schedule_day_btn_set));
	   		                
	    	            	//days.setTag(newButtonInfo);
	    	            	days.setOnClickListener(new OnClickListener(){
	 							@SuppressLint("NewApi")
								@Override
	 							public void onClick(View arg0) {
	 								 LinearLayout scheduleDetailLayout=newButtonInfo.getScheduleDetailLayout();
	 								 scheduleDetailLayout.removeAllViews();
	 								 for(LinearLayout layout:alllayout){
	 									layout.setBackgroundDrawable(layout.getResources().getDrawable(R.drawable.schedule_btn_style_layout));
	 								 }
	 								 LinearLayout layout=(LinearLayout)arg0.findViewById(R.id.schedule_day_btn_set);
	 								  if(arg0.equals(nowButton)&&showing==true){
	 									 nowButton=arg0;
	 									  showing=false;
	 								  }else{
	 								 nowButton=arg0;
	 								showing=true;
	 								layout.setBackgroundDrawable(layout.getResources().getDrawable(R.drawable.schedule_btn_style_layout_click));
	 								// ScheduleButton newButtonInfo=( ScheduleButton)arg0.getTag();
	 								
	 								 List<Schedule> scheduleList=newButtonInfo.getScheduleList();
	 								 String day=newButtonInfo.getDay();
	 								//显示当日可排班内容
	 								 
	 								
	 								//获得schedule内容。写到列表中
	 								for(Schedule schedule:scheduleList){
	 									Log.e("ScheduleList:",schedule.getDoctorName()+schedule.getDoctorId()+":"+schedule.getTimeRange()+" 可预约数："+schedule.getAvailableNum());
	 									 scheduleList=sort.sortScheduleByDay(scheduleList);
	 									if(schedule.getOutcallDate().equals(day)){
	 										Button oneButton=new Button(scheduleDetailLayout.getContext());
	 										oneButton.setPadding(10, 5, 10, 5);
	 										
	 										oneButton.setText(schedule.getTimeRange()+" 可预约数："+schedule.getAvailableNum());
	 										oneButton.setTag(schedule);
	 										oneButton.setBackgroundDrawable(layout.getResources().getDrawable(R.drawable.schedule_btn_style_layout_click));
	 										oneButton.setOnClickListener(new OnClickListener(){
	 											@Override
	 											public void onClick(View arg0) {
	 												Schedule schedule=(Schedule)arg0.getTag();
	 												String doctorId=schedule.getDoctorId();
	 												String doctorName=schedule.getDoctorName();
	 												editor.putString("now_state", "booking");
	 												//记录医生信息要清空					 
	 												editor.putString("doctorId", doctorId);
	 												editor.putString("doctorName", doctorName);
	 												editor.putString("SchState", schedule.getSchState());
	 								      			editor.putString("ScheduleID", schedule.getScheduleID());
	 								      			editor.putString("RegId", schedule.getRegId());
	 								      			editor.putString("ReserveDate", schedule.getOutcallDate());
	 								      			editor.putString("ReserveTime", schedule.getTimeRange());
	 								      			editor.putString("IdType", "");
	 								      			editor.putString("IdCode", "");
													editor.commit();
	 												//Log.e("erorr",schedule.getDoctorName()+schedule.getDoctorId()+":"+schedule.getTimeRange()+" 可预约数："+schedule.getAvailableNum());
	 												if(sp.getString("username", "").equals("")){
	 													Toast.makeText(activity, "请先登录", Toast.LENGTH_SHORT);
	 													//if(!sp.getBoolean("loginsuccess", false)){
	 														activity.startActivity(new Intent(activity,LoginActivity.class));
	 													//}
	 												}else{
		 												if(!sp.getString("defaultcardno","").equals("")){
		 													
		 													editor.putString("owner", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"owner",""));
		 													editor.putString("cardnum", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"cardnum",""));
		 													editor.putString("cardtype", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"cardtype",""));
		 													editor.putString("idnumber", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"idnumber",""));
		 													editor.putString("idtype", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"idtype",""));
		 													editor.putString("phonenumber", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"phonenumber",""));
		 													editor.commit();
		 													BookingAction action=new BookingAction(activity);
		 													action.confirmBooking();
		 												}else{
	 														activity.startActivity(new Intent(activity,ListCardActivity.class));
		 												}
	 												}
	 											}
	 										});
		 									
		 										scheduleDetailLayout.addView(oneButton);
		 										
		 									}
		 								}
	 								}
	 							}});
	    	            	
	    	            	row.addView(days);
	    	            	count++;
	            		}
	            	}
	            	scheduleLayout.addView(row);
	            }
	
		            
		            scheduleText.setText("");//显示有排班的日期：
		            scheduleText.setVisibility(View.GONE);
		            
	           
         } else{
         	
	            scheduleText.setText("没有排班");//显示有排班的日期：
	          //  scheduleDetailLayout.setVisibility(View.GONE);
         }

	}
	
	
}