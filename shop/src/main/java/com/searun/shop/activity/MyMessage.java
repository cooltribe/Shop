package com.searun.shop.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.adapter.MessageAdapter;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.MessageDto;
import com.searun.shop.data.OrderDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaPagination;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToMessageList;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MyMessage extends Activity implements OnClickListener,OnCheckedChangeListener,OnItemClickListener{
	private static MyMessage instance;
	private MemberDto md;
	private CustomProgressDialog pro;
	private SharedPreferences sp;
	private RadioGroup topRadioGroup;
	private Button back;
	private Button writeMessage;
	private List<MessageDto> messageList;
	private PullToRefreshListView messageListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_mymessage);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		init();
		findView();
		MessageDto messageDto = new MessageDto();
		messageDto.setCommand("InMessages");
		getMessage(messageDto,page);
		refresh(messageListView, messageDto);
	}
	private void init(){
		instance = this;
		messageList = new ArrayList<MessageDto>();
		sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		md = (MemberDto) StoreObject.readOAuth("md", sp);
		pro = CustomProgressDialog.createDialog(instance);
		pro.setMessage("加载中，请稍后");
		pro.show();
		page = new PdaPagination();
		page.setAmount(10 * index);
		page.setPageNumber(1);
	}
	private void findView(){
		back = ((Button) findViewById(R.id.back));
		back.setOnClickListener(this);
		TextView title = ((TextView) findViewById(R.id.title));
		title.setText("我的消息");
		writeMessage = (Button) findViewById(R.id.other);
		writeMessage.setBackgroundResource(R.drawable.icon_write_message);
		writeMessage.setOnClickListener(instance);
		topRadioGroup = (RadioGroup) findViewById(R.id.top_rg);
		topRadioGroup.setOnCheckedChangeListener(instance);
		RadioButton inbox = (RadioButton) findViewById(R.id.inbox);
		RadioButton outbox = (RadioButton) findViewById(R.id.outbox);
		RadioButton draftBox = (RadioButton) findViewById(R.id.draftbox);
		messageListView = (PullToRefreshListView) findViewById(R.id.message_lv);
		
	}
	
	private void refresh(final PullToRefreshListView view ,final MessageDto messageDto){
		view.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				geneItems(view,messageDto,false);
				
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				geneItems(view,messageDto,true);
				
			}
		});
	}
	private int index = 1;
	private PdaPagination page;
	@SuppressWarnings("rawtypes")
	private void geneItems(PullToRefreshAdapterViewBase view, MessageDto messageDto ,boolean isUpToRefresh) {
		try {
			for (int i = 0; i != 1; ++i) {
				if (isUpToRefresh) {
					
					index ++;
					Log.i("index", index + "");
				}
				page.setAmount(10 * index);
				page.setPageNumber(1);
				getMessage(messageDto, page);
				Thread.sleep(2000);
				view.onRefreshComplete();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void getMessage(MessageDto messageDto,PdaPagination page){
		
		HttpUtil.post("listMessages.action", NetWork.getParamsList(messageDto,md,page), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("收件箱", response.toString());
				try {
					PdaResponse<List<MessageDto>> pdaResponse = JsonToMessageList.parserLoginJson(response.toString());
					messageList = pdaResponse.getData();
					messageListView.setAdapter(new MessageAdapter(messageList, instance));
					messageListView.setOnItemClickListener(instance);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Toast.makeText(instance, "网络异常", Toast.LENGTH_LONG).show();
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
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		case R.id.other:
			startActivity(new Intent(instance, WriteMessageActivity.class));
			break;
		}
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		MessageDto messageDto = new MessageDto();
		pro.show();
		switch (checkedId) {
		case R.id.inbox:
			messageDto.setCommand("InMessages");
			break;

		case R.id.outbox:
			messageDto.setCommand("OutMessages");
			break;
		case R.id.draftbox:
			messageDto.setCommand("DreaftMessages");
			break;
		}
		getMessage(messageDto,page);
		refresh(messageListView, messageDto);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		MessageDto messageDto = messageList.get(position);
		Intent intent = new Intent(instance, ReadMessageActivity.class);
		intent.putExtra("message", messageDto);
		startActivity(intent);
	}
}
