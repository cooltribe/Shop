package com.searun.shop.application;

import com.searun.shop.util.CommonUtils;



public class ApplicationData {
	// SharedPreferences 存储路径
	public static final String LOGISTICS_PREFERENCES = "logisticsPreferences";
	
	//progroess设置点击四周是否显示
	public static final boolean PRO_FLAG = false;
	
	/**
	 * 默认的icon保存路径
	 */
	public static String DEFAULT_ICON_PATH = CommonUtils
			.getFileSavePath("icon");
}
