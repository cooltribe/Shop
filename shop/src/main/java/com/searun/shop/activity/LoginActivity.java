package com.searun.shop.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.searun.shop.R;
import com.searun.shop.application.MyApplication;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.MemberDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaRequest;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToMemberDto;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;


import org.apache.http.Header;
import org.json.JSONObject;

public class LoginActivity extends Activity implements View.OnClickListener {
	private static final String TAG = "LoginActivity";
	public static LoginActivity instance;
	private Button back;
	private MyApplication app;
	private Button login;
	private EditText passWord;
	private Button register;
	private SharedPreferences sPreferences;
	private TextView title;
	private EditText userName;
	private Button loginOut;
	private CustomProgressDialog pro;
	private void findView() {
		try {
			back = ((Button) findViewById(R.id.back));
			back.setOnClickListener(this);
			title = ((TextView) findViewById(R.id.title));
			title.setText("用户登录");
			findViewById(R.id.other).setVisibility(View.GONE);
			userName = ((EditText) findViewById(R.id.username));
			passWord = ((EditText) findViewById(R.id.password));
			register = ((Button) findViewById(R.id.register));
			register.setOnClickListener(this);
			login = ((Button) findViewById(R.id.login));
			login.setOnClickListener(this);
			
			loginOut = (Button) findViewById(R.id.login_out);
			loginOut.setOnClickListener(instance);
			Log.i("md", "haha"+sPreferences.getString("md", ""));
			if ( !"".equals( sPreferences.getString("md", ""))) {
				userName.setVisibility(View.GONE);
				passWord.setVisibility(View.GONE);
				register.setVisibility(View.GONE);
				login.setVisibility(View.GONE);
				loginOut.setVisibility(View.VISIBLE);
			} else {
				userName.setVisibility(View.VISIBLE);
				passWord.setVisibility(View.VISIBLE);
				register.setVisibility(View.VISIBLE);
				login.setVisibility(View.VISIBLE);
				loginOut.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void login() {
		try {
			MemberDto localMemberDto = new MemberDto();
			localMemberDto.setUsername(userName.getText().toString());
			localMemberDto.setPassword(passWord.getText().toString());
			PdaRequest<MemberDto> localPdaRequest = new PdaRequest<MemberDto>();
			localPdaRequest.setData(localMemberDto);
			String str = new Gson().toJson(localPdaRequest);
			RequestParams localRequestParams = new RequestParams();
			localRequestParams.put("jsonString", str);
			Log.i(TAG, "请求参数" + str);
			HttpUtil.post("checkPdaUserLogin.action", NetWork.getParamsList(localMemberDto, null, null), new JsonHttpResponseHandler() {
				public void onFailure(int paramAnonymousInt, Header[] paramAnonymousArrayOfHeader,
						String paramAnonymousString, Throwable paramAnonymousThrowable) {
					super.onFailure(paramAnonymousInt, paramAnonymousArrayOfHeader, paramAnonymousString,
							paramAnonymousThrowable);
					 try {
						Toast.makeText(instance, "网络异常", Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				public void onFinish() {
					super.onFinish();
					if (pro.isShowing()) {
						pro.cancel();
					}
				}

				public void onSuccess(int paramAnonymousInt, Header[] paramAnonymousArrayOfHeader,
						JSONObject paramAnonymousJSONObject) {
					super.onSuccess(paramAnonymousInt, paramAnonymousArrayOfHeader, paramAnonymousJSONObject);
					Log.i("MainActivity", "请求结果：" + paramAnonymousJSONObject.toString());
					try {
						PdaResponse<MemberDto> response = JsonToMemberDto.parserLoginJson(paramAnonymousJSONObject.toString());
						if (response.isSuccess()) {
							MemberDto md = response.getData();
							app.setMd(md);
//						List<CartItemDto> localList = app.getCartList();
//						if (localList.size() > 0) {
//							for (int i = 0; i < localList.size(); i++) {
//								localList.get(i).setMember(md);
//							}
//							app.setCartList(localList);
//						}
							sPreferences.edit().putString("md", StoreObject.saveOAuth(md)).commit();
//							sPreferences.edit().putString("username", instance.userName.getText().toString())
//								.putString("password", instance.passWord.getText().toString())
//								.putBoolean("islogin", true).commit();
							instance.finish();
						} else {
							
							Toast.makeText(instance, response.getMessage(), Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			return;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.login:
			if (userName.getText().toString().equals("")) {
				Toast.makeText(instance, "用户名不能为空", Toast.LENGTH_LONG).show();
			} else if (passWord.getText().toString().equals("")) {
				Toast.makeText(instance, "密码不能为空", Toast.LENGTH_LONG).show();
			} else {
				pro.show();
				login();
			}
			break;
		case R.id.register:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		case R.id.back:
			finish();
			break;
		case R.id.login_out:
			sPreferences.edit().remove("md").commit();
			finish();
			break;
		}
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_login);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		app = (MyApplication) getApplication();
		instance = this;
		pro = CustomProgressDialog.createDialog(instance);
		pro.setMessage("加载中，请稍后");
		sPreferences = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		findView();
	}
}
