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
import com.searun.shop.adapter.AddressAdapter;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.ReceiverDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaPagination;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToReceiveList;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddressSelect extends Activity implements OnItemClickListener,OnClickListener{
	
	private static  final String TAG = "AddressSelect";
	private static AddressSelect instance;
	private SharedPreferences sp;
	private MemberDto md;
	private CustomProgressDialog pro;
	private PullToRefreshListView addressList;
	private List<ReceiverDto> receiverList;
	private Button back;
	private Button addNewAddress;
	private Intent intent;
	private String titleString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_address_select);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		instance = this;
		intent = getIntent();
		pro = CustomProgressDialog.createDialog(this);
		md = (MemberDto) StoreObject.readOAuth("md", sp);
		receiverList = new ArrayList<ReceiverDto>();
		findView();
		if (null == md) {
			startActivity(new Intent(instance, LoginActivity.class));
			finish();
		} else {
			pro.setMessage(getString(R.string.loading));
			pro.show();
		}
		
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		page = new PdaPagination();
		page.setAmount(10 * index);
		page.setPageNumber(1);
		getReceiversList(page);
	}
	private void findView(){
		findViewById(R.id.other).setVisibility(View.GONE);
		TextView title = (TextView) findViewById(R.id.title);
		titleString = intent.getStringExtra("title");
		title.setText(titleString);
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(instance);
		addressList = (PullToRefreshListView) findViewById(R.id.address_list);
		addressList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				geneItems(addressList,false);
				
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				geneItems(addressList,true);
				
			}
		});
		addNewAddress = (Button) findViewById(R.id.add_new_address);
		addNewAddress.setOnClickListener(instance);
	}
	private int index = 1;
	private PdaPagination page;
	@SuppressWarnings("rawtypes")
	private void geneItems(PullToRefreshAdapterViewBase view, boolean isUpToRefresh) {
		try {
			for (int i = 0; i != 1; ++i) {
				if (isUpToRefresh) {
					
					index ++;
					Log.i("index", index + "");
				}
				page.setAmount(10 * index);
				page.setPageNumber(1);
				getReceiversList(page);
				Thread.sleep(2000);
				view.onRefreshComplete();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取地址列表
	 */
	private void getReceiversList(PdaPagination page){
		try {
			HttpUtil.post("listReceivers.action", NetWork.getParamsList(null, md, page), new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					try {
						Log.i("地址：", response.toString());
						analyze(response);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
					try {
						Toast.makeText(instance, "网络异常`", Toast.LENGTH_LONG).show();
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
	/**
	 * 解析json
	 * @param jsonObject
	 * @throws JSONException
	 */
	private void analyze(JSONObject jsonObject) throws JSONException{
		try {
			PdaResponse<List<ReceiverDto>> response = JsonToReceiveList.parserLoginJson(jsonObject.toString());
			receiverList = response.getData();
			System.err.println( "地址个数：：：：：："+  receiverList.size());
//		favouriteList.setAdapter(new FavouriteAdapter(response.getData(), instance));
			
			if (receiverList != null) {
				addressList.setAdapter(new AddressAdapter(receiverList, instance));
				if (titleString.equals("地址管理")) {
					addNewAddress.setVisibility(View.VISIBLE);
					addressList.setClickable(false);
					addressList.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
							// TODO Auto-generated method stub
							Log.i("dianji", "1111");
							final String[] select = {"编辑该地址","删除该地址"};
							new AlertDialog.Builder(instance).setItems(select, new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									switch (which) {
									case 0:
										Intent intent = new Intent(instance, AddAddress.class);
										intent.putExtra("receiver", receiverList.get(position));
										startActivity(intent);
										break;

									case 1:
										dialog.cancel();
										new AlertDialog.Builder(instance).setTitle("确定删除？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
											
											@Override
											public void onClick(DialogInterface dialog, int which) {
												// TODO Auto-generated method stub
												dialog.cancel();
											}
										}).setNeutralButton("确定", new DialogInterface.OnClickListener() {
											
											@Override
											public void onClick(DialogInterface dialog, int which) {
												// TODO Auto-generated method stub
												//editReceiver
											}
										}).create().show();
										break;
									}
								}
							}).create().show();
						}
					});
				} else {
					addressList.setOnItemClickListener(instance);
				}
				Log.i(TAG+1, receiverList.size() + "");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			switch (v.getId()) {

			case R.id.back:
				finish();
				break;
			case R.id.add_new_address:
				startActivity(new Intent(instance, AddAddress.class));
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		try {
			System.err.println("点击111111111");
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("address", receiverList.get(position));
			intent.putExtras(bundle);
			setResult(Activity.RESULT_OK, intent);
			finish();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
