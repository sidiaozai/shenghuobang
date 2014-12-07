package com.example.shenghuobang.Setting;

import com.example.shenghuobang.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.widget.Toast;

public class SettingActivity extends PreferenceActivity implements 
		Preference.OnPreferenceClickListener,
		Preference.OnPreferenceChangeListener {
	private static String TAG = "HelloPreference";          
	private CheckBoxPreference enable_sound; 
	private CheckBoxPreference enable_vibrate;
	private CheckBoxPreference enable_password;
	
	
	private EditTextPreference number_edit;      
	private Preference data_update;            
	private Preference version_update; 
	private Preference feedback; 
	
	private SharedPreferences mySharedPreferences; 

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.mypreference);
		
		enable_sound = (CheckBoxPreference) findPreference("apply_enable_sound");
		enable_vibrate = (CheckBoxPreference) findPreference("apply_enable_vibrate");
		enable_password = (CheckBoxPreference) findPreference("apply_enable_password");
		number_edit = (EditTextPreference)findPreference("number_edit");
		data_update = (Preference) findPreference("data_update");
		version_update = (Preference) findPreference("version_update");
		feedback = (Preference) findPreference("feedback");
		
		number_edit.setOnPreferenceClickListener(this);
		number_edit.setOnPreferenceChangeListener(this);
		
		mySharedPreferences= getSharedPreferences("shenghuobang", Activity.MODE_PRIVATE); 
		
		enable_sound.setChecked(mySharedPreferences.getBoolean("apply_enable_sound", false));
		enable_vibrate.setChecked(mySharedPreferences.getBoolean("apply_enable_vibrate", false));
		enable_password.setChecked(mySharedPreferences.getBoolean("apply_enable_password", false));
		number_edit.setDefaultValue(mySharedPreferences.getString("number_edit", null));
		
	}

	
	// 点击事件触发
	@Override
	public boolean onPreferenceClick(Preference preference) {
		
		Log.i(TAG, "onPreferenceClick----->"+String.valueOf(preference.getKey()));
		if(preference.getKey().equals("number_edit")){
			number_edit.setText(mySharedPreferences.getString("number_edit", null));
			
		} 
		
		return false;
	}
   //点击事件触发
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		Log.i(TAG, "onPreferenceTreeClick----->"+preference.getKey());
		
		
		if(preference.getKey().equals("apply_enable_sound")){
			SharedPreferences.Editor editor = mySharedPreferences.edit(); 
			editor.putBoolean("apply_enable_sound", enable_sound.isChecked());
			editor.commit();
		}else if(preference.getKey().equals("apply_enable_vibrate")){
			SharedPreferences.Editor editor = mySharedPreferences.edit(); 
			editor.putBoolean("apply_enable_vibrate", enable_vibrate.isChecked());
			editor.commit();
		}else if(preference.getKey().equals("apply_enable_password")){
			SharedPreferences.Editor editor = mySharedPreferences.edit(); 
			editor.putBoolean("apply_enable_password", enable_password.isChecked());
			editor.commit();
		}else if(preference.getKey().equals("data_update")){
			Intent i = new Intent(SettingActivity.this, UpdateDataActivity.class);
			startActivity(i);
			return true;
		}else if (preference.getKey().equals("version_update")) {

			Intent i = new Intent(SettingActivity.this, UpdateVersionActivity.class);
			startActivity(i);
			return true;
		}else if(preference.getKey().equals("feedback")){
			Toast.makeText(getApplicationContext(), "asdfas", Toast.LENGTH_SHORT).show();
		}
		
		
		return false;
	}

	public boolean onPreferenceChange(Preference preference, Object objValue) {
		
		if (preference == number_edit) {
			SharedPreferences.Editor editor = mySharedPreferences.edit(); 
			editor.putString("number_edit", objValue.toString());
			editor.commit();
			return true; // 不保存更新值
		}
		return true;  //保存更新后的值
	}
}
