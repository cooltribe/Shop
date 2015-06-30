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
import android.widget.TextView;
import android.widget.Toast;

public class AddressManage extends Activity implements OnClickListener ,OnItemClickListener{
	private static  final String TAG = "AddressManage";
	private static AddressManage instance;
	private SharedPreferences sp;
	private MemberDto md;
	private CustomProgressDialog pro;
	private PullToRefreshListView addressList;
	private List<ReceiverDto> receiverList;
	private Button addNewAddress;
	private Button back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_address_manage);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		instance = this;
		md = (MemberDto) StoreObject.readOAuth("md", sp);
		pro = CustomProgressDialog.createDialog(this);
		pro.setMessage(getString(R.string.loading)).show();;
		receiverList = new ArrayList<ReceiverDto>();
		findView();
		
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
		try {
			findViewById(R.id.other).setVisibility(View.GONE);
			TextView title = (TextView) findViewById(R.id.title);
			title.setText("地址管理");
			back = (Button) findViewById(R.id.back);
			back.setOnClickListener(instance);
			addressList = (PullToRefreshListView) findViewById(R.id.address_list);
			addressList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

				@Override
				public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
					// TODO Auto-generated method stub
//					geneItems(false);
					geneItems(addressList);
					
				}

				@Override
				public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
					// TODO Auto-generated method stub
//					initLayout(null, page ,drawable);
					geneItems(addressList);
					
				}
			});
			
			addressList.setOnItemClickListener(instance);
			addNewAddress = (Button) findViewById(R.id.add_new_address);
			addNewAddress.setOnClickListener(instance);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private int index = 1;
	private PdaPagination page;
	@SuppressWarnings("rawtypes")
	private void geneItems(PullToRefreshAdapterViewBase view) {
		try {
			for (int i = 0; i != 1; ++i) {
				index ++;
				Log.i("index", index + "");
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
			case R.id.add_new_address:
				startActivity(new Intent(instance, AddAddress.class));
				break;

			case R.id.back:
				finish();
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
		Log.i("dianji", "1111");
		final String[] select = {"编辑该地址","删除该地址"};
	}
}
