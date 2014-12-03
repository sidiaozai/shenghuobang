package com.example.shenghuobang.Setting;



import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.example.shenghuobang.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;


public class UpdateDataActivity extends Activity {
	
	private  SaveCallback saveCallback;
	private SpringProgressView progressView;
	private Handler mHandler;
	
	private sqliteDataBase.Bll.Charge bllCharge;
	private sqliteDataBase.Bll.Unforget bllUnforget;
	private sqliteDataBase.Bll.Password bllPassword;
	
	private AVObject ChargeObject = new AVObject("Charge");
	private AVObject UnforgetObject = new AVObject("Unforget");
	private AVObject PasswordObject = new AVObject("Password");
	int counts;
	int count=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_update_data);
		
		bllCharge = new sqliteDataBase.Bll.Charge(UpdateDataActivity.this);
		bllUnforget = new sqliteDataBase.Bll.Unforget(UpdateDataActivity.this);
		bllPassword = new sqliteDataBase.Bll.Password(UpdateDataActivity.this);

//		ChargeObject.deleteInBackground();
//		ChargeObject.saveInBackground();
//		UnforgetObject.deleteInBackground();
//		ChargeObject.saveInBackground();
//		PasswordObject.deleteInBackground();
//		ChargeObject.saveInBackground();
		
		
		counts = bllCharge.getCount()+ bllUnforget.getCount()+ bllPassword.getCount();
		
		
		AVOSCloud.initialize(this, "qcrjy7s8b5vforciw86pptxnhypnyt5n67yyrwnty2f2vcj4", "udpajg0bbynronjwfzqcvyfk4kfc6ccjbluzamo9u6gdmdhb");
		
		progressView = (SpringProgressView) findViewById(R.id.spring_progress_view);
		
		progressView.setMaxCount(counts);
      
		saveCallback = new SaveCallback() {
			
			@Override
			public void done(AVException arg0) {
				count++;
				progressView.setCurrentCount(count);
				
				if(count>=counts){
	        		Toast.makeText(getApplicationContext(), "ͬ���������", Toast.LENGTH_SHORT).show();
	        		finish();
	        	}
				
			}
		};
		
        updateCharge();
        updateUnforget();
        updatePassword();
        
	}
	private void  updateCharge(){
		Cursor cursor = bllCharge.query();
		while(cursor.moveToNext()){
			
			ChargeObject = new AVObject("Charge");
			int idIndex = cursor.getColumnIndex("id");
    		int yearIndex = cursor.getColumnIndex("year");
    		int monthIndex = cursor.getColumnIndex("month");
    		int dayIndex = cursor.getColumnIndex("day");
    		
    		int typeIndex = cursor.getColumnIndex("type");
    		int sumIndex = cursor.getColumnIndex("sum");
    		int desIndex = cursor.getColumnIndex("des");
    		
    		int id = cursor.getInt(idIndex);

    		int year = cursor.getInt(yearIndex);
    		int month = cursor.getInt(monthIndex);
    		int day = cursor.getInt(dayIndex);
    		
    		int sum = cursor.getInt(sumIndex);
    		int type = cursor.getInt(typeIndex);
    		String des = cursor.getString(desIndex);
    		
    		ChargeObject.put("id", id);
    		ChargeObject.put("year", year);
    		ChargeObject.put("month", month);
    		ChargeObject.put("day", day);
    		ChargeObject.put("type", type);
    		ChargeObject.put("sum", sum);
    		ChargeObject.put("des", des);

    		ChargeObject.saveInBackground(saveCallback);
		}
	}
	private void updateUnforget(){
		Cursor cursor = bllUnforget.query();
		while(cursor.moveToNext()){
			
			UnforgetObject = new AVObject("Unforget");
			
			int id;
			int year,month,day;
			int hour,minute,second;
			String name;
			String soundFileName;
			
			int idIndex = cursor.getColumnIndex("id");
    		int yearIndex = cursor.getColumnIndex("year");
    		int monthIndex = cursor.getColumnIndex("month");
    		int dayIndex = cursor.getColumnIndex("day");
    		int hourIndex = cursor.getColumnIndex("hour");
    		int minuteIndex = cursor.getColumnIndex("minute");
    		int secondIndex = cursor.getColumnIndex("second");
    		int nameIndex = cursor.getColumnIndex("name");
    		int soundFileNameIndex = cursor.getColumnIndex("soundFileName");
    		
    		id = cursor.getInt(idIndex);
    		year = cursor.getInt(yearIndex);
    		month = cursor.getInt(monthIndex);
    		day = cursor.getInt(dayIndex);
    		hour = cursor.getInt(hourIndex);
    		minute = cursor.getInt(minuteIndex);
    		second = cursor.getInt(secondIndex);
    		name = cursor.getString(nameIndex);
    		soundFileName = cursor.getString(soundFileNameIndex);
    		
    		
    		UnforgetObject.put("id", id);
    		UnforgetObject.put("year", year);
    		UnforgetObject.put("month", month);
    		UnforgetObject.put("day", day);
    		UnforgetObject.put("hour", hour);
    		UnforgetObject.put("minute", minute);
    		UnforgetObject.put("second", second);
    		UnforgetObject.put("name", name);
    		UnforgetObject.put("soundFileName", soundFileName);

    		UnforgetObject.saveInBackground(saveCallback);
		}
		
	}
	private void updatePassword(){
		Cursor cursor = bllPassword.query();
		while(cursor.moveToNext()){
			
			PasswordObject = new AVObject("Password");
			
			int id;
			String name;
			String password;
			String soundFileName;
			
			int idIndex =  cursor.getColumnIndex("id");
    		int nameIndex = cursor.getColumnIndex("name");
    		int passwordIndex = cursor.getColumnIndex("passWord");
    		int soundFileNameIndex = cursor.getColumnIndex("soundFileName");
    		
    		id = cursor.getInt(idIndex);
    		name = cursor.getString(nameIndex);
    		password = cursor.getString(passwordIndex);
    		soundFileName = cursor.getString(soundFileNameIndex);
    		
    		
    		PasswordObject.put("id", id);
    		PasswordObject.put("name", name);
    		PasswordObject.put("password", password);
    		PasswordObject.put("soundFileName", soundFileName);

    		PasswordObject.saveInBackground(saveCallback);
		}
	}
}