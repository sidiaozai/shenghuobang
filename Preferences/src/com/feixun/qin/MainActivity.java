package com.feixun.qin;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;

public class MainActivity extends Activity{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState) ;
		Intent intent=getIntent() ;
		if(intent!=null){
			String type=intent.getStringExtra("type") ; 
		    Log.i("new intent", type!=null?type:" deault intent"+ intent.getAction()!=null?intent.getAction():"no intent" ) ;	
		}
		else
			Log.i("default intent", "myself") ;
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu) ;
		menu.add(0, 1,0, "ShowPreference") ;
		menu.add(0,2,1,"Quit?") ;
		return true ;
	}
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item) ;
		switch(item.getItemId()){
		case  1:
			Intent i=new Intent(getBaseContext(),HelloPreference.class) ;
			startActivity(i) ;
			break ;
		default :
			break ;
		}
		return true ;
	}
}