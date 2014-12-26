package com.example.shenghuobang.Unforget;

import com.example.shenghuobang.CommonValue;
import com.example.shenghuobang.LoginActivity;
import com.example.shenghuobang.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Vibrator;


public class AlarmActivity extends Activity {
	//private static final ;
	private Vibrator vibrator;
	
	private SharedPreferences mySharedPreferences; 
	
	private Bundle bundle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        bundle = getIntent().getExtras();
        
        mySharedPreferences= getSharedPreferences(CommonValue.AppName, Activity.MODE_PRIVATE); 
		
        if(mySharedPreferences.getBoolean(CommonValue.APPLY_ENABLE_VIBRATE, false))
        {
	        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);  
	        long [] pattern = {100,400,100,400};   // ֹͣ ���� ֹͣ ����   
	        vibrator.vibrate(pattern,-1);           //�ظ����������pattern ���ֻ����һ�Σ�index��Ϊ-1   
        }
        AddNotification();
        finish();
        
    }
    
    public void AddNotification(){ 

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE); 
        
        Notification n = new Notification(); 
        int icon = R.drawable.ic_launcher; 
        
        int ID = bundle.getInt("id", 0);
        String tickerText =bundle.getString("unforget"); 

        long when = System.currentTimeMillis(); 

        n.icon = icon; 
        n.tickerText = tickerText; 
        n.when = when; 
        
        n.flags|=Notification.FLAG_AUTO_CANCEL; //�Զ���ֹ 
         
        Intent it = new Intent(this,LoginActivity.class);  
        /*********************
         *���PendingIntent  
         *FLAG_CANCEL_CURRENT:
         *      �����ǰϵͳ���Ѿ�����һ����ͬ��PendingIntent����
         *      ��ô�ͽ��Ƚ����е�PendingIntentȡ����Ȼ����������һ��PendingIntent���� 
         *FLAG_NO_CREATE:
         *      �����ǰϵͳ�в�������ͬ��PendingIntent����
         *      ϵͳ�����ᴴ����PendingIntent�������ֱ�ӷ���null�� 
         *FLAG_ONE_SHOT:
         *      ��PendingIntentֻ����һ�Σ�
         *      �����PendingIntent�����Ѿ�������һ�Σ�
         *      ��ô�´��ٻ�ȡ��PendingIntent�����ٴ���ʱ��
         *      ϵͳ���᷵��һ��SendIntentException����ʹ�������־��ʱ��һ��Ҫע��Ŷ�� 
         *FLAG_UPDATE_CURRENT:
         *      ���ϵͳ���Ѵ��ڸ�PendingIntent����
         *      ��ôϵͳ��������PendingIntent����
         *      ���ǻ�ʹ���µ�Intent������֮ǰPendingIntent�е�Intent�������ݣ�
         *      �������Intent�е�Extras������ǳ����ã�
         *      ����֮ǰ�ᵽ�ģ�������Ҫ��ÿ�θ���֮�����Intent�е�Extras���ݣ�
         *      �ﵽ�ڲ�ͬʱ�����ݸ�MainActivity��ͬ�Ĳ�����ʵ�ֲ�ͬ��Ч���� 
         *********************/ 
          
        PendingIntent pi = PendingIntent.getActivity(this, 0, it, PendingIntent.FLAG_UPDATE_CURRENT); 
        n.setLatestEventInfo(this,"�׼ǰ�", n.tickerText, pi); 
        nm.notify(ID,n); 
    } 
}
