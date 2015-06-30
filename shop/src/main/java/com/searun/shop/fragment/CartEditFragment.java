package com.searun.shop.fragment;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.adapter.CartEditAdapter;
import com.searun.shop.application.MyApplication;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.CartItemDto;
import com.searun.shop.data.MemberDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToCartList;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CartEditFragment extends Fragment {
	private static CartEditFragment instance;
	private List<CartItemDto> list;
	private ListView editList;
	private MyApplication app;
	private MemberDto md;
	private SharedPreferences sp;
	private CartEditAdapter adapter;
	private CustomProgressDialog pro;
	public static CartEditFragment getInstance(){
		if (null == instance) {
			instance = new CartEditFragment();
		}
		return instance;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.child_fragment_cart_edit, container, false);
		findView(view);
		return view;
	}
	private void findView(View view){
		try {
			app = (MyApplication) getActivity().getApplication();
			pro = CustomProgressDialog.createDialog(getActivity());
			sp = getActivity().getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
			md = (MemberDto) StoreObject.readOAuth("md", sp);
			editList = (ListView) view.findViewById(R.id.cart);
			if (null != md) {
				
				getCart();
				pro.setMessage("加载中，请稍后");
				pro.show();
			} else {
				adapter = new CartEditAdapter(app.getCartList(), getActivity(), app, md);
				adapter.setListener(operationListener);
				editList.setAdapter(adapter );
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	CartEditAdapter.OperationListener operationListener = new CartEditAdapter.OperationListener() {
		
		@Override
		public void operationOnclick(View paramView, CartItemDto cartItemDto) {
			// TODO Auto-generated method stub
			try {
				if (null != md) {
					getCart();
					pro.setMessage("加载中，请稍后");
					pro.show();
				} else {
					adapter = new CartEditAdapter(app.getCartList(), getActivity(), app, md);
					adapter.setListener(operationListener);
					editList.setAdapter(adapter );
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
//	EmptyCart emptyCart;
//	public void setCart(EmptyCart emptyCart){
//		this.emptyCart = emptyCart;
//	}
//	public interface EmptyCart{
//		public void showImage(List<CartItemDto> list);
//	}
	private void getCart(){
		HttpUtil.post("listCarItems.action", NetWork.getCart(null, md), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				System.err.println("CartEditFragment获取购物车:"+response.toString());
				try {
					PdaResponse<List<CartItemDto>> response2 = JsonToCartList.parserLoginJson(response.toString());
					List<CartItemDto> userList = response2.getData();
					Log.i("服务器购物车size", userList.size() + "");
					app.setCartList(null);
					adapter = new CartEditAdapter(userList, getActivity(), app, md);
					adapter.setListener(operationListener);
					editList.setAdapter(adapter );
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
}
