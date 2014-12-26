package com.example.shenghuobang.Password;

import java.util.Timer;
import java.util.TimerTask;

import sqliteDataBase.DatabaseHelper;
import sqliteDataBase.Bll.Password;

import com.example.shenghuobang.CommonValue;
import com.example.shenghuobang.LoginActivity;
import com.example.shenghuobang.MainActivity;
import com.example.shenghuobang.R;
import com.example.shenghuobang.StartPageActivity;
import com.example.shenghuobang.LockPattern.LocusPassWordView.OnCompleteListener;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class LoginPasswordActivity extends Activity {
	
	private Button btnChangePasswordStyle;
	private Button btnNumberPasswordLogin;
	private EditText etPassword;
	private TextView textView1;
	private TextView textView2;
	//private SharedPreferences mySharedPreferences; 
	private CommonValue commonValue;
	private Boolean isPicture=false;
	
	private LinearLayout llText;
	private LinearLayout llPicture;
	
	private int source;
	
	private com.example.shenghuobang.LockPattern.LocusPassWordView lpwv;
	
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
  
		setContentView(R.layout.activity_login);
		
		commonValue = new CommonValue(this);
		
		source = getIntent().getIntExtra(CommonValue.SwitchSource, 0);

		String verPassword = commonValue.getLoginPassword();// mySharedPreferences.getString(CommonValue.LOGIN_PASSWORD, null);
		//没有密码，先设置密码
//		if((verPassword==null)||verPassword.equals("")){
//			setResult(1);
//            return;
//		}
		
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		
		llText = (LinearLayout) findViewById(R.id.llText);
		llPicture = (LinearLayout) findViewById(R.id.llPicture);
		btnChangePasswordStyle = (Button) findViewById(R.id.btnChangePasswordStyle);
		etPassword = (EditText) findViewById(R.id.etPassword);
		
		isPicture = commonValue.getPasswordType();
		
		setPasswordType(isPicture);

		btnChangePasswordStyle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				isPicture =! isPicture;
				setPasswordType(isPicture);
				commonValue.setPasswordType(isPicture);
				
			}
		});
		
		btnNumberPasswordLogin = (Button) findViewById(R.id.btnNumberPasswordLogin);
		btnNumberPasswordLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String password = etPassword.getText().toString();
				String verPassword = commonValue.getLoginPassword();
				
				int loginTimes = commonValue.getLoginTimes();// mySharedPreferences.getInt(CommonValue.LOGIN_TIMES, 10);
				
				if((password==null)||(password.equals(""))){
					textView1.setText("密码不能为空，请重新输入");
				}else if(!password.equals(verPassword)){
					if(loginTimes>5){
						loginTimes--;
						textView1.setText("密码错误，请重新输入,剩余次数："+loginTimes+"次");
						commonValue.setLoginTimes(loginTimes);
						commonValue.setLoginTimes(loginTimes);
					}else if(loginTimes>0){
						loginTimes--;
						textView1.setText("提示：我的QQ密码，剩余次数："+loginTimes+"次");
						commonValue.setLoginTimes(loginTimes);
					}else{
						Toast.makeText(getApplicationContext(), "密码输入次数超过10次，本地密码数据被清除", Toast.LENGTH_SHORT).show();

						deletePasswordDataBase();
						
						commonValue.setLoginTimes(10);
						commonValue.setLoginPassword(null);
						
						
						if(source == CommonValue.AppSource)
							setResult(2);
						finish();
						
//						ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//						activityManager.restartPackage(Process.class.getPackage().getName());
					}
					
				}else {
					commonValue.setLoginTimes(10);
					//commonValue.setLoginPassword(null);
					
					setResult(1);
	                finish();
				}
			}
		});
		
		
		Timer timer = new Timer();

	     timer.schedule(new TimerTask()
	     {
	         public void run() 
	         {
	        	 InputMethodManager inputManager = (InputMethodManager)etPassword.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	             inputManager.showSoftInput(etPassword, 0);
	         }
	     }, 498);
	     
	     
	     lpwv = (com.example.shenghuobang.LockPattern.LocusPassWordView) this.findViewById(R.id.mLocusPassWordView);
	     lpwv.setOnCompleteListener(new OnCompleteListener() {
	    	 @Override
	    	 public void onComplete(String mPassword) {
				
	    		 int loginTimes =commonValue.getLoginTimes();
					
	    		 if (lpwv.verifyPassword(mPassword)) {
						
	    			 setResult(1);
		             commonValue.setLoginTimes(10);
		             finish();
		          } else {
		        	  loginTimes--;
		        	  
		        	  if(loginTimes>5){
		        		  
		        		  textView2.setText("密码错误，请重新输入,剩余次数："+loginTimes+"次");
		        		  commonValue.setLoginTimes(loginTimes);
		        	  }else if(loginTimes>0){
		        		  textView2.setText("提示：我的QQ密码，剩余次数："+loginTimes+"次");
		        		  commonValue.setLoginTimes(loginTimes);
		        	  }else{
		        		  Toast.makeText(getApplicationContext(), "密码输入次数超过10次，本地密码数据被清除", Toast.LENGTH_SHORT).show();
		        		  
		        		  deletePasswordDataBase();
		        		  commonValue.setLoginTimes(10);
		        		  commonValue.setLoginPassword(null);
		        		  
		        		  if(source == CommonValue.AppSource)
		        			  setResult(2);
		        		  finish();
					}
		        	  
		        	  lpwv.markError();
				}
			}
		});
	}
	private void setPasswordType(boolean isPicture){
		if(isPicture==false){
			llPicture.setVisibility(View.VISIBLE);
			llText.setVisibility(View.GONE);
			btnChangePasswordStyle.setText("数字密码");
		}else{
			
			etPassword.setFocusable(true);
			etPassword.setFocusableInTouchMode(true);
			etPassword.requestFocus();
			llPicture.setVisibility(View.GONE);
			llText.setVisibility(View.VISIBLE);
			btnChangePasswordStyle.setText("图形密码");
		}
	}
	private boolean deletePasswordDataBase(){
//		DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
//		databaseHelper.deleteDatabase(getApplicationContext());
		
		sqliteDataBase.Bll.Password bllPassword = new Password(getApplicationContext());
		
		bllPassword.delete();
		return true;
	}
	
}
