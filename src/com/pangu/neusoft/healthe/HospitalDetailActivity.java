package com.pangu.neusoft.healthe;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.geniusgithub.lazyloaddemo.cache.ImageLoader;
import com.pangu.neusoft.adapters.HospitalList;
import com.pangu.neusoft.adapters.HospitalListAdapter;
import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.Coordinates;
import com.pangu.neusoft.core.models.Hospital;
import com.pangu.neusoft.core.models.HospitalInfoReq;
import com.pangu.neusoft.core.models.HospitalReq;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.drawable;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
//import com.pangu.neusoft.tools.AsyncBitmapLoader;
//import com.pangu.neusoft.tools.AsyncBitmapLoader.ImageCallBack;
import com.pangu.neusoft.tools.StringMethods;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HospitalDetailActivity extends FatherActivity {
	private String hospitalId;
	private String hospitalVersion;
	private WebService service;
	private ProgressDialog mProgressDialog; 
	Hospital hospital=new Hospital();
	SharedPreferences sp;
	Editor editor;
	String[] args;
	String busline;
	String bus;
	private ImageView pic;
	private TextView hospitalIdText;
	private TextView hospitalNameText;
	private TextView hospitallevelText;
	private TextView hospitalInfoText;
	private TextView hospitalAddressText;
	private TextView hospitalZipCodeText;
	private TextView hospitalTelephoneText;
	private TextView hospitalFaxText;
	private TextView hospitalWebSiteText;
	private TextView hospitalbusText;
	//private AsyncBitmapLoader asyncImageLoader; 
	
	private ImageButton call_btn;
	private ImageButton location_btn;
	private Button booking_btn;
	
	private String latitude;
	private String longitude;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp= getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor=sp.edit();
		service=new WebService();
		setContentView(R.layout.detail_hospital);
		super.setactivitytitle("医院信息");
		hospitalId=this.getIntent().getStringExtra("hospitalId");
		hospitalVersion=this.getIntent().getStringExtra("hospitalVersion");
		
		location_btn=(ImageButton)findViewById(R.id.dingwei);
		location_btn.setOnClickListener(location);
		call_btn=(ImageButton)findViewById(R.id.hospital_tell);
		call_btn.setOnClickListener(call);
		
		hospitalIdText=(TextView)findViewById(R.id.hospital_detail_id);
		hospitalNameText=(TextView)findViewById(R.id.hospital_detail_name);
		hospitallevelText=(TextView)findViewById(R.id.hospital_detail_level_grade);
		hospitalInfoText=(TextView)findViewById(R.id.hospital_detail_intro);
		hospitalAddressText=(TextView)findViewById(R.id.hospital_detail_address);
		hospitalZipCodeText=(TextView)findViewById(R.id.hospital_detail_zipcode);
		hospitalTelephoneText=(TextView)findViewById(R.id.hospital_detail_phone);
		hospitalFaxText=(TextView)findViewById(R.id.hospital_detail_fax);
		hospitalWebSiteText=(TextView)findViewById(R.id.hospital_detail_website);
		pic=(ImageView)findViewById(R.id.hospital_detail_pictureurl);
		
		hospitalbusText=(TextView)findViewById(R.id.hospital_detail_bus);
		//asyncImageLoader = new AsyncBitmapLoader();  
		
		//call_btn = (Button)findViewById(R.id.hospital_detail_call_btn);
		//call_btn.setVisibility(View.INVISIBLE);
		//location_btn = (Button)findViewById(R.id.hospital_detail_location_btn);
		//location_btn.setVisibility(View.INVISIBLE);
		
		booking_btn = (Button)findViewById(R.id.hospital_detail_booking_btn);
		
		int width=sp.getInt("screen_width", 0);
        int height=sp.getInt("screen_height", 0); 
		
		pic.getLayoutParams().width=width;
		pic.getLayoutParams().height=height/3;
		
		booking_btn.setOnClickListener(booking);
		getDataFromInternet(); 
	}

	
	OnClickListener call=new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(!hospitalTelephoneText.equals("TextView")){
				showCallView();
			}
		}
	};
	OnClickListener location=new OnClickListener(){
		@Override
		public void onClick(View v) {
				showLocationView();
		}
	};
	OnClickListener booking=new OnClickListener(){
		@Override
		public void onClick(View v) {
			//记录地区信息。其他医院、科室、医生信息要清空		
			if(hospitalId!=null&&!hospitalNameText.getText().equals("TextView")){
				editor.putString("hospitalId", hospitalId);
				editor.putString("hospitalName", hospitalNameText.getText().toString());			    	
		    	
				editor.putString("departmentId", "NG");
				editor.putString("departmentName", "请选择科室");
		    				    	
				editor.putString("doctorId", "NG");
				editor.putString("doctorName", "请选择医生");
				
				editor.commit();
				startActivity(new Intent (HospitalDetailActivity.this, DepartmentListActivity.class));
				finish();
			}else{
				startActivity(new Intent (HospitalDetailActivity.this, DepartmentListActivity.class));
				finish();
			}
		}
	};
	
	public void showCallView(){
		String phone=hospitalTelephoneText.getText().toString();
		List<Map<String,String>> res= StringMethods.getPhone(phone);
		args=new String[res.size()];
		int i=0;
		for(Map<String,String> a:res){
		 for (Map.Entry<String, String> entry : a.entrySet()) {
			   String key = entry.getKey().toString();
			   String value = entry.getValue().toString();
			   args[i]=value;			 
		  }
		 i++;
		}
		
		new AlertDialog.Builder(this).setTitle("电话表").setItems(
				args, new DialogInterface.OnClickListener() {  
					 
                    @Override 
                    public void onClick(DialogInterface dialog, int which) {  
                    	String phone=args[which];
						
						  Intent myIntentDial = new Intent(  
	                                Intent.ACTION_CALL,Uri.parse("tel:"+phone)  
	                        );  
	                          
	                        HospitalDetailActivity.this.startActivity(myIntentDial);  
	                      
                    }  
                }).setNegativeButton(
				"确定", null).show();

	}
	
	public void showLocationView(){
		Intent intent=getIntent();
		intent.setClass(HospitalDetailActivity.this, MapActivity.class);
		intent.putExtra("address", hospitalAddressText.getText().toString());
		intent.putExtra("latitude", latitude);
		intent.putExtra("longitude", longitude);
		
		
		startActivity(intent);
	}
	
	
	public void getDataFromInternet(){

		mProgressDialog = new ProgressDialog(HospitalDetailActivity.this);   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        
        new AsyncTask<Void, Void, Boolean>(){
		    
			@Override  
	        protected void onPreExecute() {   
	            super.onPreExecute(); 
	            mProgressDialog.show();
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				HospitalInfoReq req=new HospitalInfoReq();
				
				req.setHospitalId(hospitalId);
				req.setAucode(GET.Aucode);
				SoapObject  obj = service.getHospitalDetail(req);
				
				if(obj!=null){					
					SoapObject areaObject=(SoapObject)obj.getProperty("hospitaldetail");					
						String hospitalId=areaObject.getProperty("hospitalId").toString();
						String hospitalName=areaObject.getProperty("hospitalName").toString();
						String grade=areaObject.getProperty("grade").toString();
						String level=areaObject.getProperty("level").toString();
						String address=areaObject.getProperty("address").toString();
						String intro= areaObject.getProperty("intro").toString();
						String zipCode=areaObject.getProperty("zipCode").toString();
						String telephone=areaObject.getProperty("telephone").toString();
						String fax=areaObject.getProperty("fax").toString();
						String website=areaObject.getProperty("website").toString();
						
						
						
						
						SoapObject coordinatesObj=(SoapObject)areaObject.getProperty("coordinates");
							 latitude=coordinatesObj.getProperty("latitude").toString();
							 longitude=coordinatesObj.getProperty("longitude").toString();
							
							Coordinates coordinates=new Coordinates();
							int version=0;
							try{
								coordinates.setLatitude(Double.parseDouble(latitude));
								coordinates.setLongitude(Double.parseDouble(longitude));
								version=Integer.parseInt(areaObject.getProperty("version").toString());		
							}catch(Exception ex){
								
							}
						String imageUrl;
						try{
							if(areaObject.getProperty("pictureUrl")==null){
								imageUrl=Setting.DEFAULT_DOC_url;
							}else{
								imageUrl=areaObject.getProperty("pictureUrl").toString();
							}
						}catch(Exception ex){
							imageUrl=Setting.DEFAULT_DOC_url;
						}
						
						hospital.setHospitalId(hospitalId);
						hospital.setHospitalName(hospitalName);
						hospital.setAddress(address);
						hospital.setCoordinates(coordinates);
						hospital.setFax(fax);
						hospital.setGrade(grade);
						hospital.setIntro(intro);
						hospital.setLevel(level);
						hospital.setPictureUrl(imageUrl);
						hospital.setTelephone(telephone);
						hospital.setVersion(version);
						hospital.setWebsite(website);
						hospital.setZipCode(zipCode);
						
						bus= GetBus(hospitalId);
						
					String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
					String msg=obj.getProperty("msg").toString();//返回的信息
					return true;
				}
				else
					return false;
			}
			
			@Override
			protected void onPostExecute(final Boolean result){
				super.onPostExecute(result);
				
				runOnUiThread(new Runnable()
				{
					
					@Override
					public void run()
					{

						if(mProgressDialog.isShowing()){
							mProgressDialog.dismiss();
						} 
						if (result){
							hospitalIdText.setText(hospital.getHospitalId());
							hospitalNameText.setText(hospital.getHospitalName());
							hospitallevelText.setText(hospital.getLevel()+hospital.getGrade());
							hospitalInfoText.setText(hospital.getIntro());
							
							hospitalAddressText.setText(hospital.getAddress());
							hospitalZipCodeText.setText(hospital.getZipCode());
							hospitalTelephoneText.setText(hospital.getTelephone());
							hospitalFaxText.setText(hospital.getFax());
							hospitalWebSiteText.setText(hospital.getWebsite());
							hospitalbusText.setText(bus);
							
							//pic.setText(hospital.getHospitalId());
							hospitalWebSiteText.setOnClickListener(new OnClickListener()
							{
								
								@Override
								public void onClick(View v)
								{
									// TODO Auto-generated method stub
									Intent intent = new Intent();
									
									intent.setAction(intent.ACTION_VIEW);
									
									intent.setData(Uri.parse(hospital.getWebsite().toString()));
									
									startActivity(intent);
								}
							});
//							  Bitmap bitmap=asyncImageLoader.loadBitmap(pic, hospital.getPictureUrl(), new ImageCallBack() {  
//					                
//					                @Override  
//					                public void imageLoad(ImageView imageView, Bitmap bitmap) {  
//					                    // TODO Auto-generated method stub  
//					                    imageView.setImageBitmap(bitmap);  
//					                }  
//					            });  
//					            
//					      
//					            if (bitmap == null) {  
//					                pic.setImageResource(R.drawable.booking_hosp);  
//					            }else{  
//					            	pic.setImageBitmap(bitmap); 
//					            }  
							 pic.setImageResource(R.drawable.booking_hosp);  
							 
							ImageLoader mImageLoader = new ImageLoader(HospitalDetailActivity.this);
				            mImageLoader.DisplayImage(hospital.getPictureUrl(), pic, false);
						}
						
					}
				});
			}
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
			}
		}.execute();
	}
	
	
	private String GetBus(String hospitalid)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		String path = HospitalDetailActivity.this.getResources().getString(R.string.buslineurl);  
        //包装成url的对象   
        URL url;
		try
		{
			url = new URL(path);
			
			HttpURLConnection conn =  (HttpURLConnection) url.openConnection();   
	        conn.setConnectTimeout(50000);  
	        InputStream is =conn.getInputStream();
	        
	        XmlPullParser  parser = Xml.newPullParser();  
	        
		    parser.setInput(is, "utf-8");//设置解析的数据源   
		    int type = parser.getEventType();
			//parser.require(XmlPullParser.START_TAG,null, "hospitalconfig");
		    while (type != XmlPullParser.END_DOCUMENT) {
		    	parser.next();
		    	type=parser.getEventType();
		    	String name=parser.getName();
		    	if (type == XmlPullParser.START_TAG){
		    		String tagName = parser.getName();
		    		if (tagName.equals("hospital"))
					{
		    			//Map<String, String> map = new HashMap<String, String>();
		    			String id = parser.getAttributeValue(0);// 通过属性名来获取属性值
		    			//map.put("id", id);
		    			String route = parser.getAttributeValue(1);// 通过属性索引来获取属性值
		    			//map.put("route", route);
		    			//map.put("name", parser.nextText());
		    			//list.add(map);
		    			if(id.equals(hospitalid)){
		    				busline=route;
		    				break;
		    			}
					}
				}
		    }
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		catch (XmlPullParserException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return busline;
	}



}
