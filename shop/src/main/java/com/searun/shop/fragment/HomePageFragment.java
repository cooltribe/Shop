package com.searun.shop.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView.OnEditorActionListener;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.searun.shop.R;
import com.searun.shop.activity.ArticleActivity;
import com.searun.shop.activity.GoodsInformation;
import com.searun.shop.activity.ProductListActivity;
import com.searun.shop.activity.ProductListActivity1;
import com.searun.shop.adapter.ItemHomepageAdapter;
import com.searun.shop.data.ArticleDto;
import com.searun.shop.data.HomeDataDto;
import com.searun.shop.data.ProductDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaPagination;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToHomeData;
import com.searun.shop.toobject.JsonToProductList;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.view.CircleFlowIndicator;
import com.searun.shop.view.CustomProgressDialog;
import com.searun.shop.view.ImageAdapter;
import com.searun.shop.view.MyGridView;
import com.searun.shop.view.VerticalScrollTextView;
import com.searun.shop.view.VerticalScrollTextView.ClickListener;
import com.searun.shop.view.ViewFlow;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

public class HomePageFragment extends Fragment implements OnClickListener, AdapterView.OnItemClickListener {
	private static final String TAG = "HomePageFragment";
	private static HomePageFragment instance;
	private Context context;
	private MyGridView hotGV;
	private TextView hotTV;
	private MyGridView bestGV;
	private TextView bestTV;
	private ViewFlow viewFlow;
	private CustomProgressDialog pro;
	private List<ProductDto> bestProducts;
	private List<ProductDto> hotProducts;
	private List<ProductDto> newProducts;
	private List<ArticleDto> articleDtos;
	private VerticalScrollTextView news;
	private DisplayImageOptions options;
	private ArticleDto articleDto;
	private ViewPager vp;
	
	private void initData(){
		context = getActivity();
		options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.mrpic_little)
					.showImageForEmptyUri(R.drawable.mrpic_little)
					.showImageOnFail(R.drawable.mrpic_little)
					.cacheInMemory(true)
					.cacheOnDisk(true)
					.considerExifParams(true)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.build();
		bestProducts = new ArrayList<ProductDto>();
		hotProducts = new ArrayList<ProductDto>();
		newProducts = new ArrayList<ProductDto>();
		articleDtos = new ArrayList<ArticleDto>();
		getHomeData();
	}
	private void getScreen(){
		DisplayMetrics displayMetrics = new DisplayMetrics();  
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);  

	}
	private void findView(View paramView) {
		pro = CustomProgressDialog.createDialog(context);
		pro.setMessage("加载中，请稍后");
		pro.setCancelable(false);
		pro.show();
		search = (AutoCompleteTextView) paramView.findViewById(R.id.search);
		search.setOnEditorActionListener(editorActionListener);
		news = (VerticalScrollTextView) paramView.findViewById(R.id.news);
		newsLayout = (LinearLayout) paramView.findViewById(R.id.tongzhi);
		
		/**
		 * 精品推荐
		 */
		bestTV = ((TextView) paramView.findViewById(R.id.best_tv));
		bestTV.getPaint().setFakeBoldText(true);
		bestTV.setText("精品推荐");
		bestGV = (MyGridView) paramView.findViewById(R.id.best);
		moreBest = (TextView) paramView.findViewById(R.id.best_more);
		moreBest.setOnClickListener(instance);
		
		/**
		 * 热销商品
		 */
		hotTV = ((TextView) paramView.findViewById(R.id.hot_tv));
		hotTV.setText("热销商品");
		hotTV.getPaint().setFakeBoldText(true);
		hotGV = (MyGridView) paramView.findViewById(R.id.hot);
		moreHot = (TextView) paramView.findViewById(R.id.hot_more);
		moreHot.setOnClickListener(instance);
		
		/**
		 * 新品推荐
		 */
		TextView newTV = (TextView) paramView.findViewById(R.id.new_tv);
		newTV.setText("新品推荐");
		newTV.getPaint().setFakeBoldText(true);
		newGV = (GridView) paramView.findViewById(R.id.new_gv);
		moreNew = (TextView) paramView.findViewById(R.id.new_more);
		moreNew.setOnClickListener(instance);
		
		TextView config_hidden = (TextView) paramView.findViewById(R.id.config_hidden);
		config_hidden.requestFocus();
		
		
	}


	public static HomePageFragment getInstance() {
		if (instance == null)
			instance = new HomePageFragment();
		return instance;
	}
	OnEditorActionListener editorActionListener = new OnEditorActionListener() {
		
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			// TODO Auto-generated method stub
//			getSearch();
			Intent intent = new Intent(getActivity(), ProductListActivity.class);
			intent.putExtra("keyword", search.getText().toString());
			startActivity(intent);
			return true;
		}
	};
	
	/**
	 * 获取搜索数据
	 */
	private void getSearch(){
		ProductDto pd = new ProductDto();
		pd.setMetaKeywords(search.getText().toString());
		PdaPagination pg = new PdaPagination();
		pg.setAmount(10);
		pg.setPageNumber(1);
		HttpUtil.post("searchProducts.action", NetWork.getParamsList(pd, null, pg), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("搜索结果", response.toString());
				try {
					PdaResponse<List<ProductDto>> pdaResponse = JsonToProductList.parserLoginJson(response.toString());
					if (pdaResponse.isSuccess()) {
						List<ProductDto> list = pdaResponse.getData();
						if (list.size() >  0) {
							
//							Bundle bundle = new Bundle();
//							bundle.putSerializable("productList", (Serializable) list);
							Intent intent = new Intent(getActivity(), ProductListActivity1.class);
							intent.putExtra("keyword", search.getText().toString());
							startActivity(intent);
						} else {
							Toast.makeText(getActivity(), "抱歉，没有该商品", Toast.LENGTH_LONG).show();
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
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
	 * 获取首页数据
	 */
	private void getHomeData(){
		HttpUtil.post("toIndexData.action", NetWork.getParamsList(null, null, null), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i(TAG +",toIndexData.action", response.toString());
//				SaveString.stringSave(TAG, getActivity(), response.toString());
				try {
					analyzeHomeData(response);
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
					Toast.makeText(context, "网络异常", Toast.LENGTH_LONG).show();
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
	
	private void analyzeHomeData(JSONObject jsonObject) throws Exception {
		PdaResponse<HomeDataDto> response = JsonToHomeData.parserLoginJson(jsonObject.toString());
		HomeDataDto homeDataDto = response.getData();
		
		
		 bestProducts = homeDataDto.getBestProducts();
		 Log.i("bestProducts", "" + bestProducts.size());
		 hotGV.setAdapter(new ItemHomepageAdapter(getActivity().getApplicationContext(), bestProducts, options));
		 hotGV.setOnItemClickListener(instance);
		 
		 
		 hotProducts = homeDataDto.getHotProducts();
		 bestGV.setAdapter(new ItemHomepageAdapter(getActivity().getApplicationContext(), hotProducts, options));
		 bestGV.setOnItemClickListener(instance);
		 
		 newProducts = homeDataDto.getNewProducts();
		 newGV.setAdapter(new ItemHomepageAdapter(getActivity().getApplicationContext(), newProducts, options));
		 newGV.setOnItemClickListener(instance);
		 
		 articleDtos = homeDataDto.getArticleDtos();
		 
		 news.setList(articleDtos);
		 news.updateUI();
		 news.setClickListener(listener);
		 
	}
		
	
	
	VerticalScrollTextView.ClickListener  listener = new ClickListener() {
		
		@Override
		public void onClickListener(final ArticleDto art) {
			// TODO Auto-generated method stub
//			Log.i(TAG, art.getTitle());
			newsLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					Toast.makeText(getActivity(), art.getTitle(), Toast.LENGTH_LONG).show();
					Intent intent = new Intent(getActivity(), ArticleActivity.class);
					intent.putExtra("article", art);
					startActivity(intent);
				}
			});
		}
	};
	private AutoCompleteTextView search;
	private GridView newGV;
	private TextView moreBest;
	private TextView moreHot;
	private TextView moreNew;
	private LinearLayout newsLayout;
	private void setBanner(View paramView) {
		viewFlow = ((ViewFlow) paramView.findViewById(R.id.viewflow));
		viewFlow.setAdapter(new ImageAdapter(getActivity()));
		viewFlow.setmSideBuffer(3);
		CircleFlowIndicator localCircleFlowIndicator = (CircleFlowIndicator) paramView.findViewById(R.id.viewflowindic);
		viewFlow.setFlowIndicator(localCircleFlowIndicator);
		viewFlow.setTimeSpan(4500L);
		viewFlow.setSelection(3000);
		viewFlow.startAutoFlowTimer();
	}

	
	
	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.fragment_homepage, paramViewGroup, false);
		initData();
		findView(localView);
		setBanner(localView);
		return localView;
	}

	public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
		Log.i("press", "click" + paramAdapterView.getId() +",best:" + R.id.best + ",hot:" + R.id.hot +",new_gv" + R.id.new_gv);
		Intent localIntent = new Intent(getActivity(), GoodsInformation.class);
		switch (paramAdapterView.getId()) {
		case R.id.best:
			ProductDto localGoodsData1 = hotProducts.get(paramInt);
			Bundle bundle = new Bundle();
			bundle.putSerializable("pd", localGoodsData1);
			localIntent.putExtras(bundle);
			break;
		case R.id.hot:
			System.err.println("点击");
			ProductDto localGoodsData = bestProducts.get(paramInt);
			Bundle localBundle = new Bundle();
			localBundle.putSerializable("pd", localGoodsData);
			localIntent.putExtras(localBundle);
			break;
		case R.id.new_gv:
			ProductDto localGoodsData2 = newProducts.get(paramInt);
			Bundle bundle2 = new Bundle();
			bundle2.putSerializable("pd", localGoodsData2);
			localIntent.putExtras(bundle2);
			break;
		}
		startActivity(localIntent);

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), ProductListActivity.class);
		switch (v.getId()) {
		case R.id.best_more:
			intent.putExtra("command", "BestProduct");
			break;

		case R.id.hot_more:
			intent.putExtra("command", "HotProduct");
			break;
		case R.id.new_more:
			intent.putExtra("command", "NewProduct");
			break;
		}
		startActivity(intent);
	}
}
