package com.searun.shop.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import org.apache.commons.codec.binary.Base64;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class StoreObject {
	Context context;
	
	
	
	public StoreObject(Context context) {
		super();
		this.context = context;
	}

	public static String saveOAuth(Object oAuth_1) {
//		SharedPreferences preferences = context.getSharedPreferences("base64",
//				Context.MODE_PRIVATE);
		String oAuth_Base64 = null;
		
		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(oAuth_1);
			oAuth_Base64 = new String(Base64.encodeBase64(baos
					.toByteArray()));
//			Editor editor = preferences.edit();
//			editor.putString("oAuth_1", oAuth_Base64);
//
//			editor.commit();
			Log.i("存储结果", oAuth_Base64);
		} catch (IOException e) {
			// TODO Auto-generated
		}
		Log.i("ok", "存储成功");
		return oAuth_Base64;
	}
	
	public static Object readOAuth(String storeName ,SharedPreferences preferences) {
		Object oAuth_1 = null;
//		SharedPreferences preferences = context.getSharedPreferences("base64",
//				Context.MODE_PRIVATE);
		String productBase64 = preferences.getString(storeName, "");
		if ( !"".equals( productBase64)) {
			
			//读取字节
			byte[] base64 = Base64.decodeBase64(productBase64.getBytes());
			
			ByteArrayInputStream bais = new ByteArrayInputStream(base64);
		
		try {
			//再次封装
			ObjectInputStream bis = new ObjectInputStream(bais);
			try {
				//读取对象
				oAuth_1 = bis.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return oAuth_1;
	}
}
