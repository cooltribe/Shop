package com.searun.shop.util;

import android.annotation.SuppressLint;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateChange {
	

	/**
	 * 将时间戳转换成标准格式
	 * @param string
	 * @return
	 */
public static String timestampToStandard(String string){
	Date date = new Date(Long.valueOf(string) * 1000);
	return date.toString();
}

/**
 * 将标准格式“yyyy-MM-dd HH:mm:ss”转换成date
 * @param string
 * @return
 */
@SuppressLint("SimpleDateFormat")
public static java.util.Date stringToDate (String string){
	java.util.Date date = null;
	try {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = sdf.parse(string);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return date;
	
}
}
