package com.example.shenghuobang.Charge;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;

import sqliteDataBase.Model.Charge;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddChargeActivity extends ListActivity {
	
	private Button btnRetrunCharge;
	private Button btnSave;
	private TextView tVAddChargeTime1;
	private EditText etAddChargeSum;
	private EditText etSum;
	private RadioButton radioButtonType;
	private EditText etAddChargeDes;
	private sqliteDataBase.Model.Charge modelCharge ;
	private sqliteDataBase.Bll.Charge bllCharge;
	
	private AddChargeAdapter adapter;
	private ArrayAdapter arrayAdapter;
	
	private Intent intent;   
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_charge);
		
		intent = getIntent();
		
		bllCharge = new sqliteDataBase.Bll.Charge(AddChargeActivity.this);
		
		Date curDate = new Date(System.currentTimeMillis());
		
		final int intYear = curDate.getYear()+1900;
		String strYear = String.format("%04d",intYear)+"��";
		final int intMonth = curDate.getMonth()+1;
		String strMonth = String.format("%02d",intMonth)+"��";
		final int intData = curDate.getDate();
		String strData = String.format("%02d",intData)+"��";
		
	
		tVAddChargeTime1 = (TextView) findViewById(R.id.tVAddChargeTime1);
		tVAddChargeTime1.setText(strYear+strMonth+strData);
		
		etAddChargeSum = (EditText) findViewById(R.id.etAddChargeSum);
		
		radioButtonType = (RadioButton) findViewById(R.id.radioButton1);
		
		etAddChargeDes = (EditText) findViewById(R.id.etAddChargeDes);
			
		btnSave = (Button) findViewById(R.id.btnSave);
		
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				//Date date = new Date(intYear, intMonth, intData);
				
				//String time = formatter.format(curDate);
				String srtSum = etAddChargeSum.getText().toString();
				if(srtSum.length()==0){
					Toast.makeText(AddChargeActivity.this, "����Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				
				int sum = Integer.valueOf(srtSum);
				
				int type = radioButtonType.isChecked()==true?1:0;
				String des = etAddChargeDes.getText().toString();
				if(des.length()==0){
					Toast.makeText(getApplicationContext(), "��;����Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				modelCharge = new Charge(intYear, intMonth, intData, sum, type, des);
				
				bllCharge.insert(modelCharge);
				
				setListViewData();
				
			}
		});
		
		btnRetrunCharge = (Button) findViewById(R.id.btnRetrunCharge);
		btnRetrunCharge.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				setResult(1,intent);   
				finish();
			}
		});
		
		setListViewData();
	}
	
	private void setListViewData(){
		

		Date curDate = new Date(System.currentTimeMillis());
		
		int intYear = curDate.getYear()+1900;
		int intMonth = curDate.getMonth()+1;
		int intData = curDate.getDate();
		
		Cursor cursor = bllCharge.queryByDay(intYear,intMonth,intData);
			
		if(cursor.getCount()==0){
			arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getNullData());
			setListAdapter(arrayAdapter);
		}
		else{
			adapter = new AddChargeAdapter(this, getData(cursor));
			setListAdapter(adapter);
		}
	}
	private void updateListViewData(){
		setListViewData();
	}
	private List<Charge> getData(Cursor cursor) {
		
		List<Charge> list = new ArrayList<Charge>();
		
    	while(cursor.moveToNext()){
    		
    		int idIndex = cursor.getColumnIndex("id");
    		
    		//int timeIndex = cursor.getColumnIndex("time");
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

    		list.add(new Charge(id,year, month, day, sum, type, des));
    	}
       
        cursor.close();
		return list;
	}
	private List<String> getNullData(){
		List<String> list = new ArrayList<String>();
		list.add("û�м�¼");
		return list;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			setResult(1,intent);
		}	
		return super.onKeyDown(keyCode,event); 	
	}
}