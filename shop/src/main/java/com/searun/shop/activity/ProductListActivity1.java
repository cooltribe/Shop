package com.searun.shop.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.searun.shop.R;
import com.searun.shop.adapter.ProductGridAdapter;
import com.searun.shop.adapter.ProductListAdapter;
import com.searun.shop.application.MyApplication;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.ProductDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaPagination;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToProductList;
import com.searun.shop.util.DateChange;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.SaveString;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TextView;
import android.widget.Toast;

public class ProductListActivity1 extends Activity implements OnClickListener ,OnItemClickListener,OnCheckedChangeListener{
	private static final String TAG = "ProductListActivity";
	private Intent intent;
	private ListView productList;
	private List<ProductDto> list;
	private TextView title;
	private ImageView back;
	private GridView productGrid;
	private DisplayImageOptions options;
	private RadioGroup rad;
	private CustomProgressDialog pro;
	private MyApplication app;
	private SharedPreferences sp;
	private MemberDto md;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_productlist1);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.index_titlebar);
		options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.mrpic_little)
					.showImageForEmptyUri(R.drawable.mrpic_little)
					.cacheInMemory(true)
					.cacheOnDisk(true)
					.build();
		intent = getIntent();
		sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		app = (MyApplication) getApplication();
		md = (MemberDto) StoreObject.readOAuth("md", sp);
		list = new ArrayList<ProductDto>();
		pro = CustomProgressDialog.createDialog(this);
		pro.setMessage("加载中，请稍后");
		pro.show();
		findView();
	}
	private void findView(){
		try {
			search = (AutoCompleteTextView) findViewById(R.id.search);
			search.setOnEditorActionListener(editorActionListener);
			productList = (ListView) findViewById(R.id.product_list);
			productGrid = (GridView) findViewById(R.id.product_grid);
			initRadio();
//		title = (TextView) findViewById(R.id.title);
//		title.setText(intent.getExtras().getString("name"));
//		findViewById(R.id.other).setVisibility(View.GONE);
			back = (ImageView) findViewById(R.id.logo);
			back.setImageResource(R.drawable.btn_back);
			back.setOnClickListener(this);
			
			priceText = (TextView) findViewById(R.id.price_text);
			
			simpleLayout = (LinearLayout) findViewById(R.id.simple_layout);
			simpleLayout.setOnClickListener(this);
			
			priceLayout = (LinearLayout) findViewById(R.id.price_layout);
			priceLayout.setOnClickListener(this);
			
			createLayout = (LinearLayout) findViewById(R.id.create_layout);
			createLayout.setOnClickListener(this);
			
			drawable = getResources().getDrawable(R.drawable.icon_px);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			initLayout(null,drawable);
			
			productGrid.setOnItemClickListener(this);
			productList.setOnItemClickListener(this);
			findViewById(R.id.simple_bottom).setVisibility(View.VISIBLE);
			findViewById(R.id.price_bottom).setVisibility(View.INVISIBLE);
			findViewById(R.id.create_bottom).setVisibility(View.INVISIBLE);
			
			TextView config_hidden = (TextView) findViewById(R.id.config_hidden);
			config_hidden.requestFocus();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

		OnEditorActionListener editorActionListener = new OnEditorActionListener() {
		
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			// TODO Auto-generated method stub
			product.setMetaKeywords(search.getText().toString());
			page = new PdaPagination();
			page.setAmount(10);
			page.setPageNumber(1);
			list.clear();
			getSearch(product, page, drawable);
			return true;
		}
	};
	
	/**
	 * 排列方式
	 * @param orderType 排序方式
	 */
	private void initLayout(String orderType, Drawable drawable){
		try {
			product = new ProductDto();
			product.setOrderType(orderType);
			if ( null != intent.getExtras().getString("id") ) {
				
				page = new PdaPagination();
				page.setAmount(10);
				page.setPageNumber(1);
				product.setCategoryId(intent.getExtras().getString("id"));
				list.clear();
				getProductJson(product, page,drawable);
			} else if (null != intent.getExtras().getString("keyword")) {
				product.setMetaKeywords(intent.getExtras().getString("keyword"));
				page = new PdaPagination();
				page.setAmount(10);
				page.setPageNumber(1);
				list.clear();
				getSearch(product, page, drawable);
			} else if (null != intent.getExtras().getString("command")) {
				product.setCommand(intent.getExtras().getString("command"));
				page = new PdaPagination();
				page.setAmount(10);
				page.setPageNumber(1);
				list.clear();
				getMore(product, page, drawable);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *  查询产品列表(热卖推荐，精品推荐更多接口，分页)
	 * @param command
	 */
	private void getMore(ProductDto product,PdaPagination page, final Drawable drawable){
		
		HttpUtil.post("listProducts.action", NetWork.getParamsList(product,null,page), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("分类结果", response.toString());
				try {
					getProductList(response);
					priceText.setCompoundDrawables(null, null, drawable, null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
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
	
	
	/**
	 * 获取搜索数据
	 */
	private void getSearch(ProductDto pd, PdaPagination pp, final Drawable drawable){
		HttpUtil.post("searchProducts.action", NetWork.getParamsList(pd, null, pp), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("搜索结果", response.toString());
				try {
				getProductList(response);
				priceText.setCompoundDrawables(null, null, drawable, null);
				
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
					Toast.makeText(ProductListActivity1.this, "网络异常", Toast.LENGTH_LONG).show();
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
	private void initRadio(){
		rad = (RadioGroup) findViewById(R.id.radio_group);
		rad.setOnCheckedChangeListener(this);
		productList.setVisibility(View.GONE);
	}
	
	private void getProductList(JSONObject o){
		try {
			
			PdaResponse<List<ProductDto>> response = JsonToProductList.parserLoginJson(o.toString());
			list = response.getData();
			if (list.size() > 0) {
				
				gridAdapter = new ProductGridAdapter(list, this, options, app, md);
				listAdapter = new ProductListAdapter(list, this, options, app ,md);
				productList.setAdapter(listAdapter);
				productGrid.setAdapter(gridAdapter);
				gridAdapter.notifyDataSetChanged();
				listAdapter.notifyDataSetChanged();
			} else {
				Toast.makeText(ProductListActivity1.this, "抱歉，没有该商品", Toast.LENGTH_LONG).show();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	ProductListAdapter.cartListener listener = new ProductListAdapter.cartListener() {
		
		@Override
		public void cartListenerClick(ProductDto pd) {
			// TODO Auto-generated method stub
			
		}
	};
	private void getProductJson(ProductDto pcd ,PdaPagination page, final Drawable drawable){
		try {
			HttpUtil.post("listProductsByCategory.action", NetWork.getParamsList(pcd,null,page), new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("商品列表详情", response.toString());
					getProductList(response);
					priceText.setCompoundDrawables(null, null, drawable, null);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, responseString, throwable);
					try {
						Log.i("错误信息", throwable.toString());
						Toast.makeText(ProductListActivity1.this, "网络异常", Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					super.onFinish();
					System.err.println("显示：" + pro.isShowing());
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
		
		try {
			switch (v.getId()) {
			case R.id.logo:
				finish();
				break;

			case R.id.simple_layout:
				pro.show();
				System.err.println("xianshi1111111111111111"+ pro.isShowing());
				initLayout(null,null);
				findViewById(R.id.simple_bottom).setVisibility(View.VISIBLE);
				findViewById(R.id.price_bottom).setVisibility(View.INVISIBLE);
				findViewById(R.id.create_bottom).setVisibility(View.INVISIBLE);
				break;
			case R.id.price_layout:
				boolean priceOrder = sp.getBoolean("priceorder", false);
				System.err.println("1"+ priceOrder);
				pro.show();
				if (!priceOrder) {
					
					Drawable drawable = getResources().getDrawable(R.drawable.icon_px_jx);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					initLayout("priceAsc",drawable);
					sp.edit().putBoolean("priceorder", true).commit();
					
				} else {
					Drawable drawable = getResources().getDrawable(R.drawable.icon_px_sx);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					initLayout("priceDesc",drawable);
					sp.edit().putBoolean("priceorder", false).commit();
				}
				findViewById(R.id.simple_bottom).setVisibility(View.INVISIBLE);
				findViewById(R.id.price_bottom).setVisibility(View.VISIBLE);
				findViewById(R.id.create_bottom).setVisibility(View.INVISIBLE);
				break;
			case R.id.create_layout:
				pro.show();
				initLayout("dateAsc",null);
				findViewById(R.id.simple_bottom).setVisibility(View.INVISIBLE);
				findViewById(R.id.price_bottom).setVisibility(View.INVISIBLE);
				findViewById(R.id.create_bottom).setVisibility(View.VISIBLE);
				break;
			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private LinearLayout simpleLayout;
	private LinearLayout priceLayout;
	private LinearLayout createLayout;
	private ProductDto product;
	private ProductGridAdapter gridAdapter;
	private ProductListAdapter listAdapter;
	private TextView priceText;
	private PdaPagination page;
	private AutoCompleteTextView search;
	private Drawable drawable;
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		try {
			switch (parent.getId()) {
			case R.id.product_list:
				Intent intent = new Intent(ProductListActivity1.this, GoodsInformation.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("pd", list.get(position));
				intent.putExtras(bundle);
				startActivity(intent);
				
				break;

			case R.id.product_grid:
				Intent intent1 = new Intent(this, GoodsInformation.class);
				Bundle bundle1 = new Bundle();
				bundle1.putSerializable("pd", list.get(position));
				intent1.putExtras(bundle1);
				startActivity(intent1);
				
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		try {
			switch (checkedId) {
			case R.id.list:
				productList.setVisibility(View.VISIBLE);
				productGrid.setVisibility(View.GONE);
				break;

			case R.id.grid:
				productList.setVisibility(View.GONE);
				productGrid.setVisibility(View.VISIBLE);
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
