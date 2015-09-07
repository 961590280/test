package com.weimingfj.common.utils;

import java.util.Map;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

/**
 * Map utils
 */
public class MapUtils { 
	public static String getString(Map<String, ?> map, String key) {
		return getString(map, key, "");
	}
	public static String getString(Map<String, ?> map, String key, String def) {
		CaseInsensitiveMap<String, Object> mCaseInsensitiveMap=new CaseInsensitiveMap<String, Object>(map);
		return mCaseInsensitiveMap.get(key) == null ? def : mCaseInsensitiveMap.get(key).toString();
	}
	public static int getInt(Map<String, ?> map, String key, int def) {
		int value=def;
		try{
			value=Double.valueOf(getString(map,key)).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}
		return value;
	}
	public static int getInt(Map<String, ?> map, String key) {
		return getInt(map, key, 0);
	}
	public static float getFloat(Map<String, Object> map, String key, int def) {
		
		return Float.parseFloat(getString(map,key,String.valueOf(def)));
	}
	public static float getFloat(Map<String, Object> map, String key) { 
		return getFloat(map, key, 0);
	}
	public static double getDouble(Map<String, Object> map, String key, int def) {
		return Double.parseDouble(getString(map,key,String.valueOf(def)));
	}
	public static double getDouble(Map<String, Object> map, String key) {
		return getDouble(map, key, 0);
	}
}
