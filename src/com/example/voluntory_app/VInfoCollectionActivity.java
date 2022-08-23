package com.example.voluntory_app;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class VInfoCollectionActivity extends ActionBarActivity{

	private EditText vlat, vlong, vqqnum, vcon;
	private Button loc, upload, back;
	public LocationClient mLocationClient = null;
	private MyLocationListener myListener = new MyLocationListener();
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.volinfo_layout);
		
		vlat=(EditText)findViewById(R.id.vlatitude);
		vlong=(EditText)findViewById(R.id.vlongitude);
		vqqnum=(EditText)findViewById(R.id.vqqnum);
		vcon=(EditText)findViewById(R.id.vcontacts);
		loc=(Button)findViewById(R.id.volloc);
		upload=(Button)findViewById(R.id.vupload);
		back=(Button)findViewById(R.id.vback);
		
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
	    
	    
	    
	    
	    loc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mLocationClient.start();
				vlat.setText(String.valueOf(myListener.getJing()));
				vlong.setText(String.valueOf(myListener.getWei()));
			}
	    	
	    });
	    
	    upload.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				/*Info.infos.add(new Info(31.317134, 120.74545, R.drawable.tree, "第五元素社区",  
		                "4008823823", 5));*/
				
				/*VInfo.vinfos.add(new VInfo(myListener.getJing(),myListener.getWei(),R.drawable.tree,
						vqqnum.getText().toString(),vcon.getText().toString())); */

				if(addvolInfo(vlat.getText().toString(),vlong.getText().toString(),
						vqqnum.getText().toString(),vcon.getText().toString())){
				 new AlertDialog.Builder(VInfoCollectionActivity.this)  
               	.setTitle("Succsee!").setMessage("提交成功")  
               	.setPositiveButton("确定", null).show();
				}else{
					new AlertDialog.Builder(VInfoCollectionActivity.this)  
	               	.setTitle("Failure!").setMessage("提交失败")  
	               	.setPositiveButton("确定", null).show();
				}
			}
	    	
	    });   
	    
	    back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent3=new Intent();
				intent3.setClass(VInfoCollectionActivity.this, ChooseActivity.class);
				startActivity(intent3);  
				// 销毁当前activity  
				VInfoCollectionActivity.this.onDestroy();
			}
	    	
	    });
               
	}
	
	public Boolean addvolInfo(String a,String b,String c,String d){
		String str = "insert into vi values(?,?,?,?) ";  
        LoginActivity main = new LoginActivity();  
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()  
                + "/test.dbs", null);  
        main.db = db;  
        try {  
            db.execSQL(str, new Object[]{a,b,c,d});;  
            return true;  
        } catch (Exception e1) {  
        	main.createDb2();
        	 
        }  

		
		return false;
    }


}

