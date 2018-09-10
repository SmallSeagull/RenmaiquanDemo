package com.dolphin.renmaicircle.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Map;

/**
 * 
* @ClassName: AppSharedPreferences 
* @Description: SharedPreference管理类
* @author yiw
* @date 2015-12-30 上午11:05:27 
*
 */
public class AppSharedPreferences {

	private SharedPreferences sharedPreferences;
	private static AppSharedPreferences appSharedPreferences;

	private AppSharedPreferences(Context context) {
		sharedPreferences = context.getSharedPreferences("information", Activity.MODE_PRIVATE);
	}

	public static AppSharedPreferences getInstance(Context context) {
		if (null == appSharedPreferences) {
			appSharedPreferences = new AppSharedPreferences(context);
		}
		return appSharedPreferences;
	}

	public String get(String key) {
		if (null == sharedPreferences) {
			return "";
		}
		String result = sharedPreferences.getString(key, "");
		if (!TextUtils.isEmpty(result)) {
			return result;
		}
		return "";
	}


	public boolean getBoolen(String key){
		if (null == sharedPreferences) {
			return false;
		}
		boolean result = sharedPreferences.getBoolean(key, false);
		return result;
	}

	public int getInt(String key) {
		if (null == sharedPreferences) {
			return -1;
		}
		int result = sharedPreferences.getInt(key, -1);
		return result;
	}
	public String getToken(){
		if (null == sharedPreferences) {
			return "";
		}
		String result = sharedPreferences.getString(Constant.TOKEN, "");
		return result;
	}
	public boolean getLoginStatus(){
		if (null == sharedPreferences) {
			return false;
		}
		boolean b = sharedPreferences.getBoolean(Constant.ISLOGIN, false);
		return b;
	}

	public boolean getIsFirst(){
		if (null == sharedPreferences) {
			return true;
		}
		boolean b = sharedPreferences.getBoolean(Constant.ISFIRST, true);
		return b;
	}



	public void set(String key, String value) {
		if (null != sharedPreferences) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString(key, value);
			editor.apply();
		}
	}
	public void set(String key, int value) {
		if (null != sharedPreferences) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putInt(key, value);
			editor.apply();
		}
	}
	public void set(String key, boolean value) {
		if (null != sharedPreferences) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean(key, value);
			editor.apply();
		}
	}

	public void remove(String... key) {
		for (String k : key) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.remove(k);
			editor.apply();
		}
	}

	public void clear(){
		if (null != sharedPreferences) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			Map<String, ?> all = sharedPreferences.getAll();

			for (String key : all.keySet()) {
				if(!Constant.NUMBER.equals(key)&&!Constant.PASSWORD.equals(key)){
					editor.remove(key);
				}
			}
			editor.apply();
		}
	}

}
