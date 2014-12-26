package com.example.avosclouddemo;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.sns.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button button1;
	private Button button2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
//				AVObject ChargeObject = new AVObject("asdf");
//				ChargeObject.add("name", "asdf");
//				ChargeObject.saveInBackground(new SaveCallback() {
//					
//					@Override
//					public void done(AVException arg0) {
//						
//					}
//				});
//				
//				
//				
//				
//				
//				AVUser currentUser = AVUser.getCurrentUser();
//				if (currentUser != null) {
//					Toast.makeText(getApplicationContext(), currentUser.getUsername(), Toast.LENGTH_SHORT).show();
//				} else {
//					Toast.makeText(getApplicationContext(), "没有用户", Toast.LENGTH_SHORT).show();
//				}
				
				AVUser user = new AVUser();
				user.setUsername("ZhuMeng123");
				user.setPassword("ZhuMeng123");
				user.setEmail("zhumeng1582@sina.cn");
				user.put("phone", "13265468736");
				
				user.signUpInBackground(new SignUpCallback() {
					
					@Override
					public void done(AVException arg0) {
						if(arg0==null){
							Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
							Log.i("Test", arg0.getLocalizedMessage());
						}
						
					}
				});
				
			}
		});
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AVUser.logInInBackground("ZhuMeng123", "ZhuMeng123", new LogInCallback<AVUser>() {
//					
					@Override
					public void done(AVUser arg0, AVException arg1) {
						if(arg0!=null){
							Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(getApplicationContext(), "登陆失败", Toast.LENGTH_SHORT).show();
							
						}
						
						
					}
				});
				
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
