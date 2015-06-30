package com.searun.shop.activity;

import java.util.ArrayList;

import com.searun.shop.R;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.MessageDto;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReadMessageActivity extends Activity implements OnClickListener{
	
	private static ReadMessageActivity instance;
	private SharedPreferences sp;
	private MemberDto md;
	private CustomProgressDialog pro;
	private Button back;
	private TextView titleTextView;
	private TextView addressor;
	private TextView contenTextView;
	private Button replyButton;
	private Button deleteButton;
	private Intent intent;
	private MessageDto messageDto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_read_message);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		init();
		findView();
	}
	private void init(){
		instance = this;
		intent = getIntent();
		sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		md = (MemberDto) StoreObject.readOAuth("md", sp);
		messageDto = (MessageDto) intent.getSerializableExtra("message");
		pro = CustomProgressDialog.createDialog(instance);
		pro.setMessage("加载中，请稍后");
//		pro.show();
	}
	private void findView(){
		back = ((Button) findViewById(R.id.back));
		back.setOnClickListener(this);
		TextView title = ((TextView) findViewById(R.id.title));
		title.setText("我的消息");
		findViewById(R.id.other).setVisibility(View.GONE);
		titleTextView = (TextView) findViewById(R.id.title_tv);
		titleTextView.setText(messageDto.getTitle());
		addressor = (TextView) findViewById(R.id.addressor);
		addressor.setText(messageDto.getFromMember() == null?"管理员":messageDto.getFromMember().getUsername());
		contenTextView = (TextView) findViewById(R.id.content);
		contenTextView.setText(messageDto.getContent());
		replyButton = (Button) findViewById(R.id.reply);
		replyButton.setOnClickListener(instance);
		deleteButton = (Button) findViewById(R.id.delete);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
			
		case R.id.reply:
			Intent intent = new Intent(instance, WriteMessageActivity.class);
			intent.putExtra("addressee", addressor.getText().toString());
			startActivity(intent);
			break;
		case R.id.delete:
			Toast.makeText(instance, "开发中", Toast.LENGTH_LONG).show();
			break;
		}
	}
}
