package com.searun.shop.activity;


import com.searun.shop.R;
import com.searun.shop.data.AreaDto;
import com.searun.shop.fragment.AreaLeftFragment;
import com.searun.shop.fragment.AreaLeftFragment.ItemClick;
import com.searun.shop.fragment.AreaRightFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class AreaActivity extends FragmentActivity implements ItemClick,OnClickListener{

	FragmentManager fm;
	private Button back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_area);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		findView();
		
	}
	private void findView(){
		findViewById(R.id.other).setVisibility(View.GONE);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("地址管理");
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);
		fm = getSupportFragmentManager();
		fm.beginTransaction().add(R.id.left, AreaLeftFragment.getInstance(), "省份").commitAllowingStateLoss();
	}
	
	@Override
	public void intemClickListener(Bundle bundle) {
		// TODO Auto-generated method stub
		try {
			switch (bundle.getInt("code")) {
			case 1:
				AreaDto areaDto = (AreaDto) bundle.getSerializable("area");
				System.err.println(areaDto.getName());
				fm.beginTransaction().replace(R.id.right, new AreaRightFragment(areaDto), "市").commitAllowingStateLoss();
				break;

			default:
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		fm.beginTransaction().add(area, arg1)
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}
	

}
