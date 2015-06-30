package com.searun.shop.activity;

import java.util.ArrayList;
import java.util.List;

import com.searun.shop.R;
import com.searun.shop.adapter.WriteJudgeAdapter;
import com.searun.shop.data.OrderItemDto;
import com.searun.shop.data.ProductDto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class WriteJudgeListActivity extends Activity implements OnClickListener{

	private Button backButton;
	private ListView judgeListView;
	private Intent intent;
	private ArrayList<OrderItemDto> productDtoList;
	private String orderId;
	private WriteJudgeAdapter myAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_judge_list);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		init();
		findView();
	}
	@SuppressWarnings("unchecked")
	private void init(){
		intent = getIntent();
		productDtoList = (ArrayList<OrderItemDto>) intent.getSerializableExtra("judgeList");
		orderId = intent.getStringExtra("orderId");
	}
	private void findView(){
		backButton = (Button) findViewById(R.id.back);
		backButton.setOnClickListener(this);
		TextView titleTextView = (TextView) findViewById(R.id.title);
		titleTextView.setText("商品评价");
		findViewById(R.id.other).setVisibility(View.GONE);
		judgeListView = (ListView) findViewById(R.id.judge_list);
		myAdapter = new WriteJudgeAdapter(productDtoList, this, orderId);
		judgeListView.setAdapter(myAdapter);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myAdapter.notifyDataSetChanged();
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
