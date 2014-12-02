package com.example.shenghuobang.Password;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sqliteDataBase.Model.Password;
import sqliteDataBase.Model.Unforget;

import com.example.shenghuobang.FileOper;
import com.example.shenghuobang.R;
import com.example.shenghuobang.R.id;
import com.example.shenghuobang.R.layout;
import com.example.shenghuobang.Unforget.UnforgetActivity;
import com.example.shenghuobang.Unforget.UnforgetAdapter;
import com.example.shenghuobang.Unforget.UpdateUnforgetActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class PasswordActivity extends Activity  {
	
	private Button btnAddPassword;
	private TextView mActionText;
	private GridView gridViewPassword;
	
	private sqliteDataBase.Bll.Password bllPassword;
	private List<sqliteDataBase.Model.Password> listPassword;
	private PasswordAdapter passwordAdapter;
	
	private String pathName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);
		
		bllPassword = new sqliteDataBase.Bll.Password(this);
		pathName = Environment.getExternalStorageDirectory().getAbsolutePath(); 
		
		gridViewPassword = (GridView) findViewById(R.id.gridViewPassword);
		
		setEmptyData();
		
		

		gridViewPassword.setOnItemClickListener(new OnItemClickListener() { 
			
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
            { 
            	
            	Log.i("tag", "按钮按下"); 
            	
                Password password = listPassword.get(position);
                
                Log.i("tag", "读数据成功");	

				Intent intent = new Intent();
				intent .putExtra("id", password.getId());
				intent .putExtra("name", password.getName());
				intent .putExtra("passWord", password.getPassWord());
				intent.putExtra("soundFileName", password.getSoundFileName());
				
                intent.setClass(PasswordActivity.this, UpdatePasswordActivity.class);
                startActivityForResult(intent, 1);
            } 
        }); 
		
		gridViewPassword.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				
				 final Password password = listPassword.get(position);
				 
				AlertDialog.Builder builder = new AlertDialog.Builder(PasswordActivity.this);
				
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("你确定要删除吗");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
						FileOper fileOper = new FileOper();
						File file = new File(pathName +"/"+ password.getSoundFileName());
						fileOper.deleteFile(file);
						bllPassword.delete(password.getId());
						
						updateGridView();
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Toast.makeText(PasswordActivity.this, "你选择了取消" , Toast.LENGTH_SHORT).show();
					}
				});
				
				builder.create().show(); 
				return true;
			}
		});
		
		btnAddPassword = (Button) findViewById(R.id.btnAddPassword);
		
		btnAddPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
                intent.setClass(PasswordActivity.this, AddPasswordActivity.class);
                startActivityForResult(intent, 1);
			}
		});
	}
	
	
	private List<Password> getData(){
		listPassword = new ArrayList<sqliteDataBase.Model.Password>();
		
		Cursor cursor = bllPassword.query();

		while(cursor.moveToNext()){
			int id;
			String name;
			String password;
			String soundFileName;
			
			int idIndex =  cursor.getColumnIndex("id");
    		int nameIndex = cursor.getColumnIndex("name");
    		int passwordIndex = cursor.getColumnIndex("passWord");
    		int soundFileNameIndex = cursor.getColumnIndex("soundFileName");
    		
    		id = cursor.getInt(idIndex);
    		name = cursor.getString(nameIndex);
    		password = cursor.getString(passwordIndex);
    		soundFileName = cursor.getString(soundFileNameIndex);

    		Log.i("tag", "密码名"+name);
    		sqliteDataBase.Model.Password modelPassword = new Password(id,name, password, soundFileName);
    		listPassword.add(modelPassword);
		}
		return listPassword;
		
	}
	private void setEmptyData(){
		Log.i("tag", "没有密码数据");
		TextView emptyView = new TextView(PasswordActivity.this);  
        emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
        emptyView.setText("没有密码数据，请先添加密码数据");  
        emptyView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        emptyView.setVisibility(View.GONE);  
        ((ViewGroup)gridViewPassword.getParent()).addView(emptyView);  
        gridViewPassword.setEmptyView(emptyView);
	}
	private void updateGridView(){
		passwordAdapter = new PasswordAdapter(this, getData());
		passwordAdapter.notifyDataSetChanged();
		gridViewPassword.invalidateViews();
		gridViewPassword.setAdapter(passwordAdapter);
	}

	@Override
	protected void onResume(){
		updateGridView();
		super.onResume();
	}

	
}
