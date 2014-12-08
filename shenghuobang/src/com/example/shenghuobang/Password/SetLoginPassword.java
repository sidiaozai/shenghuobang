package com.example.shenghuobang.Password;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shenghuobang.CommonValue;
import com.example.shenghuobang.R;
import com.example.shenghuobang.StartPageActivity;

public class SetLoginPassword extends Activity  {
	
	private SharedPreferences mySharedPreferences; 
	private TextView textView1;
	private EditText etPassword;
	private Button btnSetPassword;
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
  
		setContentView(R.layout.activity_password_set);
		mySharedPreferences= getSharedPreferences("shenghuobang", Activity.MODE_PRIVATE); 
		
		
		textView1 = (TextView) findViewById(R.id.textView1);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etPassword = (EditText) findViewById(R.id.etPassword);
//		etPassword.setFocusable(true);
//		etPassword.setFocusableInTouchMode(true);
//		etPassword.requestFocus();
//		Timer timer = new Timer();
//
//	     timer.schedule(new TimerTask()
//	     {
//	         public void run() 
//	         {
//	        	 InputMethodManager inputManager = (InputMethodManager)etPassword.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//	             inputManager.showSoftInput(etPassword, 0);
//	         }
//	     }, 498);
	     
		btnSetPassword = (Button) findViewById(R.id.btnSetPassword);
		
		btnSetPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String password = etPassword.getText().toString();
				if(password==null){
					textView1.setText("密码不能为空，请重新输入");
					return;
				}else{
					SharedPreferences.Editor editor = mySharedPreferences.edit(); 
					editor.putString(CommonValue.LOGIN_PASSWORD, password);
					editor.commit();
					setResult(1);
					finish();
				}
				
			}
		});
	}

}
