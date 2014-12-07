package com.example.testclock;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	private Button button1;
	private TextView textView1;
	private TextView textView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		//final Calendar c_set = Calendar.getInstance();
		final Date date = new Date();
		
		//c_set.clear();
		//c_set.set(2014, 11+1, 2, 1, 1, 0);
		
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override 
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
//				c_set.set(Calendar.YEAR,2013);
//				c_set.set(Calendar.MONTH,12);
//				c_set.set(Calendar.DAY_OF_MONTH,12);
//				c_set.set(Calendar.HOUR_OF_DAY,12);
//				c_set.set(Calendar.MINUTE,12);
//				c_set.set(Calendar.SECOND,12);
				date.setYear(2014);
				date.setMonth(11);
				date.setDate(10);
				date.setHours(12);
				date.setMinutes(12);
				date.setSeconds(0);
				
				textView1.setText(""+date.getYear()+"年"+date.getMonth()+"月"+date.getDate()+"日");
				textView2.setText(""+date.getHours()+"时"+date.getMinutes()+"分"+date.getSeconds()+"秒");
			}
		});
		
		
	}


}
