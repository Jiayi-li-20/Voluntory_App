package com.example.voluntory_app;

import java.net.PasswordAuthentication;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



/**
 * 
 * 
 *  注      册： ValidatePhoneNumActivity  -->  RegisterActivity
 * 
 *  忘记密码   ForgetCodeActivity        -->  RepasswordActivity
 *   
 *  @author liubao.zeng
 *
 */   
public class LoginActivity extends ActionBarActivity implements OnClickListener,OnLongClickListener {
 // 声明控件对象
 private EditText et_name, et_pass;
 private Button mLoginButton,mLoginError,mRegister,ONLYTEST,cancel1,cancel2,visibleset;  
 private int visiflag = 0;
 int selectIndex=1;
 int tempSelect=selectIndex;
 boolean isReLogin=false;
 private int SERVER_FLAG=0;
 private RelativeLayout countryselect;
 private TextView   coutry_phone_sn, coutryName;//
// private String [] coutry_phone_sn_array,coutry_name_array;
 public final static int LOGIN_ENABLE=0x01;    //注册完毕了
 public final static int LOGIN_UNABLE=0x02;    //注册完毕了
 public final static int PASS_ERROR=0x03;      //注册完毕了
 public final static int NAME_ERROR=0x04;      //注册完毕了
 
 public static SQLiteDatabase db;  

 final Handler UiMangerHandler = new Handler(){   
  @Override    
  public void handleMessage(Message msg) {  
   switch(msg.what){  
   case LOGIN_ENABLE:  
    mLoginButton.setClickable(true);            
//    mLoginButton.setText(R.string.login);
    break;
   case LOGIN_UNABLE:
    mLoginButton.setClickable(false);
    break;
   case PASS_ERROR:
    
    break;
   case NAME_ERROR:
    break;
   }     
   super.handleMessage(msg);
  }   
 };
 private Button bt_username_clear;
 private Button bt_pwd_clear;
 private Button bt_pwd_eye;
 private TextWatcher username_watcher;       
 private TextWatcher password_watcher;      
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  requestWindowFeature(Window.FEATURE_NO_TITLE);   //不显示系统的标题栏          
  
  setContentView(R.layout.login_layout);
  et_name = (EditText) findViewById(R.id.username);
  et_pass = (EditText) findViewById(R.id.password);
  
  bt_username_clear = (Button)findViewById(R.id.bt_username_clear);
  bt_pwd_clear = (Button)findViewById(R.id.bt_pwd_clear);
  bt_pwd_eye = (Button)findViewById(R.id.bt_pwd_eye);
  bt_username_clear.setOnClickListener(this);
  bt_pwd_clear.setOnClickListener(this);
  bt_pwd_eye.setOnClickListener(this);
  cancel1 = (Button) findViewById(R.id.bt_username_clear);
  cancel2 = (Button) findViewById(R.id.bt_pwd_clear);
  cancel1.setOnClickListener(this);
  cancel2.setOnClickListener(this);
  initWatcher();
  et_name.addTextChangedListener(username_watcher);
  et_pass.addTextChangedListener(password_watcher);
  
  mLoginButton = (Button) findViewById(R.id.login);
  mLoginError  = (Button) findViewById(R.id.login_error);
  mRegister    = (Button) findViewById(R.id.register);
  visibleset =(Button) findViewById(R.id.bt_pwd_eye);
  ONLYTEST     = (Button) findViewById(R.id.register);
  ONLYTEST.setOnClickListener(this);      
  ONLYTEST.setOnLongClickListener((OnLongClickListener) this);
  mLoginButton.setOnClickListener(new LoginListener());       
  mLoginError.setOnClickListener(this);       
  mRegister.setOnClickListener(this);  
  visibleset.setOnClickListener(this);
  
  db = SQLiteDatabase.openOrCreateDatabase(LoginActivity.this.getFilesDir().toString()  
          + "/test.dbs", null); 
  
//  countryselect=(RelativeLayout) findViewById(R.id.countryselect_layout);
//  countryselect.setOnClickListener(this);
//  coutry_phone_sn=(TextView) findViewById(R.id.contry_sn);
//  coutryName=(TextView) findViewById(R.id.country_name);
 
//  coutryName.setText(coutry_name_array[selectIndex]);    //默认为1
//  coutry_phone_sn.setText("+"+coutry_phone_sn_array[selectIndex]);
 }

 /**
  * 手机号，密码输入控件公用这一个watcher
  */
 private void initWatcher() {
  username_watcher = new TextWatcher() {
   public void onTextChanged(CharSequence s, int start, int before, int count) {}
   public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
   public void afterTextChanged(Editable s) {
    et_pass.setText("");
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
 }
  
 
 @Override
 public void onClick(View arg0) {
  switch (arg0.getId()) {

  case R.id.login_error: //无法登陆(忘记密码了吧)
//   Intent login_error_intent=new Intent();
//   login_error_intent.setClass(LoginActivity.this, ForgetCodeActivity.class);
//   startActivity(login_error_intent);
   break;
  case R.id.register:    //注册新的用户
	  Intent intent2=new Intent();
	  intent2.setClass(LoginActivity.this, RigisterActivity.class);
	  startActivity(intent2);
   
   break;
  case R.id.bt_username_clear:
	  TextView tv1=(TextView) findViewById(R.id.username);
	  tv1.setText("");
	  break;
  case R.id.bt_pwd_clear:
	  TextView tv2=(TextView) findViewById(R.id.password);
	  tv2.setText("");
	  break;
  case R.id.bt_pwd_eye:
	  TextView passwd=(EditText)findViewById(R.id.password);
	  if(visiflag!=1){
		  passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
		  visiflag = 1;
	  }else{
		  passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
		  visiflag = 0;
	  }
	  break;
  }
 }
 /**
  * 登陆
  */
 private void login() {
 }

 

 /**
  * 监听Back键按下事件,方法2:
  * 注意:
  * 返回值表示:是否能完全处理该事件
  * 在此处返回false,所以会继续传播该事件.
  * 在具体项目中此处的返回值视情况而定.
  */ 
 @Override 
 public boolean onKeyDown(int keyCode, KeyEvent event) { 
  if ((keyCode == KeyEvent.KEYCODE_BACK)) {
   if(isReLogin){
    Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
    mHomeIntent.addCategory(Intent.CATEGORY_HOME);
    mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
      | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
    LoginActivity.this.startActivity(mHomeIntent);
   }else{
    LoginActivity.this.finish();
   }
   return false; 
  }else { 
   return super.onKeyDown(keyCode, event); 
  } 
 }
@Override
public boolean onLongClick(View arg0) {
	return false;
}

class LoginListener implements OnClickListener {  
	  
    @Override  
    public void onClick(View v) {  

        String name = et_name.getText().toString();  
        String password = et_pass.getText().toString();  
        if (name.equals("") || password.equals("")) {  
            // 弹出消息框  
            new AlertDialog.Builder(LoginActivity.this).setTitle("错误")  
                    .setMessage("帐号或密码不能空").setPositiveButton("确定", null)  
                    .show();  
            
        } else {  
            isUserinfo(name, password);  
        }  
    }  

    // 判断输入的用户是否正确  
    public Boolean isUserinfo(String name, String pwd) {  
        
    	try{  
            String str="select * from vt where phone=? and password=?";  
            String str2="select * from st where phone=? and password=?";
            Cursor cursor = db.rawQuery(str, new String []{name,pwd});
            Cursor cursor2 = db.rawQuery(str2, new String []{name,pwd});
            if(cursor.getCount()<=0){  
            	if(cursor2.getCount()<=0){
            		new AlertDialog.Builder(LoginActivity.this).setTitle("错误")  
            		.setMessage("帐号或密码错误！").setPositiveButton("确定", null)  
            		.show();  
            		return false;  
            	}else{
            		new AlertDialog.Builder(LoginActivity.this).setTitle("正确")  
                    .setMessage("成功登录").setPositiveButton("确定", null)  
                    .show();  
            		Intent intent2=new Intent(LoginActivity.this,SChooseActivity.class);   
                    startActivity(intent2);
                    return true; 
            	}
            }else{  
                new AlertDialog.Builder(LoginActivity.this).setTitle("正确")  
                .setMessage("成功登录").setPositiveButton("确定", null)  
                .show();  
                Intent intent=new Intent(LoginActivity.this,ChooseActivity.class);   
                startActivity(intent);
                return true;  
            }  
              
        }catch(SQLiteException e){  
            createDb();  
        }  
    	
        return false;  
    }  
  
}  
// 创建数据库和用户表  
public void createDb() {  
    db.execSQL("create table vt( name varchar(30),phone varchar(30) primary key,password varchar(30),flag varchar(10))"); 
    db.execSQL("create table st( name varchar(30),phone varchar(30) primary key,password varchar(30),flag varchar(10))");
    
}  
public void createDb2(){
	db.execSQL("create table vi( latitude varchar(30),longitude varchar(30),qqnum varchar(30),phone varchar(30) primary key)");
}
public void createDb3(){
	db.execSQL("create table si( latitude varchar(30),longitude varchar(30),name varchar(30),qqnum varchar(30),phone varchar(30)"
    		+ " primary key,renum integer(100))");
}
@Override  
public boolean onCreateOptionsMenu(Menu menu) {  
    // Inflate the menu; this adds items to the action bar if it is present.  
    getMenuInflater().inflate(R.menu.main, menu);  
    return true;  
}  

}

