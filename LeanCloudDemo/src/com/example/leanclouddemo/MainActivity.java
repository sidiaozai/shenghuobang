package com.example.leanclouddemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;

public class MainActivity extends Activity {
	
	private Button button1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		AVOSCloud.initialize(this, "qcrjy7s8b5vforciw86pptxnhypnyt5n67yyrwnty2f2vcj4", "udpajg0bbynronjwfzqcvyfk4kfc6ccjbluzamo9u6gdmdhb");
		
		
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AVObject testObject = new AVObject("TestObject");
				testObject.put("foo1", "bar1");
				testObject.put("foo2", "bar1");
				testObject.put("foo3", "bar1");
				testObject.put("foo4", "bar1");
				testObject.saveInBackground();
				
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