package com.searun.shop.activity;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.searun.shop.R;
import com.searun.shop.adapter.MyFragmentPagerAdapter;
import com.searun.shop.data.ProductImage;
import com.searun.shop.fragment.JudgeImageShowFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

public class JudgeImageShowActivity extends FragmentActivity {

	private ViewPager viewPager;
	private List<ProductImage> productImages;
	private int big;
	private DisplayImageOptions options;
	private Intent intent;
	private FragmentManager fm;
	private List<Fragment> fragmentList;
	private MyFragmentPagerAdapter adapter;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_judge_image_show);
		init();
		initFragment();
		
	}
	@SuppressWarnings("unchecked")
	private void init(){
		intent = getIntent();
		fragmentList = new ArrayList<Fragment>();
		productImages = (List<ProductImage>) intent.getSerializableExtra("list");
		big = intent.getExtras().getInt("big");
		viewPager = (ViewPager) findViewById(R.id.nav_viewpager);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.mrpic)
				.showImageForEmptyUri(R.drawable.mrpic)
				.showImageOnFail(R.drawable.mrpic)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
	}
	private void initFragment(){
		fm = getSupportFragmentManager();
		for (int i = 0; i < productImages.size(); i++) {
			JudgeImageShowFragment fragment = new JudgeImageShowFragment(productImages.get(i), options);
			fragmentList.add(fragment);
		}
		adapter = new MyFragmentPagerAdapter(fm, fragmentList);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(big);
	}
}
