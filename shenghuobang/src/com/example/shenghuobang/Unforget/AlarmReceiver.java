package com.example.shenghuobang.Unforget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
    	
//        Intent i=new Intent(context, AlarmActivity.class); 
    	Intent i = new Intent("com.example.shenghuobang.Unforget.AlarmService"); 
    	
    	Bundle bundle = intent.getExtras();
    	i.putExtras(bundle);
       // context.startActivity(i);
    	context.startService(i);
    }
     
}
