package com.searun.shop.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.adapter.FavouriteAdapter;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.ProductDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaPagination;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToProductList;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;
import com.searun.shop.view.XListView;
import com.searun.shop.view.XListView.IXListViewListener;

/**
 * 我的收藏
 * @author Administrator
 *
 */
public class FavouriteListActivity extends Activity implements OnClickListener,OnItemClickListener,OnItemLongClickListener,IXListViewListener{
	
	private static final String TAG = "FavouriteListActivity";
	private static FavouriteListActivity instance;
	private SharedPreferences sp;
	private MemberDto md;
	private CustomProgressDialog pro;
	private XListView favouriteList;
	private List<ProductDto> productList;
	private Button back;
	private TextView noProduct;
	private LinearLayout strollLayout;
	private Button stroll;
	private FavouriteAdapter myAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.actgivity_favouritelist);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		try {
			pp = new PdaPagination();
			
			sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
			instance = this;
			md = (MemberDto) StoreObject.readOAuth("md", sp);
			pro = CustomProgressDialog.createDialog(this);
			 mHandler = new Handler();
			findView();
			if (null == md) {
				startActivity(new Intent(instance, LoginActivity.class));
				finish();
			} else {
				pro.setMessage("加载中，请稍后");
				pro.show();
				productList = new ArrayList<ProductDto>();
				geneItems(true);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void findView(){
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(instance);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("我的收藏");
		findViewById(R.id.other).setVisibility(View.GONE);
		noProduct = (TextView) findViewById(R.id.no_product);
		strollLayout = (LinearLayout) findViewById(R.id.stroll_layout);
		stroll = (Button) findViewById(R.id.stroll);
		favouriteList = (XListView) findViewById(R.id.favourite_list);
		favouriteList.setPullRefreshEnable(true);
		favouriteList.setPullLoadEnable(true);
		favouriteList.setAutoLoadEnable(true);
		favouriteList.setXListViewListener(this);
		favouriteList.setRefreshTime(getTime());
	}
	 private String getTime() {
	        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
	    }
	private void getFavouriteList (PdaPagination page , final int i,final boolean isRefresh){
		page.setPageNumber(1);
		page.setAmount(10 * i);
		Log.i("iiiiiiiiiiiiii", i+"");
		try {
			HttpUtil.post("listFavourites.action", NetWork.getParamsList(null, md,page), new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i(TAG, response.toString());
					try {
						analyze(response,isRefresh);
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
	/**
	 * 解析json
	 * @param jsonObject
	 * @throws JSONException
	 */
	private void analyze(JSONObject jsonObject, boolean isRefresh) throws JSONException{
		try {
			PdaResponse<List<ProductDto>> response = JsonToProductList.parserLoginJson(jsonObject.toString());
			productList = response.getData();
//		favouriteList.setAdapter(new FavouriteAdapter(response.getData(), instance));
			if (productList .size() > 0) {
				favouriteList.setVisibility(View.VISIBLE);
				myAdapter = new FavouriteAdapter(productList, instance);
				favouriteList.setAdapter(myAdapter);
				if (isRefresh) {
					favouriteList.setSelection(0);
				} else {
					favouriteList.setSelection(productList.size());
				}
				favouriteList.setOnItemClickListener(instance);
				favouriteList.setOnItemLongClickListener(instance);
				Log.i(TAG+1, productList.size() + "");
			} else {
				noProduct.setVisibility(View.VISIBLE);
				strollLayout.setVisibility(View.VISIBLE);
				stroll.setOnClickListener(instance);
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

		case R.id.stroll:
			setResult(Activity.RESULT_OK);
			finish();
			break;
		}
	}
	private void deleteFavourite(ProductDto pd) {
		try {
			HttpUtil.post("deleteFavourite.action", NetWork.getParamsList(pd.getId(), md,null), new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("删除结果", response.toString());
					try {
						Toast.makeText(instance, response.optString("message"), Toast.LENGTH_LONG).show();
						geneItems(true);
						myAdapter.notifyDataSetChanged();
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(instance, GoodsInformation.class);
		intent.putExtra("pd", productList.get(position -1));
		startActivity(intent);
	}
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
		// TODO Auto-generated method stub
		favouriteList.setClickable(false);
		new AlertDialog.Builder(instance).setTitle("删除该收藏商品？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		}).setNeutralButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				try {
					Log.i("删除删除删除删除", position+ "");
					deleteFavourite(productList.get(position -1));
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).create().show();
		
		return true;
	}
	private Handler mHandler;
	 private int mIndex = 0;
	 private int mRefreshIndex = 0;
	private PdaPagination pp;
	
//	@Override
//	public void onWindowFocusChanged(boolean hasFocus) {
//		// TODO Auto-generated method stub
//		super.onWindowFocusChanged(hasFocus);
//		if (hasFocus) {
//			favouriteList.autoRefresh();
//		}
//	}
	private void geneItems(boolean isRefresh) {
		try {
			for (int i = 0; i != 1; ++i) {
				// items.add("refresh cnt " + (++start));
//				pp.setPageNumber( ++mIndex );
				getFavouriteList(pp ,++ mIndex, isRefresh);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	   private void onLoad() {
	        favouriteList.stopRefresh();
	        favouriteList.stopLoadMore();
	        favouriteList.setRefreshTime(getTime());
	    }
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		 mHandler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	                mIndex = ++mRefreshIndex;
	                productList.clear();
	                geneItems(true);
//	                mAdapter = new ArrayAdapter<String>(XListViewActivity.this, R.layout.vw_list_item,
//	                        items);
//	                mListView.setAdapter(mAdapter);
	                onLoad();
	            }
	        }, 2500);
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		 mHandler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	                geneItems(false);
	                myAdapter.notifyDataSetChanged();
	                onLoad();
	            }
	        }, 2500);
	}
	
	
}
