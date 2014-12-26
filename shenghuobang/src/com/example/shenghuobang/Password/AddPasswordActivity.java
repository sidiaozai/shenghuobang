package com.example.shenghuobang.Password;

import java.io.File;
import java.io.IOException;

import sqliteDataBase.Bll.Password;

import com.example.shenghuobang.CommonValue;
import com.example.shenghuobang.FileOper;
import com.example.shenghuobang.MediaPlayerPlay;
import com.example.shenghuobang.MediaRecorderPlay;
import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPasswordActivity extends Activity {
	
	private Button btnAddPasswordCancel;
	private Button btnAddPasswordOK;
	private Button btnIsPassword;
	
	private EditText etPasswordName;
	private EditText etPassword;
	private Button btnAddPasswordAudio;
	
	private String pathName = null; 
	private String fileName = "NULL";
	
	
	private sqliteDataBase.Model.Password modelPassword ;
	private sqliteDataBase.Bll.Password bllPassword;
	
	private long startTime;
	private static final int MIN_INTERVAL_TIME = 2000;// 2s
	
	private MediaRecorderPlay mediaRecordPlay;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		setContentView(R.layout.activity_add_password);
		
		bllPassword = new sqliteDataBase.Bll.Password(this);
		
		
		
		etPasswordName = (EditText) findViewById(R.id.etPasswordName);
		etPassword = (EditText) findViewById(R.id.etPassword);
	
		btnAddPasswordCancel = (Button) findViewById(R.id.btnAddPasswordCancel);
		btnAddPasswordCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				FileOper fileOper = new FileOper();
				File file = new File(pathName +"/"+ fileName);
				fileOper.deleteFile(file);
				finish();
				
				
			}
		});
		btnAddPasswordOK = (Button) findViewById(R.id.btnAddPasswordOK);
		btnAddPasswordOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String passwordName = etPasswordName.getText().toString();
				
				if(passwordName.length()==0){
					Toast.makeText(getApplicationContext(), "����������Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				
				
				String password = etPassword.getText().toString();
				String soundFileName = fileName;
				if(password.equals("")&&soundFileName.equals("NULL")){
					Toast.makeText(getApplicationContext(), "�������������ͬʱΪ��", Toast.LENGTH_SHORT).show();
					return;
				}
				
				CommonValue commonValue = new CommonValue(getApplicationContext());
				
			    String 	encryptPassword = commonValue.Encrypt(password);
				
				modelPassword = new sqliteDataBase.Model.Password(passwordName, encryptPassword, soundFileName);
				bllPassword.insert(modelPassword);
				
				setResult(1,getIntent());  
				finish();
				
				
			}
		});
		btnIsPassword =  (Button) findViewById(R.id.btnIsPassword);
		btnIsPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				TransformationMethod passwordMethod = PasswordTransformationMethod.getInstance();
				TransformationMethod HideReturnsMethod = HideReturnsTransformationMethod.getInstance();
				TransformationMethod state = etPassword.getTransformationMethod();
				if(state == passwordMethod){
					etPassword.setTransformationMethod(HideReturnsMethod);
					
				}else{
					etPassword.setTransformationMethod(passwordMethod);
				}
				etPassword.setSelection(etPassword.length());
			}
		});
//		
		btnAddPasswordAudio = (Button) findViewById(R.id.btnAddPasswordAudio);
		btnAddPasswordAudio.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				
				if(btnAddPasswordAudio.getText().toString().equals("����"))
					return false;
				
				if(event.getAction()== MotionEvent.ACTION_DOWN){
					
					if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			            Toast.makeText(AddPasswordActivity.this, "SD�������ڣ������SD��", Toast.LENGTH_SHORT).show();
			            return true;
			        }
					pathName = Environment.getExternalStorageDirectory().getAbsolutePath(); 
					
					
					btnAddPasswordAudio.setText("����¼��");
					
					startTime = System.currentTimeMillis();
					fileName = String.valueOf(""+startTime+CommonValue.SoundFileType) ;
					
					mediaRecordPlay = new MediaRecorderPlay(pathName +File.separator+ fileName); 
					mediaRecordPlay.start();  
				}else if (event.getAction()== MotionEvent.ACTION_UP){//�жϰ�ť�ͷű��ͷ� 
				
					long intervalTime = System.currentTimeMillis() - startTime;
					if (intervalTime < MIN_INTERVAL_TIME) {
						Toast.makeText(getApplicationContext(), "¼��ʱ��̫�̣�¼����ȡ��", Toast.LENGTH_SHORT).show();
						
						mediaRecordPlay.stop();
						
						File file = new File(pathName +"/"+ fileName);
						file.delete();
						btnAddPasswordAudio.setText("¼��");
					}else{
						mediaRecordPlay.stop();
						
						btnAddPasswordAudio.setText("����");
					}
					return true;
				}else if (event.getAction()== MotionEvent.ACTION_CANCEL){
					mediaRecordPlay.stop();
					
					Toast.makeText(getApplicationContext(), "¼����ȡ��", Toast.LENGTH_SHORT).show();
					File file = new File(pathName +File.separator+ fileName);
					file.delete();
					btnAddPasswordAudio.setText("¼��");
				}

				return true;
			}
		});
		
		btnAddPasswordAudio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				File file = new File(pathName +File.separator+ fileName);
				if(!file.exists()){
            		Log.e("LOG_TAG","�ļ�������");  
            		return;
            	}
				
				MediaPlayerPlay mediaPlayPlay = new MediaPlayerPlay(pathName +File.separator+ fileName);
				mediaPlayPlay.Start();
				btnAddPasswordAudio.setText("���ڲ���");
				
				mediaPlayPlay.SetOnCompletionListener(new OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer arg0) {
						btnAddPasswordAudio.setText("����");
					}
				}); 
				
			}
		});
		
		
	}

}
