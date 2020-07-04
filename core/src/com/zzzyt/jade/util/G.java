package com.zzzyt.jade.util;

import com.badlogic.gdx.utils.ObjectMap;

public class G {
	public static ObjectMap<String, Object> globals = new ObjectMap<String, Object>();

	public static Object get(String key) {
		return globals.get(key);
	}

	public static Object put(String key, Object value) {
		globals.put(key, value);
		return value;
	}

	public static void remove(String key) {
		globals.remove(key);
	}
}
