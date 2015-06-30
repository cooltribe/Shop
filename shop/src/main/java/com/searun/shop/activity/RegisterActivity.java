package com.searun.shop.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.searun.shop.R;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.MemberDto;
import com.searun.shop.entity.PdaRequest;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToMemberDto;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.util.Utility;
import com.searun.shop.view.CustomProgressDialog;
import com.searun.shop.view.MyEdittext;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends Activity implements View.OnClickListener {
	private CheckBox agreement;
	private Button back;
//	private EditText email;
//	private EditText password;
	private TextView read;
	private Button register;
//	private EditText repeat;
	private SharedPreferences sPreferences;
	private TextView title;
//	private EditText userName;
//	private EditText yanzhengma;
	private CustomProgressDialog pro;
	private MyEdittext userName;
	private MyEdittext password;
	private MyEdittext repeat;
	private MyEdittext email;

	private void findView() {
		pro = CustomProgressDialog.createDialog(this);
		pro.setMessage("加载中，请稍后");
		back = ((Button) findViewById(R.id.back));
		back.setOnClickListener(this);
		title = ((TextView) findViewById(R.id.title));
		title.setText("用户注册");
		findViewById(R.id.other).setVisibility(View.GONE);
		userName = (MyEdittext) findViewById(R.id.username);
		userName.setTv("用户名：");
		password = (MyEdittext) findViewById(R.id.password);
		password.setTv("密码：");
		password.setType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
		repeat = (MyEdittext) findViewById(R.id.repeat);
		repeat.setTv("重复密码：");
		repeat.setType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
		email = (MyEdittext) findViewById(R.id.email);
		email.setTv("Email");
		email.setType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		agreement = ((CheckBox) findViewById(R.id.agreement));
		read = ((TextView) findViewById(R.id.read));
		read.setOnClickListener(this);
		register = ((Button) findViewById(R.id.register_button));
		register.setOnClickListener(this);
	}

	private void getRegister() {
		MemberDto localMemberDto = new MemberDto();
		localMemberDto.setEmail(email.getEdittext().toString());
		localMemberDto.setUsername(userName.getEdittext().toString());
		localMemberDto.setPassword(password.getEdittext().toString());
		localMemberDto.setIsAgreeAgreement(Boolean.valueOf(agreement.isChecked()));
		PdaRequest<MemberDto> localPdaRequest = new PdaRequest<MemberDto>();
		localPdaRequest.setData(localMemberDto);
		String str = new Gson().toJson(localPdaRequest);
		RequestParams localRequestParams = new RequestParams();
		localRequestParams.put("jsonString", str);
		Log.i("请求参数", str);
		HttpUtil.post("registerUser.action", localRequestParams, new JsonHttpResponseHandler() {
			public void onFailure(int paramAnonymousInt, Header[] paramAnonymousArrayOfHeader, Throwable paramAnonymousThrowable,
					JSONObject paramAnonymousJSONObject) {
				super.onFailure(paramAnonymousInt, paramAnonymousArrayOfHeader, paramAnonymousThrowable, paramAnonymousJSONObject);
				try {
					Toast.makeText(RegisterActivity.this, "网络异常", Toast.LENGTH_LONG).show();
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

			public void onSuccess(int paramAnonymousInt, Header[] paramAnonymousArrayOfHeader, JSONObject paramAnonymousJSONObject) {
				super.onSuccess(paramAnonymousInt, paramAnonymousArrayOfHeader, paramAnonymousJSONObject);
				Log.i("TAG", "注册结果：" + paramAnonymousJSONObject.toString());
				try {
					PdaResponse<MemberDto> response = JsonToMemberDto.parserLoginJson(paramAnonymousJSONObject.toString());
					if (response.isSuccess()) {
						MemberDto md = response.getData();
						sPreferences.edit().putString("md", StoreObject.saveOAuth(md)).commit();
						RegisterActivity.this.finish();
						LoginActivity.instance.finish();
					} else {
						Toast.makeText(RegisterActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.register_button:
			Log.i("geshi", Utility.isEmail(email.getEdittext().toString()) + "");
			if (userName.getEdittext().toString().equals("")) {
				Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
			} else if (password.getEdittext().toString().equals("")) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			} else if (!repeat.getEdittext().toString().equals(this.password.getEdittext().toString())) {
				Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
			} else if (!Utility.isEmail(email.getEdittext().toString())) {
				Toast.makeText(this, "email格式错误", Toast.LENGTH_SHORT).show();
			} else if ( !agreement.isChecked()) {
				Toast.makeText(this, "不同意注册协议无法注册", Toast.LENGTH_SHORT).show();
			}else {
				pro.show();
				getRegister();
			}
			break;
		case R.id.read:
			startActivity(new Intent(this, AgreementActivity.class));
			break;
		case R.id.back:
			finish();
			break;
		}
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_register);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		sPreferences = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		findView();
	}
}
