package com.searun.shop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.searun.shop.R;
import com.searun.shop.activity.ProductListActivity;
import com.searun.shop.activity.ProductListActivity1;
import com.searun.shop.adapter.ClassificationLeftAdapter;
import com.searun.shop.data.ProductCategoryDto;
import com.searun.shop.data.ProductDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaPagination;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToProductList;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.SaveString;
import com.searun.shop.view.CustomProgressDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClassificationFragment extends Fragment implements AdapterView.OnItemClickListener {
	private static final String TAG = "ClassificationFragment";
	private static ClassificationFragment instance;
	private FragmentManager fm;
	private ListView sortListView;
	private List<ProductCategoryDto> list = new ArrayList<ProductCategoryDto>();;
	private DisplayImageOptions options;
	private ClassificationLeftAdapter clAdapter;
	private CustomProgressDialog pro;
	private AutoCompleteTextView search;
	private void findView(View paramView) {
		fm = getChildFragmentManager();
		sortListView = ((ListView) paramView.findViewById(R.id.sort_index));
		
		search = (AutoCompleteTextView) paramView.findViewById(R.id.search);
		search.setOnEditorActionListener(editorActionListener);
		
		options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.mrpic)
					.showImageForEmptyUri(R.drawable.mrpic)
					.cacheInMemory(false)
					.cacheOnDisk(true)
					.build();
		
		pro = CustomProgressDialog.createDialog(getActivity());
		pro.setMessage("加载中，请稍后");
		pro.show();
	}

		OnEditorActionListener editorActionListener = new OnEditorActionListener() {
		
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			// TODO Auto-generated method stub
			getSearch();
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
							Intent intent = new Intent(getActivity(), ProductListActivity.class);
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
	
	public static ClassificationFragment getInstance() {
		if (instance == null) {
			instance = new ClassificationFragment();
		}
		return instance;
	}

	private List<ProductCategoryDto> getList(JSONArray array) {
		
		List<ProductCategoryDto> childList = new ArrayList<ProductCategoryDto>();
		try {
//			JSONArray array = response.optJSONArray("children");
			System.err.println("array长度" + array.length());
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = (JSONObject) array.get(i);
				ProductCategoryDto pcd = new ProductCategoryDto();
				pcd.setId(object.optString("id"));
				pcd.setImage(object.optString("image"));
				pcd.setMetaDescription(object.optString("metaDescription"));
				pcd.setMetaKeywords(object.optString("metaKeywords"));
				pcd.setName(object.optString("name"));
				pcd.setOrderList(object.optInt("orderList"));
				pcd.setPath(object.optString("path"));
				JSONArray array2 = object.optJSONArray("children");
				if (null != array2) {
					pcd.setChildren(getList(array2));
				} else {
					pcd.setChildren(null);
				}
				childList.add(pcd);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return childList;
		
	}
	
	
	private void getSort() {
		
		HttpUtil.post("listProductCategory.action", NetWork.getParamsList(null, null, null), new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i(TAG, response.toString());
				try {
					if (response.optBoolean("success")) {
					JSONArray array = response.optJSONArray("data");
					System.err.println("array总长度" + array.length());
					list = getList(array);
					fm.beginTransaction().add(R.id.sort_fg, new ClassificationChildFragment(list.get(0))).commitAllowingStateLoss();
					clAdapter = new ClassificationLeftAdapter(list, getActivity(),options);
					sortListView.setAdapter(clAdapter);
					sortListView.setOnItemClickListener(ClassificationFragment.this);
					System.err.println("111111111111111:" + list.size());
					} else {
						Toast.makeText(getActivity(), response.optString("message"), Toast.LENGTH_LONG).show();
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
				try {
					Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
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

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		list.clear();
	}
	
	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.fragment_classification, paramViewGroup, false);
		getSort();
		System.err.println("长度：" + list.size());
		findView(localView);
		return localView;
	}

	public void onDetach() {
		super.onDetach();
		try {
			Field localField = Fragment.class.getDeclaredField("mChildFragmentManager");
			localField.setAccessible(true);
			localField.set(this, null);
			return;
		} catch (NoSuchFieldException localNoSuchFieldException) {
			localNoSuchFieldException.printStackTrace();
			return;
		} catch (IllegalAccessException localIllegalAccessException) {
			localIllegalAccessException.printStackTrace();
			return;
		} catch (IllegalArgumentException localIllegalArgumentException) {
			localIllegalArgumentException.printStackTrace();
		}
	}

	public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
		clAdapter.serSelectItem(paramInt);
		clAdapter.notifyDataSetInvalidated();
		this.fm.beginTransaction().replace(R.id.sort_fg, new ClassificationChildFragment(list.get(paramInt))).commitAllowingStateLoss();
	}
}
