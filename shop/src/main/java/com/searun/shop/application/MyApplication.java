package com.searun.shop.application;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.searun.shop.data.CartItemDto;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.ProductDto;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
	
	private List<CartItemDto> cartList = new ArrayList<CartItemDto>();
	private List<CartItemDto> confirmList = new ArrayList<CartItemDto>();

	private MemberDto md;

	public MemberDto getMd() {
		return md;
	}
	public void setMd(MemberDto md) {
		this.md = md;
	}
	
	public List<CartItemDto> getConfirmList() {
		return confirmList;
	}
	public void setConfirmList(List<CartItemDto> confirmList) {
		this.confirmList = confirmList;
	}
	public List<CartItemDto> getCartList() {
		return cartList;
	}
	public void setCartList(List<CartItemDto> cartList) {
		this.cartList = cartList;
	}
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());
	}


	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}
}
