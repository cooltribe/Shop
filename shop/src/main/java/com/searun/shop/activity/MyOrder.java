package com.searun.shop.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
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

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.adapter.OrderAdapter;
import com.searun.shop.adapter.OrderAdapter.Confirm;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.application.MyApplication;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.OrderDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaPagination;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToOrderList;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyOrder extends Activity implements OnClickListener, OnItemClickListener, OnCheckedChangeListener ,Confirm{

	private static MyOrder instance;
	private MyApplication app;
	private CustomProgressDialog pro;
	private SharedPreferences sp;
	private MemberDto md;
	private PullToRefreshListView orderListView;
	private Button back;
	private List<OrderDto> orderList;
	private Intent intent;
	private OrderDto orderDto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_myorder);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		init();
		findView();
		getMyOrder(orderDto,page);
		refresh(orderListView, orderDto);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getMyOrder(orderDto,page);
		refresh(orderListView, orderDto);
	}
	private void findView() {
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(instance);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("我的订单");
		RadioGroup rg = (RadioGroup) findViewById(R.id.order_rg);
		rg.setOnCheckedChangeListener(instance);
		RadioButton all = (RadioButton) findViewById(R.id.all);
		RadioButton pay = (RadioButton) findViewById(R.id.pay);
		RadioButton send = (RadioButton) findViewById(R.id.send);
		RadioButton takeOver = (RadioButton) findViewById(R.id.take_over);
		judge = (RadioButton) findViewById(R.id.judge);
		int tag = intent.getExtras().getInt("tag");
		findViewById(R.id.other).setVisibility(View.GONE);
		orderListView = (PullToRefreshListView) findViewById(R.id.order_list);
		switch (tag) {
		case 1:
			all.setChecked(true);
			break;

		case 2:
			pay.setChecked(true);
			break;
		case 3:
			send.setChecked(true);
			break;
		case 4:
			takeOver.setChecked(true);
			break;
		case 5:
			judge.setChecked(true);
			break;
		case 6:
			break;
		}
	}
	private void refresh(final PullToRefreshListView view ,final OrderDto orderDto){
		view.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
               
				geneItems(view,orderDto,false);
				
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				geneItems(view,orderDto,true);
				
			}
		});
		initIndicator(view);
	}
	/**
	 * 设置上拉和下拉的属性
	 * @param view
	 */
	private void initIndicator(PullToRefreshListView view)
	{
		ILoadingLayout startLabels = view.getLoadingLayoutProxy(true, false);
		String label = DateUtils.formatDateTime(  
                getApplicationContext(),  
                System.currentTimeMillis(),  
                DateUtils.FORMAT_SHOW_TIME  
                        | DateUtils.FORMAT_SHOW_DATE  
                        | DateUtils.FORMAT_ABBREV_ALL);  
//		startLabels.setPullLabel("你可劲拉，拉...");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("玩命刷新中，请稍安勿躁...");// 刷新时
//		startLabels.setReleaseLabel("你敢放，我就敢刷新...");// 下来达到一定距离时，显示的提示
		startLabels.setLastUpdatedLabel(label);  // 显示最后更新的时间  
		
		ILoadingLayout endLabels = view.getLoadingLayoutProxy(false, true);
//		endLabels.setPullLabel("你可劲拉，拉2...");// 刚下拉时，显示的提示
		endLabels.setRefreshingLabel("玩命刷新中，请稍安勿躁...");// 刷新时
//		endLabels.setReleaseLabel("你敢放，我就敢刷新2...");// 下来达到一定距离时，显示的提示
	}
	private void init() {
		app = (MyApplication) getApplication();
		instance = this;
		pro = CustomProgressDialog.createDialog(instance);
		sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		pro.setMessage("加载中，请稍后");
		md = (MemberDto) StoreObject.readOAuth("md", sp);
		page = new PdaPagination();
		page.setAmount(10 * index);
		page.setPageNumber(1);
		orderList = new ArrayList<OrderDto>();
		intent = getIntent();
		orderDto = null == intent.getSerializableExtra("order")? new OrderDto() : (OrderDto) intent.getSerializableExtra("order");
		
		if (null == md) {
			startActivity(new Intent(instance, LoginActivity.class));
			finish();
		} else {
			
			pro.show();
		}
	}
	private int index = 1;
	private PdaPagination page;
	private RadioButton judge;
	private OrderAdapter adapter;
	@SuppressWarnings("rawtypes")
	private void geneItems(PullToRefreshAdapterViewBase view, OrderDto orderDto ,boolean isUpToRefresh) {
		try {
			for (int i = 0; i != 1; ++i) {
				if (isUpToRefresh) {
					
					index ++;
					Log.i("index", index + "");
				}
				page.setAmount(10 * index);
				page.setPageNumber(1);
				getMyOrder(orderDto, page);
				Thread.sleep(2000);
				view.onRefreshComplete();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void getMyOrder(OrderDto orderDto,PdaPagination pp) {
		try {
			HttpUtil.post("listOrders.action", NetWork.getParamsList(orderDto, md, pp), new JsonHttpResponseHandler() {
				

				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("我的订单列表", response.toString());
					try {
						PdaResponse<List<OrderDto>> pdaResponse = JsonToOrderList.parserLoginJson(response.toString());
						if (orderList != null) {
							orderList.clear();
						}
						orderList = pdaResponse.getData();
						adapter = new OrderAdapter(orderList, instance, md, pro);
						adapter.setConfirm(instance);
						orderListView.setAdapter(adapter);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		// orderList.get(position)
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		OrderDto orderDto = new OrderDto();
		pro.show();
		switch (checkedId) {
		case R.id.all:
			
			break;

		case R.id.pay://待付款
			orderDto.setPaymentStatus("unpaid");
			break;
		case R.id.send://待发货
			orderDto.setShippingStatus("unshipped");
			break;
		case R.id.take_over://待收货
			orderDto.setShippingStatus("shipped");
			break;
		case R.id.judge://待评价
			orderDto.setOrderStatus("received");
			break;
			
		}
		getMyOrder(orderDto,page);
		refresh(orderListView, orderDto);
	}

	@Override
	public void onConfirm(int tag) {
		// TODO Auto-generated method stub
		OrderDto orderDto = new OrderDto();
		switch (tag) {
		case 5:
			orderDto.setOrderStatus("received");
			judge.setChecked(true);
			break;

		default:
			break;
		}
		getMyOrder(orderDto,page);
		refresh(orderListView, orderDto);
	}
}
