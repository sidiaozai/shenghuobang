package com.example.shenghuobang.Charge;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;





import com.example.shenghuobang.R;
import com.example.shenghuobang.Unforget.AddUnforgetActivity;

import sqliteDataBase.Model.Charge;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddChargeActivity extends ListActivity  {
	
	private Button btnRetrunCharge;
	private Button btnSave;
	private TextView tVAddChargeTime1;
	private EditText etAddChargeSum;
	private RadioButton radioButtonType;
	private EditText etAddChargeDes;
	private sqliteDataBase.Model.Charge modelCharge ;
	private sqliteDataBase.Bll.Charge bllCharge;
	
	//private AddChargeAdapter adapter;
	private ListChargeAdapter adapter;
	
	private Intent intent;   
	private int intYear ;
	private String strYear;
	private int intMonth ;
	private String strMonth ;
	private int intData;
	private String strData ;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_charge);
		
		intent = getIntent();
		
		bllCharge = new sqliteDataBase.Bll.Charge(AddChargeActivity.this);

		tVAddChargeTime1 = (TextView) findViewById(R.id.tVAddChargeTime1);
		boolean isUpdate = intent.getBooleanExtra("isUpdate", false);
		if(isUpdate == false){
			Date curDate = new Date(System.currentTimeMillis());
			
			intYear = curDate.getYear()+1900;
			strYear = String.format("%04d",intYear)+"年";
			intMonth = curDate.getMonth()+1;
			strMonth = String.format("%02d",intMonth)+"月";
			intData = curDate.getDate();
			strData = String.format("%02d",intData)+"日";
			 
		}else{
			
			intYear = intent.getIntExtra("year", 2014);
			strYear = String.format("%04d",intYear)+"年";
			intMonth = intent.getIntExtra("month", 12);
			strMonth = String.format("%02d",intMonth)+"月";
			intData = intent.getIntExtra("day", 1);
			strData = String.format("%02d",intData)+"日";
		}
		tVAddChargeTime1.setText(strYear+strMonth+strData);
		
		tVAddChargeTime1.setOnClickListener(new OnClickListener() {
			
			Calendar calendar = Calendar.getInstance();
			@Override
			public void onClick(View arg0) {
				
				String time = tVAddChargeTime1.getText().toString();

				String strYear = time.substring(0, 4);
				String strMonth = time.substring(5, 7);
				String strData = time.substring(8, 10);
				
				int intYear = Integer.parseInt(strYear);
				int intMonth = Integer.parseInt(strMonth);
				int intData = Integer.parseInt(strData);
					
					new DatePickerDialog(AddChargeActivity.this,new OnDateSetListener() {
						
						@Override
						public void onDateSet(DatePicker arg0, int year, int month, int dayOfMonth) {

							String strYear = String.valueOf(year)+"年";
							String strMonth = String.format("%02d",month+1)+"月";
							String strData = String.format("%02d",dayOfMonth)+"日";
							
							tVAddChargeTime1.setText(strYear+strMonth+strData);
							
							setListViewData();
						}
					},intYear,intMonth-1,intData).show();
				}
		});
		
		etAddChargeSum = (EditText) findViewById(R.id.etAddChargeSum);
		
		radioButtonType = (RadioButton) findViewById(R.id.radioButton1);
		
		etAddChargeDes = (EditText) findViewById(R.id.etAddChargeDes);
			
		btnSave = (Button) findViewById(R.id.btnSave);
		
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				String time = tVAddChargeTime1.getText().toString();

				String strYear = time.substring(0, 4);
				String strMonth = time.substring(5, 7);
				String strData = time.substring(8, 10);
				
				int intYear = Integer.parseInt(strYear);
				int intMonth = Integer.parseInt(strMonth);
				int intData = Integer.parseInt(strData);
				
				String srtSum = etAddChargeSum.getText().toString();
				if(srtSum.length()==0){
					Toast.makeText(AddChargeActivity.this, "金额不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if(srtSum.length()>8){
					Toast.makeText(AddChargeActivity.this, "输入金额不能超过100000000元", Toast.LENGTH_SHORT).show();
					return;
				}
				
				int sum = Integer.valueOf(srtSum);
				
				int type = radioButtonType.isChecked()==true?1:0;
				String des = etAddChargeDes.getText().toString();
				if(des.length()==0){
					Toast.makeText(getApplicationContext(), "用途不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				modelCharge = new Charge(intYear, intMonth, intData, sum, type, des);
				
				bllCharge.insert(modelCharge);
				
				setListViewData();
				
				etAddChargeDes.setText("");
				etAddChargeSum.setText("");
				etAddChargeSum.setFocusable(true);
				etAddChargeSum.requestFocus();
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
		adapter = new ListChargeAdapter(this);
		adapter.setListChargeAdapterListening(new ListChargeAdapterListening() {
			
			@Override
			public void deleteItem(int position) {
				sqliteDataBase.Model.Charge modelCharge = (Charge) adapter.getItem(position);
				sqliteDataBase.Bll.Charge bllCharge = new sqliteDataBase.Bll.Charge(getApplicationContext());
				bllCharge.delete(modelCharge.getId());
			}
		});
	}
	
	private void setListViewData(){
		

		//Date curDate = new Date(System.currentTimeMillis());
		
		
		String time = tVAddChargeTime1.getText().toString();

		String strYear = time.substring(0, 4);
		String strMonth = time.substring(5, 7);
		String strData = time.substring(8, 10);
		
		int intYear = Integer.parseInt(strYear);
		int intMonth = Integer.parseInt(strMonth);
		int intData = Integer.parseInt(strData);
		
		Cursor cursor = bllCharge.queryByDay(intYear,intMonth,intData);
			
		if(cursor.getCount()==0){
			setListAdapter(null);
		}else{
			adapter.setData(getData(cursor));
			setListAdapter(adapter);
		}
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			setResult(1,intent);
		}	
		return super.onKeyDown(keyCode,event); 	
	}
	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		setListViewData();
		super.onResume();
	}
}
