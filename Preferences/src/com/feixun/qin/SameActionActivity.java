package com.feixun.qin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class SameActionActivity  extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState) ;
		Intent i=new Intent("com.feixun.action.seemAction") ;
		i.putExtra("type", "wifi") ;
		startActivity(i) ;
	}

}
