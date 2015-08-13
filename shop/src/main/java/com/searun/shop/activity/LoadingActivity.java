package com.searun.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.searun.shop.service.NetIsConnectedService;

public class LoadingActivity extends Activity {
	
	private Handler handle = new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				switch (msg.what) {
				case 1:
					startActivity(new Intent(LoadingActivity.this, MainActivity.class));
					finish();
					break;

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};
	private void startService(){
		Intent intent = new Intent(LoadingActivity.this, NetIsConnectedService.class);
		startService(intent);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		startService();
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				handle.sendEmptyMessage(1);
			}
		}, 3000);
	}
}
