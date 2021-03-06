package com.example.shenghuobang.Unforget;

import java.util.List;

import com.example.shenghuobang.R;

import sqliteDataBase.Model.Unforget;
import android.R.color;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class UnforgetAdapter extends BaseAdapter{ 
        //上下文对象 
        private Context context; 
        //图片数组 
        private List<sqliteDataBase.Model.Unforget> array;

        public  UnforgetAdapter(Context context,List<sqliteDataBase.Model.Unforget> array){ 
            this.context = context; 
            this.array = array;
        } 
        public int getCount() { 
            return array.size();
        } 
 
        public Object getItem(int item) { 
            return item; 
        } 
 
        public long getItemId(int id) { 
            return id; 
        } 
         
        //创建View方法 
        public View getView(int position, View convertView, ViewGroup parent) { 
    		ViewHolder viewHolder = null; 
        	
            if (convertView == null) { 
            	viewHolder = new ViewHolder();
            	convertView = LayoutInflater.from(context).inflate(R.layout.view_unforget, null);
    			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvUnforgetName);
    			viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvUnforgetTime);
    			convertView.setTag(viewHolder);
    			 
            }  
            else { 
            	viewHolder = (ViewHolder) convertView.getTag();
            } 
            
            convertView.setBackgroundResource(R.drawable.btn_bg_unforget);
            Unforget unforget = array.get(position);
//            viewHolder.tvName.getBackground().setAlpha(100);
            viewHolder.tvName.setText(unforget.getName());
            //viewHolder.tvTime.getBackground().setAlpha(100);
            
            String time = unforget.getYear()+"年"+unforget.getMonth()+"月"+unforget.getDay()+"日 "+unforget.getHour()+"时"+unforget.getMinute()+"分";
            viewHolder.tvTime.setText(time);
            
//            viewHolder.tvName.setClickable(false);
//            viewHolder.tvName.setFocusable(false);
//            viewHolder.tvName.setEnabled(false);
//            viewHolder.tvName.setFocusableInTouchMode(false);

            return convertView; 
        } 
        
        final static class ViewHolder {
        	TextView tvName;
    		TextView tvTime;
    		
    	}
} 
