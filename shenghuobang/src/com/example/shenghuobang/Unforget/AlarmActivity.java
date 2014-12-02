package com.example.shenghuobang.Unforget;

import com.example.shenghuobang.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;


public class AlarmActivity extends Activity {
	private static final int ID = 1213; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        //��ʾ�Ի���
//        new AlertDialog.Builder(AlarmActivity.this).
//            setTitle("����").//���ñ���
//            setMessage("ʱ�䵽�ˣ�").//��������
//            setPositiveButton("֪����", new OnClickListener(){//���ð�ť
//                public void onClick(DialogInterface dialog, int which) {
//                    AlarmActivity.this.finish();//�ر�Activity
//                }
//            }).create().show();
        AddNotification();
        
    }
    
    public void AddNotification(){ 
        //����֪ͨ������������ 
        //���NotificationManagerʵ�� 
        String service = NOTIFICATION_SERVICE; 
        NotificationManager nm = (NotificationManager)getSystemService(service); 
        //ʵ����Notification 
        Notification n = new Notification(); 
        //������ʾͼ�� 
        int icon = R.drawable.ic_launcher; 
        //������ʾ��Ϣ 
        String tickerText ="�ҵĳ���"; 
        //��ʾʱ�� 
        long when = System.currentTimeMillis(); 
          
        n.icon = icon; 
        n.tickerText = tickerText; 
        n.when = when; 
        //��ʾ�ڡ����ڽ����С� 
        //  n.flags = Notification.FLAG_ONGOING_EVENT; 
        n.flags|=Notification.FLAG_AUTO_CANCEL; //�Զ���ֹ 
        //ʵ����Intent 
        Intent it = new Intent(this,AlarmActivity.class);  
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
         
        //�����¼���Ϣ����ʾ������������ 
        n.setLatestEventInfo(this,"�ҵ�����", "�ҵ������������С���", pi); 
      
        nm.notify(ID,n); 
    } 
    
    

}