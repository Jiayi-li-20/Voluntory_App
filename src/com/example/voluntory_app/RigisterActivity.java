package com.example.voluntory_app;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Build;

public class RigisterActivity extends ActionBarActivity implements OnClickListener,OnLongClickListener{
	private EditText et_name, et_pass, et_phone, et_rpass;
	private Button mLoginButton,mRegister,ONLYTEST,cancel1,cancel2,cancel3,cancel4,visibleset,visibleset2;
	private Button bt_username_clear;
	private Button bt_pwd_clear;
	private Button bt_pwd_eye;
	private Button bt_userphone_clear;
	private Button bt_rpwd_clear;
	private Button bt_rpwd_eye;
	private TextWatcher username_watcher;       
	private TextWatcher password_watcher;  
	private TextWatcher userphone_watcher;       
	private TextWatcher rpassword_watcher;  
	private RadioGroup mrg;
	private RadioButton mr1, mr2;
	SQLiteDatabase db;  
	
	int radioflag = 1;
	int selectIndex=1;
	 int tempSelect=selectIndex;
	 int visiflag1 = 0;
	 int visiflag2 = 0;
	 boolean isReLogin=false;
	 /*public final static int REGIS_ENABLE=0x01;    //注册完毕了
	 public final static int REGIS_UNABLE=0x02;    //注册完毕了
	 public final static int PASS_ERROR=0x03;      //注册完毕了
	 public final static int NAME_ERROR=0x04;      //注册完毕了
	 final Handler UiMangerHandler = new Handler(){   
		  @Override    
		  public void handleMessage(Message msg) {  
		   // TODO Auto-generated method stub
		   switch(msg.what){  
		   case REGIS_ENABLE:  
		    mLoginButton.setClickable(true);            
//		    mLoginButton.setText(R.string.login);
		    break;
		   case REGIS_UNABLE:
		    mLoginButton.setClickable(false);
		    break;
		   case PASS_ERROR:
		    
		    break;
		   case NAME_ERROR:
		    break;
		   }     
		   super.handleMessage(msg);
		  }   
		 };*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rigister_layout);
		
		et_name = (EditText) findViewById(R.id.username);
		  et_pass = (EditText) findViewById(R.id.password);
		  et_phone = (EditText) findViewById(R.id.userphone);
		  et_rpass = (EditText) findViewById(R.id.repassword);
		  
		  bt_username_clear = (Button)findViewById(R.id.bt_username_clear);
		  bt_userphone_clear = (Button)findViewById(R.id.bt_userphone_clear);
		  bt_pwd_clear = (Button)findViewById(R.id.bt_pwd_clear);
		  bt_rpwd_clear = (Button)findViewById(R.id.bt_rpwd_clear);
		  bt_pwd_eye = (Button)findViewById(R.id.bt_pwd_eye);
		  bt_rpwd_eye = (Button)findViewById(R.id.bt_rpwd_eye);
		  bt_username_clear.setOnClickListener(this);
		  bt_pwd_clear.setOnClickListener(this);
		  bt_pwd_eye.setOnClickListener(this);
		  bt_userphone_clear.setOnClickListener(this);
		  bt_rpwd_clear.setOnClickListener(this);
		  bt_rpwd_eye.setOnClickListener(this);
		  cancel1 = (Button) findViewById(R.id.bt_username_clear);
		  cancel2 = (Button) findViewById(R.id.bt_userphone_clear);
		  cancel3 = (Button) findViewById(R.id.bt_pwd_clear);
		  cancel4 = (Button) findViewById(R.id.bt_rpwd_clear);
		  cancel1.setOnClickListener(this);
		  cancel2.setOnClickListener(this);
		  cancel3.setOnClickListener(this);
		  cancel4.setOnClickListener(this);
		  initWatcher();
		  et_name.addTextChangedListener(username_watcher);
		  et_pass.addTextChangedListener(password_watcher);
		  et_phone.addTextChangedListener(userphone_watcher);
		  et_rpass.addTextChangedListener(rpassword_watcher);
		  
		  mLoginButton = (Button) findViewById(R.id.login);
		  mRegister    = (Button) findViewById(R.id.register);
		  visibleset =(Button) findViewById(R.id.bt_pwd_eye);
		  visibleset2 =(Button) findViewById(R.id.bt_rpwd_eye);
		  ONLYTEST     = (Button) findViewById(R.id.register);
		  ONLYTEST.setOnClickListener(this);      
		  ONLYTEST.setOnLongClickListener((OnLongClickListener) this);
		  mLoginButton.setOnClickListener(this);            
		  mRegister.setOnClickListener(this);  
		  visibleset.setOnClickListener(this);
		  visibleset2.setOnClickListener(this);
		  
		  mrg=(RadioGroup)findViewById(R.id.radioGroup);
		  mr1=(RadioButton)findViewById(R.id.person);
		  mr2=(RadioButton)findViewById(R.id.site);
		  mrg.setOnCheckedChangeListener(rglistener);
		 
	}
	
	private RadioGroup.OnCheckedChangeListener rglistener=new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedid) {
			if(checkedid==mr1.getId()){
				radioflag=0;
			}else if(checkedid==mr2.getId()){
				radioflag=1;
			}
		}
	};

	 /**
	  * 手机号，密码输入控件公用这一个watcher
	  */
	 private void initWatcher() {
	  userphone_watcher = new TextWatcher() {
	   public void onTextChanged(CharSequence s, int start, int before, int count) {}
	   public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
	   public void afterTextChanged(Editable s) {
	    et_pass.setText("");
	    if(s.toString().length()>0){
	     bt_userphone_clear.setVisibility(View.VISIBLE);
	    }else{
	     bt_userphone_clear.setVisibility(View.INVISIBLE);
	    }
	   }
	  };
	  username_watcher = new TextWatcher() {
		   public void onTextChanged(CharSequence s, int start, int before, int count) {}
		   public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
		   public void afterTextChanged(Editable s) {
		    if(s.toString().length()>0){
		     bt_username_clear.setVisibility(View.VISIBLE);
		    }else{
		     bt_username_clear.setVisibility(View.INVISIBLE);
		    }
		   }
		  };
	  
	  password_watcher = new TextWatcher() {
	   public void onTextChanged(CharSequence s, int start, int before, int count) {}
	   public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
	   public void afterTextChanged(Editable s) {
	    if(s.toString().length()>0){
	     bt_pwd_clear.setVisibility(View.VISIBLE);
	    }else{
	     bt_pwd_clear.setVisibility(View.INVISIBLE);
	    }
	   }
	  };
	  rpassword_watcher = new TextWatcher() {
		   public void onTextChanged(CharSequence s, int start, int before, int count) {}
		   public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
		   public void afterTextChanged(Editable s) {
		    if(s.toString().length()>0){
		     bt_rpwd_clear.setVisibility(View.VISIBLE);
		    }else{
		     bt_rpwd_clear.setVisibility(View.INVISIBLE);
		    }
		   }
		  };
	 }
	 public Boolean addVolUser(String name, String phone, String password, String flag) {  
	        String str = "insert into vt values(?,?,?,?) ";  
	        LoginActivity main = new LoginActivity();  
	        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()  
	                + "/test.dbs", null);  
	        main.db = db;  
	        try {  
	            db.execSQL(str, new String[] { name, phone,password,flag});
	            return true;  
	        } catch (Exception e) {  
	            main.createDb();  
	        }  
	        return false;  
	    }  
	 
	 public Boolean addsiteUser(String name, String phone, String password, String flag) {  
	        String str = "insert into st values(?,?,?,?) ";  
	        LoginActivity main = new LoginActivity();  
	        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()  
	                + "/test.dbs", null);  
	        main.db = db;  
	        try {  
	            db.execSQL(str, new String[] { name, phone,password,flag});  
	            return true;  
	        } catch (Exception e) {  
	            main.createDb();  
	        }  
	        return false;  
	    }  
	 
	 @Override
	public void onClick(View arg0) {
		  // TODO Auto-generated method stub
		  switch (arg0.getId()) {
		  case R.id.login:  //登陆
		//   login(); 
			 Intent intent=new Intent(RigisterActivity.this,LoginActivity.class);   
		     startActivity(intent);
			  break;
		  
		  case R.id.register:    //注册新的用户
			  String name = et_name.getText().toString();  
              String password = et_pass.getText().toString();  
              String repassword = et_rpass.getText().toString();
              String phone = et_phone.getText().toString();
 
              if (!((name.equals("") && password.equals("") && repassword.equals("") && phone.equals("")))&&password!=repassword) {
            	  if(radioflag!=1){
            		  if (addVolUser(name, phone,password,"0")) {  
            			  DialogInterface.OnClickListener ss = new DialogInterface.OnClickListener() {  
            				  @Override  
            				  public void onClick(DialogInterface dialog, int which) {  
                              // 跳转到登录界面   
                				
                					Intent intent1=new Intent();
                					intent1.setClass(RigisterActivity.this, LoginActivity.class);
                					startActivity(intent1);  
                					RigisterActivity.this.onDestroy();
                				
                          }  
            			};
                              new AlertDialog.Builder(RigisterActivity.this)  
                              	.setTitle("Succsee!").setMessage("注册成功")  
                              	.setPositiveButton("确定", ss).show();  

            		  } else {  
            			  new AlertDialog.Builder(RigisterActivity.this)  
                              	.setTitle("Failure!").setMessage("注册失败")  
                              	.setPositiveButton("确定", null).show();  
            		  }  
            	  }else if(radioflag==1){
            		  if (addsiteUser(name, phone,password,"1")) {  
            			  DialogInterface.OnClickListener ss = new DialogInterface.OnClickListener() {  
            				  public void onClick(DialogInterface dialog, int which) {
            					  Intent intent2=new Intent();
                    			  	intent2.setClass(RigisterActivity.this, LoginActivity.class);
                    			  	startActivity(intent2); 
                    			  	RigisterActivity.this.onDestroy();
            				  }
            			  };
            			  new AlertDialog.Builder(RigisterActivity.this)  
                        	.setTitle("Succsee!").setMessage("注册成功")  
                        	.setPositiveButton("确定", ss).show();  

            		  } else {  
            			  new AlertDialog.Builder(RigisterActivity.this)  
                        	.setTitle("Failure!").setMessage("注册失败")  
                        	.setPositiveButton("确定", null).show(); 
            		  	}
            	  }
              }else {
            	  new AlertDialog.Builder(RigisterActivity.this)  
                  .setTitle("Warning!").setMessage("输入框不能为空")  
                  .setPositiveButton("确定", null).show();
              } 
		   break;
		  case R.id.bt_username_clear:
			  TextView tv1=(TextView) findViewById(R.id.username);
			  tv1.setText("");
			  break;
		  case R.id.bt_userphone_clear:
			  TextView tv2=(TextView) findViewById(R.id.userphone);
			  tv2.setText("");
			  break;
		  case R.id.bt_pwd_clear:
			  TextView tv3=(TextView) findViewById(R.id.password);
			  tv3.setText("");
			  break;
		  case R.id.bt_rpwd_clear:
			  TextView tv4=(TextView) findViewById(R.id.repassword);
			  tv4.setText("");
			  break;
		  case R.id.bt_pwd_eye:
			  TextView passwd=(EditText)findViewById(R.id.password);
			  if(visiflag1!=1){
				  passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				  visiflag1 = 1;
			  }else{
				  passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
				  visiflag1 = 0;
			  }
			  break;
		  case R.id.bt_rpwd_eye:
			  TextView repasswd=(EditText)findViewById(R.id.repassword);
			  if(visiflag2!=1){
				  repasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				  visiflag2 = 1;
			  }else{
				  repasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
				  visiflag2 = 0;
			  }
			  break;
		  }
		 }

	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	 @Override  
	    protected void onDestroy() {  
	        super.onDestroy();  
	 }
	 
	 @Override  
	    protected void onResume() {  
	        super.onResume(); 
	 }

	@Override  
    protected void onPause() {  
		super.onPause(); 
	}
	

}
