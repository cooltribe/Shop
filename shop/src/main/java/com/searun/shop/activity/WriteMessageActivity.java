package com.searun.shop.activity;

import org.apache.http.Header;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.MessageDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WriteMessageActivity extends Activity implements OnClickListener{
	
	private Button back;
	private EditText addresseeEditText;
	private EditText titlEditText;
	private EditText contentEditText;
	private Button sendButton;
	private Button saveButton;
	private SharedPreferences sp;
	private MemberDto md;
	private CustomProgressDialog pro;
	private static WriteMessageActivity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_write_message);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);
		init();
		findView();
	}
	private void init(){
		instance = this;
		sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		md = (MemberDto) StoreObject.readOAuth("md", sp);
		pro = CustomProgressDialog.createDialog(instance);
		pro.setMessage("加载中，请稍后");
		
	}
	private void findView(){
		back = ((Button) findViewById(R.id.back));
		back.setOnClickListener(instance);
		
		TextView title = ((TextView) findViewById(R.id.title));
		title.setText("写消息");
		findViewById(R.id.other).setVisibility(View.GONE);
		addresseeEditText = (EditText) findViewById(R.id.addressee);
		if (null != getIntent()) {
			addresseeEditText.setText(getIntent().getStringExtra("addressee"));
		}
		titlEditText = (EditText) findViewById(R.id.title_message);
		contentEditText = (EditText) findViewById(R.id.content);
		
		sendButton = (Button) findViewById(R.id.send);
		sendButton.setOnClickListener(instance);
		
		saveButton = (Button) findViewById(R.id.save);
		saveButton.setOnClickListener(instance);
	}
	private void operateMessage(MessageDto messageDto){
		HttpUtil.post("saveMessage.action", NetWork.getParamsList(messageDto, md, null), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("写信息", response.toString());
				try {
					if (response.optBoolean("success")) {
						titlEditText.setText("");
						contentEditText.setText("");
					}
					Toast.makeText(instance, response.optString("message"), Toast.LENGTH_LONG).show();
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		MessageDto messageDto = new MessageDto();
		messageDto.setTitle(titlEditText.getText().toString());
		messageDto.setContent(contentEditText.getText().toString());
		messageDto.setToMemberUsername(addresseeEditText.getText().toString());
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		case R.id.send:
			messageDto.setIsSaveDraftbox(false);
			pro.show();
			operateMessage(messageDto);
			break;
		case R.id.save:
			messageDto.setIsSaveDraftbox(true);
			pro.show();
			operateMessage(messageDto);
			break;
		}
		
	}
}
