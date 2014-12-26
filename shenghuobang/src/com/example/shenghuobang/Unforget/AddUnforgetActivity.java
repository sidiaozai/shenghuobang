package com.example.shenghuobang.Unforget;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;




import com.example.shenghuobang.CommonValue;
import com.example.shenghuobang.FileOper;
import com.example.shenghuobang.MediaPlayerPlay;
import com.example.shenghuobang.MediaRecorderPlay;
import com.example.shenghuobang.R;





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
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
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


	private long startTime;
	private static final int MIN_INTERVAL_TIME = 2000;// 2s
	
	private sqliteDataBase.Bll.Unforget bllUnforget;

	private MediaRecorderPlay mediaRecordPlay;

	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		setContentView(R.layout.activity_add_unforget);

		bllUnforget = new sqliteDataBase.Bll.Unforget(this);
		
		long currentTime = System.currentTimeMillis();
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy��-MM��dd��-HHʱmm��ss��");
		Date date = new Date(currentTime);

		tvUnforgetYear = (TextView) findViewById(R.id.tvUnforgetYear);
		int year = date.getYear()+1900;
		tvUnforgetYear.setText(String.valueOf(year)+"��");
		
		
		tvUnforgetMonth = (TextView) findViewById(R.id.tvUnforgetMonth);
		int month = date.getMonth();
		tvUnforgetMonth.setText(String.format("%02d",month+1)+"��");
		
		tvUnforgetDay = (TextView) findViewById(R.id.tvUnforgetDay);
		int dayOfMonth = date.getDate();
		tvUnforgetDay.setText(String.format("%02d",dayOfMonth+1)+"��");
		
		tvUnforgetHour = (TextView) findViewById(R.id.tvUnforgetHour);
		int hour = date.getHours();
		tvUnforgetHour.setText(String.format("%02d",0)+"ʱ");
		
		tvUnforgetMinute = (TextView) findViewById(R.id.tvUnforgetMinute);
		int minute = date.getMinutes();
		tvUnforgetMinute.setText(String.format("%02d",0)+"��");
		
		edUnforgetName = (EditText) findViewById(R.id.etForgetName);
		
		btnAddUnforgetAudio = (Button)findViewById(R.id.btnAddUnforgetAudio);
		
		btnAddUnforgetAudio.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				
				if(btnAddUnforgetAudio.getText().toString().equals("����"))
					return false;
				
				if(event.getAction()== MotionEvent.ACTION_DOWN){
					
					if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			            Toast.makeText(AddUnforgetActivity.this, "SD�������ڣ������SD��", Toast.LENGTH_SHORT).show();
			            return true;
			        }
					
					btnAddUnforgetAudio.setText("����¼��");
					startTime = System.currentTimeMillis();
					pathName = Environment.getExternalStorageDirectory().getAbsolutePath();
					fileName = String.valueOf(""+startTime+CommonValue.SoundFileType) ;
					mediaRecordPlay = new MediaRecorderPlay(pathName +File.separator+ fileName); 
					mediaRecordPlay.start();
					

				}else if (event.getAction()== MotionEvent.ACTION_UP){//�жϰ�ť�ͷű��ͷ� 
				
					long intervalTime = System.currentTimeMillis() - startTime;
					if (intervalTime < MIN_INTERVAL_TIME) {
						Toast.makeText(getApplicationContext(), "¼��ʱ��̫�̣�¼����ȡ��", Toast.LENGTH_SHORT).show();
						mediaRecordPlay.stop();
						File file = new File(pathName +File.separator+ fileName);
						file.delete();
						btnAddUnforgetAudio.setText("¼��");
					}else{
						mediaRecordPlay.stop();
						btnAddUnforgetAudio.setText("����");
					}
				}else if (event.getAction()== MotionEvent.ACTION_CANCEL){
					Toast.makeText(getApplicationContext(), "¼����ȡ��", Toast.LENGTH_SHORT).show();
					
					mediaRecordPlay.stop();
					
					File file = new File(pathName +File.separator+ fileName);
					file.delete();
					
					btnAddUnforgetAudio.setText("¼��");
				}
				
				return true;
			}
		});
		
		btnAddUnforgetAudio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				File file = new File(pathName +File.separator+ fileName);
				if(!file.exists()){
            		Log.e("LOG_TAG","�ļ�������");  
            		return;
            	}
				
				MediaPlayerPlay mediaPlayPlay = new MediaPlayerPlay(pathName +File.separator+ fileName);
				mediaPlayPlay.Start();
				btnAddUnforgetAudio.setText("���ڲ���");
				
				mediaPlayPlay.SetOnCompletionListener(new OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer arg0) {
						btnAddUnforgetAudio.setText("����");
					}
				});
			}
		});
		
		
		btnAddUnforgetCancel = (Button) findViewById(R.id.btnAddUnforgetCancel);
		btnAddUnforgetCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				FileOper fileOper = new FileOper();
				File file = new File(pathName+File.separator+fileName);
				if(file.exists()){
					fileOper.deleteFile(file);
				}
				finish();
			}
		});
		
		btnAddUnforgetOK = (Button) findViewById(R.id.btnAddUnforgetOK);
		btnAddUnforgetOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				String unforgetName = edUnforgetName.getText().toString();
				if(unforgetName.equals("")){
					
					Toast.makeText(getApplicationContext(), "����������Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				
				
				Calendar c_cur = Calendar.getInstance();//��ȡ���ڶ���  
				Calendar c_set = getDateTimeFromTextView();//��ȡ���ڶ���    
				
				
                if(c_cur.getTimeInMillis()> c_set.getTimeInMillis()){  
                	Toast.makeText(getApplicationContext(), "����ʱ��С��ϵͳʱ��", Toast.LENGTH_SHORT).show();
    				return;
                } 
				
				Unforget modelUnforget = new Unforget(0,c_set.get(Calendar.YEAR), c_set.get(Calendar.MONTH)+1, c_set.get(Calendar.DAY_OF_MONTH),
						c_set.get(Calendar.HOUR_OF_DAY), c_set.get(Calendar.MINUTE), c_set.get(Calendar.SECOND), unforgetName, fileName);
				bllUnforget.insert(modelUnforget);
                
				Intent intent = new Intent(AddUnforgetActivity.this, AlarmReceiver.class);    //����Intent����
				
				Bundle budle = new Bundle();
				budle.putString("unforget", modelUnforget.getName());
				budle.putInt("id", bllUnforget.getMaxId());
				
                intent.putExtras(budle);
                PendingIntent pi = PendingIntent.getBroadcast(AddUnforgetActivity.this, bllUnforget.getMaxId(), intent, 0);    //����PendingIntent
                
                AlarmManager alarmManager=alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, c_set.getTimeInMillis(), pi);
                
                //alarmManager.cancel(pi); 
				finish();
				
			}
		});
		
		
		
		llUnforgetDate = (LinearLayout) findViewById(R.id.llUnforgetDate);
		llUnforgetDate.setOnClickListener(new OnClickListener() {
			
			
			
			@Override
			public void onClick(View arg0) {
				
				String strYear = tvUnforgetYear.getText().toString().substring(0, 4);
				int intYear = Integer.parseInt(strYear);
				
				
				String strMonth = tvUnforgetMonth.getText().toString().substring(0, 2);
				int intMonth = Integer.parseInt(strMonth);

				
				String strDay = tvUnforgetDay.getText().toString().substring(0, 2);
				int intDay = Integer.parseInt(strDay);
				
				new DatePickerDialog(AddUnforgetActivity.this,new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker arg0, int year, int month, int dayOfMonth) {
						tvUnforgetYear.setText(String.valueOf(year)+"��");
						tvUnforgetMonth.setText(String.format("%02d",month+1)+"��");
						tvUnforgetDay.setText(String.format("%02d",dayOfMonth)+"��");
						
					}
				},intYear,intMonth-1,intDay).show();
			}
		});
		llUnforgetTime = (LinearLayout) findViewById(R.id.llUnforgetTime);
		llUnforgetTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String strHour = tvUnforgetHour.getText().toString().substring(0, 2);
				int intHour = Integer.parseInt(strHour);
				
				String strMinute = tvUnforgetMinute.getText().toString().substring(0, 2);
				int intMinute = Integer.parseInt(strMinute);
					
				new TimePickerDialog(AddUnforgetActivity.this, new OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker arg0, int hour, int minute) {
						tvUnforgetHour.setText(String.format("%02d",hour)+"ʱ");
						tvUnforgetMinute.setText(String.format("%02d",minute)+"��");
						
					}
				}, intHour,intMinute, true).show();
				
			}
		});
	}
	private Calendar getDateTimeFromTextView(){
		
		Calendar c_set = Calendar.getInstance();
				
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
		c_set.set(Calendar.HOUR_OF_DAY, intHour);        //��������Сʱ��
		c_set.set(Calendar.MINUTE, intMinute);            //�������ӵķ�����
		c_set.set(Calendar.SECOND, 0);                //�������ӵ�����
		c_set.set(Calendar.MILLISECOND, 0);            //�������ӵĺ�����
		
		return c_set;
		
	}
}
