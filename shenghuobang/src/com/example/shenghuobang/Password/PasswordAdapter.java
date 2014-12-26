package com.example.shenghuobang.Password;

import java.util.List;

import com.example.shenghuobang.R;

import sqliteDataBase.Model.Password;
import sqliteDataBase.Model.Unforget;
import android.R.color;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PasswordAdapter extends BaseAdapter{ 
        //�����Ķ��� 
        private Context context; 
        //ͼƬ���� 
        private List<sqliteDataBase.Model.Password> array;

        public  PasswordAdapter(Context context,List<sqliteDataBase.Model.Password> array){ 
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
         
        //����View���� 
        public View getView(int position, View convertView, ViewGroup parent) { 
        	
//        	Button buttonView; 
//            if (convertView == null) { 
//            	buttonView = new Button(context); 
//            }  
//            else { 
//            	buttonView = (Button) convertView; 
//            } 
//            
//            buttonView.setBackgroundResource(R.drawable.btn_bg_password);
//	          
//            Password password = array.get(position);
//	        buttonView.setText(password.getName());
//	        buttonView.setGravity(Gravity.CENTER);
//	        buttonView.setClickable(false);
//	        buttonView.setFocusable(false);
//	        buttonView.setEnabled(false);
//	        buttonView.setFocusableInTouchMode(false);
//
//            return buttonView;  
        
        	TextView textView; 
	          if (convertView == null) { 
	        	  textView = new TextView(context); 
	          }  
	          else { 
	        	  textView = (TextView) convertView; 
	          } 
	          
	          textView.setBackgroundResource(R.drawable.btn_bg_password);
		          
	          Password password = array.get(position);
	          textView.setText(password.getName());
	          textView.setGravity(Gravity.CENTER);
	          textView.setTextColor(context.getResources().getColor(R.color.black));
//	          textView.setClickable(false);
//	          textView.setFocusable(false);
//	          textView.setEnabled(false);
//	          textView.setFocusableInTouchMode(false);
	
	          return textView; 
        	
        } 
        
} 
