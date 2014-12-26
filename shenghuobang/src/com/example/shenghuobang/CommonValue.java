package com.example.shenghuobang;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.util.Log;

public class CommonValue {
	
	public static String AppName = "shenghuobang";
	
	public static String APPLY_ENABLE_SOUND = "apply_enable_sound";
	public static String APPLY_ENABLE_VIBRATE = "apply_enable_vibrate";
	public static String APPLY_ENABLE_PASSWORD = "apply_enable_password";
	public static String APPLY_IS_PICTURE_PASSWORD = "apply_is_picture_password";
	public static String LOGIN_PASSWORD = "login_password";
	public static String LOGIN_TIMES = "login_times";
	public static String DATA_UPDATE = "data_update";
	public static String VERSION_UPDATE = "version_update";
	public static String FEEDBACK = "feedback";
	
	
	public static String SoundFileType =".3gp";
	public static String fileName = "fileName";
	
	public static DecimalFormat myFormatter = new DecimalFormat("0.00"); 
	
	public static String ClickPosition = "clickposition";
	public static int ClickPassword =1;
	public static int ClickSetPassword=2;
	
	public static String SwitchSource ="switch_source";
	public static int PageSource =1;
	public static int AppSource =2;
	
	
	public String EveryTimeRecond="EveryTimeRecond";
	public String moneyAmount="moneyAmount";
	public String recondType="recondType";
	public String consumePurpose="consumePurpose";
	public String recondTime="recondTime";
	public String remarks="remarks";
	
	public String SecretCode="SecretCode";
	public String secretCode="secretCode";
	public String soundFilePath="soundFilePath";
	
	public String Memo="Memo";
	public String eventDate="eventDate";
	
	
	

	
	private static SharedPreferences mySharedPreferences; 
	
	private Context context;
	public CommonValue(Context context ){
		this.context = context;
		mySharedPreferences=  context.getSharedPreferences(CommonValue.AppName, Activity.MODE_PRIVATE); 
	}
	
	//////////////////////////////////////
	public String getLoginPassword(){
		
		AESEnc aesDecrypt = new AESEnc();
		
		String pass =  mySharedPreferences.getString(CommonValue.LOGIN_PASSWORD, null);
		
		
		if(pass==null)
			return "";
		byte[] passwordByte=null;
		try {
			byte[] mEncryptText = pass.getBytes("ISO-8859-1");
			passwordByte = aesDecrypt.decrypt(mEncryptText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String password = null;
		try {
			password = new String(passwordByte,"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Log.i("getPassword2", password);

		return password;
	}
	public void setLoginPassword(String password) {
		
		if((password==null)||(password.equals(null)))
			return;

		AESEnc aesEncrypt = new AESEnc();
		byte[] mEncryptText = null;
		try {
			mEncryptText = aesEncrypt.encrypt(password.getBytes("ISO-8859-1"));
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		try {
			password = new String(mEncryptText,"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putString(CommonValue.LOGIN_PASSWORD, password);
		editor.commit();
	}
	
	
	
	public String Decrypt(String password){
		
		
		
		byte[] mEncryptText=null;
		try {
			mEncryptText = password.getBytes("ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		AESEnc aesDecrypt = new AESEnc();
		byte [] passwordByte= null;
		try {
			passwordByte = aesDecrypt.decrypt(mEncryptText);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		String retStr=null;
		try {
			retStr =  new String(passwordByte,"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return retStr;
	}
	
	public String Encrypt(String password){
		AESEnc aesEncrypt = new AESEnc();
		byte[] mEncryptText = null;
		try {
			mEncryptText = aesEncrypt.encrypt(password.getBytes("ISO-8859-1"));
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		String encryt= null;
		try {
			encryt=  new String(mEncryptText,"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return encryt;
	}
	
	/////////////////////////////////////////
	public boolean getApplyEnableSound(){
		return mySharedPreferences.getBoolean(CommonValue.APPLY_ENABLE_SOUND, false);
	}
	public void setApplyEnableSound(boolean isCheck){
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putBoolean(CommonValue.APPLY_ENABLE_SOUND, isCheck);
		editor.commit();
	}
	
	///////////////////////////////////////////
	public boolean getApplyEnableVibrate(){
		return mySharedPreferences.getBoolean(CommonValue.APPLY_ENABLE_VIBRATE, false);
	}
	public void setApplyEnableVibrate(boolean isCheck){
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putBoolean(CommonValue.APPLY_ENABLE_VIBRATE, isCheck);
		editor.commit();
	}
	////////////////////////////////////////////
	public boolean getPasswordType(){
		return mySharedPreferences.getBoolean(CommonValue.APPLY_IS_PICTURE_PASSWORD, false);
	}
	public void setPasswordType(boolean isPicture){
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putBoolean(CommonValue.APPLY_IS_PICTURE_PASSWORD, isPicture);
		editor.commit();
	}
	////////////////////////////////////////////
	public boolean getApplyEnablePassword(){
		return mySharedPreferences.getBoolean(CommonValue.APPLY_ENABLE_PASSWORD, false);
	}
	public void setApplyEnablePassword(boolean isCheck){
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putBoolean(CommonValue.APPLY_ENABLE_PASSWORD, isCheck);
		editor.commit();
	}
	
	public int getLoginTimes(){
		return mySharedPreferences.getInt(CommonValue.LOGIN_TIMES, 10);
	}
	public void setLoginTimes(int loginTimes){
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putInt(CommonValue.LOGIN_TIMES,loginTimes);
		editor.commit();
	}
	
	
	
}
