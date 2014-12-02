package com.example.shenghuobang.Charge;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.shenghuobang.MonPickerDialog;
import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;
import com.example.shenghuobang.Unforget.UnforgetActivity;

import sqliteDataBase.Model.Charge;
import sqliteDataBase.Model.ChargeStatistic;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ChargeActivity extends ListActivity {
	
	private ImageView imageAddCharge;
	private TextView tvInMonthSum;
	private TextView tvOutMonthSum;
	private TextView tvBalance;
	private TextView tvYear;
	private TextView tvMonth;
	
	private LinearLayout llTimeSelector;
	
	private ChargeAdapter adapter;
	
	private sqliteDataBase.Bll.Charge bllCharge;
	private List<ChargeStatistic> list;
 
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charge);

		bllCharge = new sqliteDataBase.Bll.Charge(ChargeActivity.this);
		
		llTimeSelector = (LinearLayout) findViewById(R.id.llTimeSelector);
		
		tvInMonthSum = (TextView) findViewById(R.id.tvInMonthSum);
		tvOutMonthSum = (TextView) findViewById(R.id.tvOutMonthSum);
		tvBalance = (TextView) findViewById(R.id.tvBalance);

		Date curDate = new Date(System.currentTimeMillis());//获取当前时间    
		
		imageAddCharge = (ImageView) findViewById(R.id.imageAddCharge);
		
		
		tvYear = (TextView) findViewById(R.id.tvYear);
		int year = curDate.getYear()+1900;
		
		tvYear.setText(String.valueOf(year)+"年");
		
		tvMonth = (TextView) findViewById(R.id.tvMonth);
		int month = curDate.getMonth()+1;
		tvMonth.setText(String.valueOf(month)+"月");

		imageAddCharge.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
                intent.setClass(ChargeActivity.this, AddChargeActivity.class);
                startActivityForResult(intent, 1);

			}
		});
		
		llTimeSelector.setOnClickListener(new OnClickListener() {
			
			Calendar calendar = Calendar.getInstance();
			@Override
			public void onClick(View arg0) {
				new MonPickerDialog(ChargeActivity.this,dateListener, calendar.get(1), calendar.get(2),1).show();
			}
		});
		setListViewData();
	}
	DatePickerDialog.OnDateSetListener dateListener =  new DatePickerDialog.OnDateSetListener() { 
		@Override
		public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
			
			tvYear.setText(String.valueOf(year)+"年");
			tvMonth.setText(String.format("%02d",month+1)+"月");
			setListViewData();
		}     
	}; 

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
	}

	private void setListViewData(){
		
		String strYear = tvYear.getText().toString().substring(0, 4);
		int intYear;
		intYear=Integer.parseInt(strYear);
		
		String strMonth = tvMonth.getText().toString().substring(0, 2);
		int intMonth;
		intMonth=Integer.parseInt(strMonth);
		
		Cursor cursor = bllCharge.queryByMonth(intYear,intMonth);
		
		if(cursor.getCount()==0){
//			arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getNullData());
//			setListAdapter(arrayAdapter);
			setListAdapter(null);
			tvInMonthSum.setText("收入");
			tvOutMonthSum.setText("支出");
			tvBalance.setText("结余");
		}
		else{
			adapter = new ChargeAdapter(this, getData(intYear,intMonth));
			setListAdapter(adapter);
		}
	}

	
	
	
	private List<ChargeStatistic> getData(int year,int month) {

		list = new ArrayList<ChargeStatistic>();
		int inMonthSum=0;
		int outMonthSum=0;
		for(int day=31;day>=1;day--){

			Cursor cursor = bllCharge.queryByDay(year, month, day);
			if(cursor.getCount()==0)
				continue;
			
			int inDaySum=0;
			int outDaySum=0;
			while(cursor.moveToNext()){
	    		int typeIndex = cursor.getColumnIndex("type");
	    		int sumIndex = cursor.getColumnIndex("sum");
	    		int sum = cursor.getInt(sumIndex);
	    		int type = cursor.getInt(typeIndex);
	    		
	    		if(type==0){
	    			inDaySum += sum;
	    		}
	    		else{
	    			outDaySum +=sum;
	    		}
	    	}
			inMonthSum += inDaySum;
			outMonthSum += outDaySum;
			list.add(new ChargeStatistic(year, month, day, inDaySum, outDaySum));
    		cursor.close();
		}
		tvInMonthSum.setText("收入："+ inMonthSum);
		
		tvOutMonthSum.setText("支出："+ outMonthSum);
		
		int monthBalance = inMonthSum-outMonthSum;
		
		tvBalance.setText("结余："+monthBalance);
		return list;
	}

	@Override   
    protected void onListItemClick(ListView l, View v, int position, long id) {  
		super.onListItemClick(l, v, position, id);  
		
		ChargeStatistic chargeStatistic = list.get(position);

		Intent intent = new Intent();
		intent.putExtra("year", chargeStatistic.getYear());
		intent.putExtra("month", chargeStatistic.getMonth());
		intent.putExtra("day", chargeStatistic.getDay());
        intent.setClass(ChargeActivity.this, UpdateChargeActivity.class);
        
        startActivityForResult(intent, 1);
    }  
	
	@Override 
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		super.onActivityResult(requestCode,resultCode,data);   
		if(resultCode==1){  
			setListViewData();
			//Toast.makeText(getApplicationContext(), "由添加界面返回", Toast.LENGTH_SHORT).show();
		}
	}  
}