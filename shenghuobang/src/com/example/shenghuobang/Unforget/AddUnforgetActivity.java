package com.example.shenghuobang.Unforget;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.example.shenghuobang.DateTimePickerDialog;
import com.example.shenghuobang.FileOper;
import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import sqliteDataBase.Model.Unforget;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddUnforgetActivity extends Activity {
	
	private Button btnAddUnforgetCancel;
	private Button btnAddUnforgetOK;
	
	private LinearLayout llUnforgetDate;
	private LinearLayout llUnforgetTime;
	
	private TextView tvUnforgetYear;
	private TextView tvUnforgetMonth;
	private TextView tvUnforgetDay;
	
	private TextView tvUnforgetHour;
	private TextView tvUnforgetMinute;
	
	private EditText edUnforgetName;
	
	private Button btnAddUnforgetAudio;
	private String pathName = null; 
	private String fileName = "NULL";
	
	private MediaRecorder mRecorder;
	
	private AlarmManager alarmManager=null;
	
	private sqliteDataBase.Bll.Unforget bllUnforget;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		setContentView(R.layout.activity_add_unforget);
		
		alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
		
		pathName = Environment.getExternalStorageDirectory().getAbsolutePath();  
		
		bllUnforget = new sqliteDataBase.Bll.Unforget(this);
		
		long currentTime = System.currentTimeMillis();
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
		Date date = new Date(currentTime);

		tvUnforgetYear = (TextView) findViewById(R.id.tvUnforgetYear);
		int year = date.getYear()+1900;
		tvUnforgetYear.setText(String.valueOf(year)+"年");
		
		
		tvUnforgetMonth = (TextView) findViewById(R.id.tvUnforgetMonth);
		int month = date.getMonth();
		tvUnforgetMonth.setText(String.format("%02d",month+1)+"月");
		
		tvUnforgetDay = (TextView) findViewById(R.id.tvUnforgetDay);
		int dayOfMonth = date.getDate();
		tvUnforgetDay.setText(String.format("%02d",dayOfMonth)+"日");
		
		tvUnforgetHour = (TextView) findViewById(R.id.tvUnforgetHour);
		int hour = date.getHours();
		tvUnforgetHour.setText(String.format("%02d",hour)+"时");
		
		tvUnforgetMinute = (TextView) findViewById(R.id.tvUnforgetMinute);
		int minute = date.getMinutes();
		tvUnforgetMinute.setText(String.format("%02d",minute)+"分");
		
		edUnforgetName = (EditText) findViewById(R.id.etForgetName);
		
		btnAddUnforgetAudio = (Button)findViewById(R.id.btnAddUnforgetAudio);
		
		btnAddUnforgetAudio.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				
				if(btnAddUnforgetAudio.getText().toString().equals("播放"))
					return false;
				
				if (event.getAction()== MotionEvent.ACTION_UP)//判断按钮释放被释放 
				{
					btnAddUnforgetAudio.setText("播放");
					mRecorder.stop();  
					mRecorder.release();  
					mRecorder = null;  
					return true;
				}else if(event.getAction()== MotionEvent.ACTION_DOWN){
					
					btnAddUnforgetAudio.setText("正在录音");
					
					long curDate = System.currentTimeMillis();
					fileName = String.valueOf(""+curDate+".3gp") ;
					
					mRecorder = new MediaRecorder();  
		            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);  
		            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);  
		            mRecorder.setOutputFile(pathName +"/"+ fileName);  
		            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);  
		            try {
		            	mRecorder.prepare();  
		            } catch (IOException e) {
		            	btnAddUnforgetAudio.setText("录音失败");
		            	Log.e("LOG_TAG", "prepare() failed");  
		            }  
		            mRecorder.start();  
				}
				
				return false;
			}
		});
		
		
		btnAddUnforgetAudio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				MediaPlayer mPlayer = new MediaPlayer();  
	            try{  
	                mPlayer.setDataSource(pathName +"/"+ fileName);  
	                mPlayer.prepare();  
	                mPlayer.start();  
	            }catch(IOException e){  
	                Log.e("LOG_TAG","播放失败");  
	            } 
				
			}
		});
		
		
		btnAddUnforgetCancel = (Button) findViewById(R.id.btnAddUnforgetCancel);
		btnAddUnforgetCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				FileOper fileOper = new FileOper();
				File file = new File(pathName +"/"+ fileName);
				fileOper.deleteFile(file);
				finish();
			}
		});
		
		btnAddUnforgetOK = (Button) findViewById(R.id.btnAddUnforgetOK);
		btnAddUnforgetOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				String unforgetName = edUnforgetName.getText().toString();
				if(unforgetName.equals("")){
					
					Toast.makeText(getApplicationContext(), "备忘名不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				
				Calendar c_set = Calendar.getInstance();//获取日期对象    
				Calendar c_cur = Calendar.getInstance();//获取日期对象  
				
				String strYear = tvUnforgetYear.getText().toString().substring(0, 4);
				int intYear;
				intYear=Integer.parseInt(strYear);
				
				
				String strMonth = tvUnforgetMonth.getText().toString().substring(0, 2);
				int intMonth;
				intMonth=Integer.parseInt(strMonth);

				
				String strDay = tvUnforgetDay.getText().toString().substring(0, 2);
				int intDay;
				intDay=Integer.parseInt(strDay);
				
				String strHour = tvUnforgetHour.getText().toString().substring(0, 2);
				int intHour;
				intHour=Integer.parseInt(strHour);
				
				String strMinute = tvUnforgetMinute.getText().toString().substring(0, 2);
				int intMinute;
				intMinute=Integer.parseInt(strMinute);
				
				c_set.set(Calendar.YEAR, intYear);
				c_set.set(Calendar.MONTH, intMonth-1);
				c_set.set(Calendar.DAY_OF_MONTH, intDay);
				c_set.set(Calendar.HOUR_OF_DAY, intHour);        //设置闹钟小时数
				c_set.set(Calendar.MINUTE, intMinute);            //设置闹钟的分钟数
				c_set.set(Calendar.SECOND, 0);                //设置闹钟的秒数
				c_set.set(Calendar.MILLISECOND, 0);            //设置闹钟的毫秒数
				
                if(c_cur.getTimeInMillis()> c_set.getTimeInMillis()){  
                	Toast.makeText(getApplicationContext(), "备忘时间小于系统时间", Toast.LENGTH_SHORT).show();
    				return;
                } 
				
				Unforget modelUnforget = new Unforget(0,intYear, intMonth, intDay, intHour, intMinute, 0, unforgetName, fileName);
				bllUnforget.insert(modelUnforget);

                Intent intent = new Intent(AddUnforgetActivity.this, AlarmReceiver.class);    //创建Intent对象
                PendingIntent pi = PendingIntent.getBroadcast(AddUnforgetActivity.this, bllUnforget.getMaxId(), intent, 0);    //创建PendingIntent
                alarmManager.set(AlarmManager.RTC_WAKEUP, c_set.getTimeInMillis(), pi);
                //alarmManager.cancel(pi);
				setResult(1,getIntent());  
				finish();
				
			}
		});
		
		
		
		llUnforgetDate = (LinearLayout) findViewById(R.id.llUnforgetDate);
		llUnforgetDate.setOnClickListener(new OnClickListener() {
			
			Calendar calendar = Calendar.getInstance();
			@Override
			public void onClick(View arg0) {
				
					
					new DatePickerDialog(AddUnforgetActivity.this,new OnDateSetListener() {
						
						@Override
						public void onDateSet(DatePicker arg0, int year, int month, int dayOfMonth) {
							tvUnforgetYear.setText(String.valueOf(year)+"年");
							tvUnforgetMonth.setText(String.format("%02d",month+1)+"月");
							tvUnforgetDay.setText(String.format("%02d",dayOfMonth)+"日");
							
						}
					},calendar.getTime().getYear()+1900,calendar.getTime().getMonth(),calendar.getTime().getDate()).show();
				}
		});
		llUnforgetTime = (LinearLayout) findViewById(R.id.llUnforgetTime);
		llUnforgetTime.setOnClickListener(new OnClickListener() {
			
			Calendar calendar = Calendar.getInstance();
			@Override
			public void onClick(View arg0) {
				
					
					new TimePickerDialog(AddUnforgetActivity.this, new OnTimeSetListener() {
						
						@Override
						public void onTimeSet(TimePicker arg0, int hour, int minute) {
							tvUnforgetHour.setText(String.format("%02d",hour)+"时");
							tvUnforgetMinute.setText(String.format("%02d",minute)+"分");
							
						}
					}, calendar.getTime().getHours(),calendar.getTime().getMinutes()/*calendar.get(4), calendar.get(5)*/, true).show();
			}
		});
	}
}
