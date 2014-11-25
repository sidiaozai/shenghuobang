package com.example.shenghuobang.Unforget;

import java.util.List;

import com.example.shenghuobang.R;
import com.example.shenghuobang.R.drawable;

import sqliteDataBase.Model.Unforget;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

public class UnforgetAdapter extends BaseAdapter{ 
        //�����Ķ��� 
        private Context context; 
        //ͼƬ���� 
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
         
        //����View���� 
        public View getView(int position, View convertView, ViewGroup parent) { 
             
        	final Button buttonView; 
            if (convertView == null) { 
            	buttonView = new Button(context); 
            }  
            else { 
            	buttonView = (Button) convertView; 
            } 
            buttonView.setBackgroundResource(R.drawable.btn_bg_unforget);//ΪImageView����ͼƬ��Դ 
            Unforget unforget = array.get(position);
            buttonView.setText(unforget.getName());
            buttonView.setClickable(false);
            buttonView.setFocusable(false);
//            buttonView.setEnabled(false);
//            buttonView.setFocusableInTouchMode(false);
//            buttonView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					buttonView.setText(null);
//				}
//			});
            return buttonView; 
        } 
} 