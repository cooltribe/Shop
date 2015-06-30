package com.searun.shop.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.adapter.OrderChildAdapter;
import com.searun.shop.adapter.logisticsAdapter;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.KuaidiTracking;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.OrderDto;
import com.searun.shop.data.OrderItemDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToOrder;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.SaveString;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LogisticsActivity extends Activity implements OnClickListener{

	private static LogisticsActivity instance;
	private ListView orderListView;
	private ListView logisticsListView;
	private Intent intent;
	private CustomProgressDialog pro;
	private SharedPreferences sp;
	private MemberDto md;
	private List<OrderItemDto> orderList;
	private List<KuaidiTracking> logisticsList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_logistics);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		init();
		findView();
		getOrder();
	}
	private void init(){
		intent = getIntent();
		instance = this;
		pro = CustomProgressDialog.createDialog(instance);
		sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		pro.setMessage("加载中，请稍后");
		pro.show();
		md = (MemberDto) StoreObject.readOAuth("md", sp);
		orderList = new ArrayList<OrderItemDto>();
		logisticsList = new ArrayList<KuaidiTracking>();
	}
	private void findView(){
		Button back = (Button) findViewById(R.id.back);
		back.setOnClickListener(instance);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("物流跟踪");
		findViewById(R.id.other).setVisibility(View.GONE);
		orderListView = (ListView) findViewById(R.id.order_list);
		logisticsListView = (ListView) findViewById(R.id.logistics_list);
	}
	private void getOrder(){
		HttpUtil.post("getLogisticsInfo.action", NetWork.getParamsList(intent.getStringExtra("orderId"), md,null), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("物流跟踪", response.toString());
				try {
					analyze(response);
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
	private void analyze(JSONObject response){
		try {
			PdaResponse<OrderDto> pdaResponse = JsonToOrder.parserLoginJson(response.toString());
			OrderDto orderDto = pdaResponse.getData();
			orderList = orderDto.getOrderItems();
			
			logisticsList = orderDto.getTrackings();
			Log.i("size", logisticsList.size()+"");
			Collections.reverse(logisticsList);
			orderListView.setAdapter(new OrderChildAdapter(orderList, instance,pro,null,false,false));
			if (logisticsList.isEmpty()) {
				Toast.makeText(instance, "暂无物流信息", Toast.LENGTH_LONG).show();
			} else {
				
				logisticsListView.setAdapter(new logisticsAdapter(logisticsList, instance));
			}
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

		default:
			break;
		}
	}
}
