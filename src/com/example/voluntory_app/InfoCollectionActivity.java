package com.example.voluntory_app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class InfoCollectionActivity extends ActionBarActivity{

	private EditText slat, slong, sname, scon, sqqnum, snum;
	private Button loc, upload, back;
	public LocationClient mLocationClient = null;
	private MyLocationListener myListener = new MyLocationListener();
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.siteinfo_layout);
		slat=(EditText)findViewById(R.id.latitude);
		slong=(EditText)findViewById(R.id.longitude);
		sname=(EditText)findViewById(R.id.sitename);
		scon=(EditText)findViewById(R.id.contacts);
		sqqnum=(EditText)findViewById(R.id.qqnum);
		snum=(EditText)findViewById(R.id.requestnum);
		loc=(Button)findViewById(R.id.siteloc);
		upload=(Button)findViewById(R.id.upload);
		back=(Button)findViewById(R.id.back);
		
		mLocationClient = new LocationClient(getApplicationContext());     
	    //声明LocationClient类
	    mLocationClient.registerLocationListener(myListener);    
	    //注册监听函数
	    
	    LocationClientOption option = new LocationClientOption();

	    option.setLocationMode(LocationMode.Hight_Accuracy);
	    option.setCoorType("bd09ll");
	    option.setScanSpan(1000);
	    option.setOpenGps(true);
	    option.setLocationNotify(true);
	    option.setIgnoreKillProcess(false);
	    option.SetIgnoreCacheException(false);
	    option.setEnableSimulateGps(false);
	    mLocationClient.setLocOption(option);
	    
	    /*int num = Integer.parseInt(snum.getText().toString());*/
	    
	    
	    
	    loc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mLocationClient.start();
				slat.setText(String.valueOf(myListener.getJing()));
				slong.setText(String.valueOf(myListener.getWei()));
			}
	    	
	    });
	    

	    
	    upload.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				if(addsiteInfo(slat.getText().toString(),slong.getText().toString(),
						sname.getText().toString(),sqqnum.getText().toString(),scon.getText().toString(),
						Integer.parseInt(snum.getText().toString()))){
				 new AlertDialog.Builder(InfoCollectionActivity.this)  
               	.setTitle("Succsee!").setMessage("提交成功")  
               	.setPositiveButton("确定", null).show();
				}else {
					new AlertDialog.Builder(InfoCollectionActivity.this)  
	               	.setTitle("Failure!").setMessage("提交失败")  
	               	.setPositiveButton("确定", null).show();
				}
			}
	    	
	    });   
	    
	    back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent3=new Intent();
				intent3.setClass(InfoCollectionActivity.this, SChooseActivity.class);
				startActivity(intent3);  
				// 销毁当前activity  
				InfoCollectionActivity.this.onDestroy();
			}
	    	
	    });
               
	}
	


	public Boolean addsiteInfo(String a,String b,String c,String d,String e,int f){
		String str = "insert into si values(?,?,?,?,?,?) ";  
        LoginActivity main = new LoginActivity();  
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()  
                + "/test.dbs", null);  
        main.db = db;  
        try {  
            db.execSQL(str, new Object[]{a,b,c,d,e,f});;  
            return true;  
        } catch (Exception e1) {  
        	main.createDb3();
        	/*db.execSQL("create table si( latitude varchar(30),longitude varchar(30),
        	name varchar(30),qqnum varchar(30),phone varchar(30) primary key,renum integer(100))"); */ 
        }  

		
		return false;
    }
	
}
