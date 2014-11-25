package com.example.shenghuobang.Unforget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import sqliteDataBase.Model.Unforget;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class UnforgetActivity extends Activity {
	
	private Button btnAddUnForget;
	private GridView gridViewUnforget;
	private sqliteDataBase.Bll.Unforget bllUnforget;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unforget);
		
		bllUnforget = new sqliteDataBase.Bll.Unforget(this);
		
		btnAddUnForget = (Button) findViewById(R.id.btnAddUnForget);
		
		btnAddUnForget.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
                intent.setClass(UnforgetActivity.this, AddUnforgetActivity.class);
                startActivityForResult(intent, 1);
			}
		});
		
		gridViewUnforget = (GridView) findViewById(R.id.gridViewUnforget);
		
		
		gridViewUnforget.setAdapter(new UnforgetAdapter(this,getData())); 
        
		gridViewUnforget.setOnItemClickListener(new OnItemClickListener() 
        { 
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
            { 
                Toast.makeText(UnforgetActivity.this, "pic" + position, Toast.LENGTH_SHORT).show(); 
            } 
        }); 
		
		
	}
	DatePickerDialog.OnDateSetListener dateListener =  new DatePickerDialog.OnDateSetListener() { 
		@Override
		public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
			
			
		}     
	};

	private List<Unforget> getData() {
		
		List<Unforget> listUnforget = new ArrayList<Unforget>();
		
		Cursor cursor = bllUnforget.query();
		if(cursor.getCount()!=0)
		{
			while(cursor.moveToNext()){
				int year,month,day;
				int hour,minute,second;
				String name;
				String soundFileName;
				
				
	    		int yearIndex = cursor.getColumnIndex("year");
	    		int monthIndex = cursor.getColumnIndex("month");
	    		int dayIndex = cursor.getColumnIndex("day");
	    		int hourIndex = cursor.getColumnIndex("hour");
	    		int minuteIndex = cursor.getColumnIndex("minute");
	    		int secondIndex = cursor.getColumnIndex("second");
	    		int nameIndex = cursor.getColumnIndex("name");
	    		int soundFileNameIndex = cursor.getColumnIndex("soundFileName");
	    		
	    		year = cursor.getInt(yearIndex);
	    		month = cursor.getInt(monthIndex);
	    		day = cursor.getInt(dayIndex);
	    		hour = cursor.getInt(hourIndex);
	    		minute = cursor.getInt(minuteIndex);
	    		second = cursor.getInt(secondIndex);
	    		name = cursor.getString(nameIndex);
	    		soundFileName = cursor.getString(soundFileNameIndex);
	    		
	    		Unforget modelUnforget = new Unforget(year, month, day, hour, minute, second, name, soundFileName);
	    		listUnforget.add(modelUnforget);
			}
		}
		else{
			Unforget modelUnforget = new Unforget(2014, 11, 24, 18, 52, 00, "��������", "NULL");
			listUnforget.add(modelUnforget);
		}
		return listUnforget;
	}
	
	@Override 
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		super.onActivityResult(requestCode,resultCode,data);   
		if(resultCode==1){  
			gridViewUnforget.setAdapter(new UnforgetAdapter(this,getData()));
		}
	}  
	
	
}