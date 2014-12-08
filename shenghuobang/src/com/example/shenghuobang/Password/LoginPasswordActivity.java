package com.example.shenghuobang.Password;

import java.util.Timer;
import java.util.TimerTask;

import com.example.shenghuobang.CommonValue;
import com.example.shenghuobang.R;
import com.example.shenghuobang.StartPageActivity;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class LoginPasswordActivity extends Activity {
	
	private Button btnChangePasswordStyle;
	private EditText etPassword;
	private SharedPreferences mySharedPreferences; 
	private Boolean isPicture=false;
	
	private LinearLayout llText;
	private LinearLayout llPicture;
	
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
  
		setContentView(R.layout.activity_password_input);
		
		mySharedPreferences= getSharedPreferences("shenghuobang", Activity.MODE_PRIVATE); 
		
		String verPassword = mySharedPreferences.getString(CommonValue.LOGIN_PASSWORD, null);
		//û�����룬����������
		if((verPassword==null)||verPassword.equals("")){
			setResult(1);
            return;
		}
		
		llText = (LinearLayout) findViewById(R.id.llText);
		llPicture = (LinearLayout) findViewById(R.id.llPicture);
		
		btnChangePasswordStyle = (Button) findViewById(R.id.btnChangePasswordStyle);
		btnChangePasswordStyle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(isPicture==false){
					isPicture = true;
					llPicture.setVisibility(View.VISIBLE);
					llText.setVisibility(View.GONE);
					btnChangePasswordStyle.setText("��������");
				}else{
					isPicture=false;
					llPicture.setVisibility(View.GONE);
					llText.setVisibility(View.VISIBLE);
					btnChangePasswordStyle.setText("ͼ������");
				}
				
			}
		});
		
		etPassword = (EditText) findViewById(R.id.etPassword);
		etPassword.setFocusable(true);
		etPassword.setFocusableInTouchMode(true);
		etPassword.requestFocus();
		Timer timer = new Timer();

	     timer.schedule(new TimerTask()
	     {
	         public void run() 
	         {
	        	 InputMethodManager inputManager = (InputMethodManager)etPassword.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	             inputManager.showSoftInput(etPassword, 0);
	         }
	     }, 498);
	     
		etPassword.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				
				//Toast.makeText(getApplicationContext(), "onTextChanged", Toast.LENGTH_LONG).show(); 
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				
				//Toast.makeText(getApplicationContext(), "beforeTextChanged", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				
				String password = etPassword.getText().toString();
				
				String verPassword = mySharedPreferences.getString(CommonValue.LOGIN_PASSWORD, null);
				
				if(password.equals(verPassword)){
					setResult(1);
	                finish();
				}
			}
		});
		
	}
	
}
