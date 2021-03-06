package com.example.shenghuobang.Setting;

import com.example.shenghuobang.CommonValue;
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
import android.view.KeyEvent;
import android.widget.Toast;

public class SettingActivity extends PreferenceActivity implements 
		Preference.OnPreferenceClickListener,
		Preference.OnPreferenceChangeListener {
	private static String TAG = "HelloPreference";          
	private CheckBoxPreference enableSound; 
	private CheckBoxPreference enableVibrate;
	private CheckBoxPreference enablePassword;
	
	private Preference setLoginPassword;      
	private Preference dataUpdate;            
	private Preference versionUpdate; 
	private Preference feedback; 
	
	
	private CommonValue commonValue;
	//private SharedPreferences mySharedPreferences; 

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.mypreference);
		
		enableSound = (CheckBoxPreference) findPreference(CommonValue.APPLY_ENABLE_SOUND);
		enableVibrate = (CheckBoxPreference) findPreference(CommonValue.APPLY_ENABLE_VIBRATE);
		enablePassword = (CheckBoxPreference) findPreference(CommonValue.APPLY_ENABLE_PASSWORD);
		setLoginPassword = (Preference)findPreference(CommonValue.LOGIN_PASSWORD);
		dataUpdate = (Preference) findPreference(CommonValue.DATA_UPDATE);
		versionUpdate = (Preference) findPreference(CommonValue.VERSION_UPDATE);
		feedback = (Preference) findPreference(CommonValue.FEEDBACK);
		
//		login_password.setOnPreferenceClickListener(this);
//		login_password.setOnPreferenceChangeListener(this);
		
		commonValue = new CommonValue(this);
		//mySharedPreferences= getSharedPreferences(CommonValue.AppName, Activity.MODE_PRIVATE); 
		
		
	}

	
	// 点击事件触发
	@Override
	public boolean onPreferenceClick(Preference preference) {
		
		Log.i(TAG, "onPreferenceClick----->"+String.valueOf(preference.getKey()));
		
		
		return false;
	}
   //点击事件触发
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		Log.i(TAG, "onPreferenceTreeClick----->"+preference.getKey());
		
		
		if(preference.getKey().equals(CommonValue.APPLY_ENABLE_SOUND)){
			commonValue.setApplyEnableSound(enablePassword.isChecked());
		}else if(preference.getKey().equals(CommonValue.APPLY_ENABLE_VIBRATE)){
			commonValue.setApplyEnableVibrate(enablePassword.isChecked());
		}else if(preference.getKey().equals(CommonValue.APPLY_ENABLE_PASSWORD)){
			commonValue.setApplyEnablePassword(enablePassword.isChecked());
		}else if(preference.getKey().equals(CommonValue.LOGIN_PASSWORD)){
			Intent i = new Intent(SettingActivity.this, SetLoginPassword.class);
			startActivity(i);
			return true;
		}else if(preference.getKey().equals(CommonValue.DATA_UPDATE)){
			Intent i = new Intent(SettingActivity.this, UpdateDataActivity.class);
			startActivity(i);
			return true;
		}else if (preference.getKey().equals(CommonValue.VERSION_UPDATE)) {

			Intent i = new Intent(SettingActivity.this, AuthActivity.class);
			//Intent i = new Intent(SettingActivity.this, UpdateVersionActivity.class);
			startActivity(i);
			return true;
		}else if(preference.getKey().equals(CommonValue.FEEDBACK)){
			Toast.makeText(getApplicationContext(), "功能未实现", Toast.LENGTH_SHORT).show();
			return true;
		}
		
		
		return false;
	}

	public boolean onPreferenceChange(Preference preference, Object objValue) {
		
//		if (preference == login_password) {
//			SharedPreferences.Editor editor = mySharedPreferences.edit(); 
//			editor.putString(CommonValue.LOGIN_PASSWORD, objValue.toString());
//			editor.commit();
//			return true; // 不保存更新值
//		}
		return true;  //保存更新后的值
	}
	@Override
	protected void onResume() {
		
		enableSound.setChecked(commonValue.getApplyEnableSound());
		enableVibrate.setChecked(commonValue.getApplyEnableVibrate());
		enablePassword.setChecked(commonValue.getApplyEnablePassword());
		//login_password.setDefaultValue(mySharedPreferences.getString(CommonValue.LOGIN_PASSWORD, null));
		super.onResume();
	}
	private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
            if((System.currentTimeMillis()-exitTime) > 2000){  
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
                exitTime = System.currentTimeMillis();   
            } else {
                finish();
                System.exit(0);
            }
            return true;   
        }
        return super.onKeyDown(keyCode, event);
    }
}
