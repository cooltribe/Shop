package com.searun.shop.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.AvoidXfermode.Mode;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.adapter.CartPayAdapter;
import com.searun.shop.application.MyApplication;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.CartItemDto;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.ProductDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToCartList;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class ShopCartFragment extends Fragment implements OnClickListener{
	private static ShopCartFragment instance;
	private CartPayAdapter adapter;
	private MyApplication app;
	private Button buy;
	private Boolean isEmpty;//是否登陆
	private FragmentManager fm;
	private List<CartItemDto> list;
	private ListView listView;
	private SharedPreferences sp;
	private MemberDto md;
	private CustomProgressDialog pro;
	private Button pay;
	private TextView sum;
	private TextView textView;
	private View view;
	private CheckBox allSelect;
	private TextView operate;
	private TextView title;

	private void findView(View v) {
		try {
			fm = getChildFragmentManager();
			list = app.getCartList();
			pro = CustomProgressDialog.createDialog(getActivity());
			Button back = (Button) v.findViewById(R.id.back);
			back.setVisibility(View.GONE);
			title = (TextView) v.findViewById(R.id.title);
			title.setText("购物车");
			operate = (TextView) v.findViewById(R.id.operate);
			operate.setOnClickListener(instance);
			Log.i("用户是否为空", isEmpty + "");
			if (! isEmpty) {
				md = (MemberDto) StoreObject.readOAuth("md", sp);
				if (null != md) {
					getCartList(md);
					pro.setMessage("加载中，请稍后").show();
				}
			} else {
				setFragment(list);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setFragment(List<CartItemDto> list){
		
		try {
			if (list == null || list.size() < 1) {
				fm.beginTransaction().add(R.id.cart_fragment, CartEmptyFragment.getInstance(), "购物车为空").commitAllowingStateLoss();
				operate.setVisibility(View.GONE);
			} else {
				fm.beginTransaction().add(R.id.cart_fragment, CartPayFragment.getInstance(fm,operate), "购物车有货").commitAllowingStateLoss();
				operate.setVisibility(View.VISIBLE);
				operate.setText("编辑");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 从服务器获取购物车并同步
	 */
	private void getCartList(MemberDto md){
		HttpUtil.post("listCarItems.action", NetWork.getCart(list, md), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				System.err.println("ShopCartFragment同步购物车:"+response.toString());
				try {
					PdaResponse<List<CartItemDto>> response2 = JsonToCartList.parserLoginJson(response.toString());
					List<CartItemDto> userList = response2.getData();
					app.setCartList(null);
					if (userList != null) {
						
						Log.i("服务器购物车size", userList.size() + "");
						setFragment(userList);
					}
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
	public static ShopCartFragment getInstance() {
		if (instance == null)
			instance = new ShopCartFragment();
		return instance;
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

	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		view = paramLayoutInflater.inflate(R.layout.fragment_shopcart, paramViewGroup, false);
		app = ((MyApplication) getActivity().getApplication());
		sp = getActivity().getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		isEmpty = "".equals(sp.getString("md", ""));
		findView(view);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.operate:
			if (operate.getText().toString().equals("编辑")) {
				fm.beginTransaction().replace(R.id.cart_fragment, CartEditFragment.getInstance(), "购物车编辑").commitAllowingStateLoss();
				operate.setText("完成");
			} else if (operate.getText().toString().equals("完成")) {
				fm.beginTransaction().replace(R.id.cart_fragment, CartPayFragment.getInstance(fm,operate), "购物车有货").commitAllowingStateLoss();
				operate.setText("编辑");
			} 
			break;
		}
	}
	

}
