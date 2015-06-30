package com.searun.shop.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.AreaDto;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.ReceiverDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;
import com.searun.shop.view.MyEdittext;

import org.apache.http.Header;
import org.json.JSONObject;

public class AddAddress extends Activity implements OnClickListener{
	private static final String TAG = "AddAddress";
	private static AddAddress instance;
	private MyEdittext name;
	private MyEdittext areaPath;
	private MyEdittext address;
	private MyEdittext phone;
	private MyEdittext mobile;
	private MyEdittext zipCode;
	private CheckBox isDefault;
	private Button add;
	private SharedPreferences sp;
	private MemberDto md;
	private CustomProgressDialog pro;
	private String areaId = "";
	private AreaDto ad;
	private Button back;
	private Intent intent;
	private ReceiverDto receiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_addaddress);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		initData();
		findView();
	}
	private void initData(){
		try {
			sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
			instance = this;
			md = (MemberDto) StoreObject.readOAuth("md", sp);
			pro = CustomProgressDialog.createDialog(instance);
			intent = getIntent();
			receiver = (ReceiverDto) intent.getSerializableExtra("receiver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void findView(){
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(instance);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("地址添加");
		findViewById(R.id.other).setVisibility(View.GONE);
		name = (MyEdittext)findViewById(R.id.name);
		name.setTv("姓名:");
		areaPath = (MyEdittext) findViewById(R.id.areaPath);
		areaPath.setTv("市区:");
		areaPath.setVisibility(View.GONE,View.VISIBLE);
		areaPath.setOnClickListener(instance);
		
		address = (MyEdittext) findViewById(R.id.address);
		address.setTv("地址:");
		phone = (MyEdittext) findViewById(R.id.phone);
		phone.setTv("电话:");
		mobile = (MyEdittext) findViewById(R.id.mobile);
		mobile.setTv("手机:");
		zipCode = (MyEdittext) findViewById(R.id.zipCode);
		zipCode.setTv("邮编:");
		isDefault = (CheckBox) findViewById(R.id.isDefault);
		
		add = (Button) findViewById(R.id.add);
		add.setOnClickListener(instance);
		if (null != receiver) {
			name.setEt(receiver.getName());
//			areaPath.setText(receiver.getAreaPath());
			address.setEt(receiver.getAddress());
			phone.setEt(receiver.getPhone());
			mobile.setEt(receiver.getMobile());
			zipCode.setEt(receiver.getZipCode());
			isDefault.setChecked(receiver.getIsDefault());
			add.setText("编辑");
		} else {
			add.setText("保存");
		}
	}
	private void addNewAddress(String action){
		try {
			ReceiverDto rd = new ReceiverDto();
			rd.setName(name.getEdittext());
			rd.setAreaPath(areaId);
			rd.setAddress(address.getEdittext().toString());
			rd.setPhone(phone.getEdittext().toString());
			rd.setMobile(mobile.getEdittext().toString());
			rd.setZipCode(zipCode.getEdittext().toString());
			rd.setIsDefault(isDefault.isChecked());
			HttpUtil.post(action, NetWork.getParamsList(rd ,md,null), new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					try {
						Log.i(TAG, response.toString());
						Toast.makeText(instance, response.optString("message"), Toast.LENGTH_LONG).show();
						sp.edit().remove("area").commit();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
					try {
						Toast.makeText(instance, "网络异常", Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					super.onFinish();
					if (pro.isShowing()) {
						pro.cancel();
						finish();
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			ad = (AreaDto) StoreObject.readOAuth("area", sp);
			if (null != ad) {
				
				areaPath.setEt(ad.getName());
				areaId = ad.getPath();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sp.edit().remove("ad").commit();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			switch (v.getId()) {
			case R.id.add:
				if (null != md) {
					if (name.getEdittext().toString().equals("")) {
						Toast.makeText(instance, "收货人姓名不能为空", Toast.LENGTH_LONG).show();
					} else if (areaId.equals("") || areaId == null) {
						Toast.makeText(instance, "区域不能为空", Toast.LENGTH_LONG).show();
					} else if (address.getEdittext().toString().equals("")) {
						Toast.makeText(instance, "收货地址不能为空", Toast.LENGTH_LONG).show();
					} else if ( phone.getEdittext().toString().equals("") && mobile.getEdittext().toString().equals("")) {
						Toast.makeText(instance, "电话或手机必须填写其中一项", Toast.LENGTH_LONG).show();
					} else if (zipCode.getEdittext().toString().equals("")) {
						Toast.makeText(instance, "邮政编码不能为空", Toast.LENGTH_LONG).show();
					} else {
						pro.setMessage("加载中，请稍后").show();
						if (null != receiver) {
							addNewAddress("editReceiver.action");
						} else {
							addNewAddress("addReceiver.action");
						}
					}
				}
				break;

			case R.id.areaPath:
				Intent intent = new Intent(instance, AreaActivity.class);
				startActivity(intent);
				break;
			case R.id.back:
				sp.edit().remove("area").commit();
				finish();
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
