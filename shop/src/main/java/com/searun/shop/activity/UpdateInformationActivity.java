package com.searun.shop.activity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.MemberRankDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToMemberDto;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;
import com.searun.shop.view.MyEdittext;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateInformationActivity extends Activity implements OnClickListener{
	
	private MyEdittext email;
	private MyEdittext mobile;
	private MyEdittext tel;
	private MyEdittext address;
	private MyEdittext qq;
	private CustomProgressDialog pro;
	private Button back;
	private Button submit;
	private SharedPreferences sp;
	private MemberDto md;
	private static UpdateInformationActivity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_updateinfo);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		instance = this;
		findView();
	}
	private void findView(){
		sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		md = (MemberDto) StoreObject.readOAuth("md", sp);
		pro = CustomProgressDialog.createDialog(this);
		Drawable drawable = getResources().getDrawable(R.drawable.mi_icon);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		pro.setMessage("加载中，请稍后");
		back = ((Button) findViewById(R.id.back));
		back.setOnClickListener(this);
		TextView title = ((TextView) findViewById(R.id.title));
		title.setText("修改信息");
		findViewById(R.id.other).setVisibility(View.GONE);
		email = (MyEdittext) findViewById(R.id.email);
		email.setTv("E-mail：");
		email.setDrawable(drawable);
		mobile = (MyEdittext) findViewById(R.id.mobile);
		mobile.setTv("手机：");
		mobile.setDrawable(drawable);
		tel = (MyEdittext) findViewById(R.id.tel);
		tel.setTv("电话：");
		address = (MyEdittext) findViewById(R.id.address);
		address.setTv("联系地址：");
		qq = (MyEdittext) findViewById(R.id.qq);
		qq.setTv("联系QQ：");
//		emailEditText = (EditText) findViewById(R.id.email_edittext);
//		mobileEditText = (EditText) findViewById(R.id.mobile_edittext);
//		telEditText = (EditText) findViewById(R.id.tel_edittext);
//		addressEditText = (EditText) findViewById(R.id.address_edittext);
//		qqEditText = (EditText) findViewById(R.id.qq_edittext);
		submit = (Button) findViewById(R.id.submit_button);
		
		getMember(md);
	}
	
	/**
	 * 修改提交
	 */
	private void submitMember(MemberDto memberDto){
		MemberDto member = new MemberDto();
		member.setEmail(email.getEdittext().toString());
		member.setMobile(mobile.getEdittext().toString());
		member.setPhone(tel.getEdittext().toString());
		member.setAddress(address.getEdittext().toString());
		member.setQq(qq.getEdittext().toString());
		member.setId(memberDto.getId());
		HttpUtil.post("updateInformation.action", NetWork.getParamsList(member, memberDto, null), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("修改信息", response.toString());
				try {
					PdaResponse<MemberDto> pdaResponse = JsonToMemberDto.parserLoginJson(response.toString());
					if (pdaResponse.isSuccess()) {
						MemberDto md = pdaResponse.getData();
						sp.edit().putString("md", StoreObject.saveOAuth(md)).commit();
						finish();
					} else {
						Toast.makeText(instance, pdaResponse.getMessage(), Toast.LENGTH_LONG).show();
					}
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
				Log.i("完成", "修改完成");
				if (pro.isShowing()) {
					pro.cancel();
				}
			}
		});
		
	}
	/**
	 * 获取个人信息
	 * @param md
	 */
	private void getMember(MemberDto md){
		HttpUtil.post("memberIndex.action", NetWork.getCart(null, md), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("用户信息", "用户信息：" + response.toString());
				try {
					if (response.optBoolean("success")) {
						
						analyzeMemer(response);
					}
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
				}
			}
		});
	}
	/**
	 * 解析获取到的个人信息
	 * @param jsonObject
	 * @throws JSONException 
	 */
	private void analyzeMemer(JSONObject jsonObject) throws Exception{
		try {
			PdaResponse<MemberDto> response = JsonToMemberDto.parserLoginJson(jsonObject.toString());
			md = response.getData();
			email.setEt(md.getEmail());
			mobile.setEt(md.getMobile());
			tel.setEt(md.getPhone());
			address.setEt(md.getAddress());
			qq.setEt(md.getQq());
			submit.setOnClickListener(instance);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		case R.id.submit_button:
			if (email.getEdittext().toString().equals("")) {
				Toast.makeText(instance, "Email不能为空", Toast.LENGTH_SHORT).show();
			} else if (mobile.getEdittext().toString().equals("")) {
				Toast.makeText(instance, "手机号码不能为空", Toast.LENGTH_SHORT).show();
			} else {
				pro.show();
				submitMember(md);
			}
			break;
		}
	}
}
