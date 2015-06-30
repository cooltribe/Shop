package com.searun.shop.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.searun.shop.R;
import com.searun.shop.fragment.ClassificationFragment;
import com.searun.shop.fragment.HomePageFragment;
import com.searun.shop.fragment.ShopCartFragment;
import com.searun.shop.fragment.UserCenterFragment;
import com.searun.shop.util.ClickInterface;
import com.searun.shop.util.UpdateService;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener, ClickInterface {
	private static final String TAG = "MainActivity";
	private static MainActivity instance;
	private FragmentManager fm;
	private FragmentTransaction ft;
	private RadioGroup nav;
	private Intent intent;
	// 退出时间
	private long currentBackPressedTime = 0;

	// 退出间隔

	private static final int BACK_PRESSED_INTERVAL = 2000;

	// 重写onBackPressed()方法,继承自退出的方法

	@Override
	public void onBackPressed() {

		// 判断时间间隔

		if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {

			currentBackPressedTime = System.currentTimeMillis();

			Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();

		} else {

			// 退出

			finish();

		}
	}
	
	private void dataInit() {
		instance = this;
		this.fm = getSupportFragmentManager();
		
		intent = new Intent();
		intent.setAction("com.searun.shop.util.UpdateService");
		intent.setPackage(getPackageName());
		startService(intent);
	}
	
	private void viewInit() {
		this.nav = ((RadioGroup) findViewById(R.id.nav));
		this.nav.setOnCheckedChangeListener(instance);
		if (!HomePageFragment.getInstance().isAdded()) {
			Log.i(TAG, "首页1");
			fm.beginTransaction().replace(R.id.fragment, HomePageFragment.getInstance(), "首页").commitAllowingStateLoss();
		}
	}
	private void anim(){
		ValueAnimator animator = ValueAnimator.ofInt(1,100);
		animator.setDuration(1000);
		animator.setStartDelay(500);
		animator.setRepeatCount(2);
		animator.start();
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(nav, "alpha", 1f,0f,1f);
	}
	public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt) {
		try {
			this.ft = this.fm.beginTransaction();
			switch (paramInt) {
			default:
			case R.id.index:
				if (!HomePageFragment.getInstance().isAdded()) {
					Log.i(TAG, "首页");
					ft.replace(R.id.fragment, HomePageFragment.getInstance(), "首页");
				}
				break;
			case R.id.classification:
				if (!ClassificationFragment.getInstance().isAdded()) {
					Log.i(TAG, "分类");
					ft.replace(R.id.fragment, ClassificationFragment.getInstance(), "分类");
				}
				break;
			case R.id.shopcart:
				if (!ShopCartFragment.getInstance().isAdded()) {
					Log.i(TAG, "购物车");
					ft.replace(R.id.fragment, ShopCartFragment.getInstance(), "购物车");
				}
				break;
			case R.id.usercenter:
				if (!UserCenterFragment.getInstance().isAdded()) {
					Log.i(TAG, "个人中心");
					ft.replace(R.id.fragment, UserCenterFragment.getInstance(), "个人中心");
				}
				break;
			}
			this.ft.commitAllowingStateLoss();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		// requestWindowFeature(7);
		setContentView(R.layout.activity_main);
		// getWindow().setFeatureInt(7, R.layout.index_titlebar);
		dataInit();
		viewInit();
	}

	protected void onDestroy() {
		super.onDestroy();
		try {
			stopService(intent);
			Process.killProcess(Process.myPid());
			System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * i==1回调跳转首页
	 */
	@Override
	public void onClick(int i) {
		// TODO Auto-generated method stub
		try {
			if (i == 1) {
				if (!HomePageFragment.getInstance().isAdded()) {
					fm.beginTransaction().replace(R.id.fragment, HomePageFragment.getInstance(), "首页")
							.commitAllowingStateLoss();
					((RadioButton) findViewById(R.id.index)).setChecked(true);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
