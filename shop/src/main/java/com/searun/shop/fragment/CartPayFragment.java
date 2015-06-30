package com.searun.shop.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.activity.ConfirmOrder;
import com.searun.shop.adapter.CartPayAdapter;
import com.searun.shop.adapter.CartPayAdapter.ViewHolder;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.application.MyApplication;
import com.searun.shop.data.CartItemDto;
import com.searun.shop.data.MemberDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToCartList;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;
import com.searun.shop.view.MyListView;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartPayFragment extends Fragment implements OnClickListener,OnItemClickListener{
	private MyApplication app;
	private List<CartItemDto> list;
	private List<CartItemDto> userList;
	private static CartPayFragment instance;
	private CartPayAdapter adapter;
	private MyListView payListView;
	private TextView sum;
	private MemberDto md;
	
	private SharedPreferences sp;
	private CustomProgressDialog pro;
	private FragmentManager fragmentManager;
	private TextView textView;
	@SuppressLint("ValidFragment")
	public CartPayFragment(FragmentManager fragmentManager,TextView textView) {
		super();
		this.fragmentManager = fragmentManager;
		this.textView = textView;
	}
	
	public CartPayFragment() {
		super();
	}

	public static CartPayFragment getInstance(FragmentManager fragmentManager,TextView textView) {
		if (null == instance) {
			instance = new CartPayFragment(fragmentManager,textView);
		}
		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.child_fragment_cart_pay, container, false);
		findView(view);
		sum.setText("0.0");
		payList.clear();
		if (null != md) {
			updateCart();
			pro.setMessage("加载中，请稍后");
			pro.show();
		} else {
			adapter = new CartPayAdapter(list, getActivity(), true);
//			adapter.setListener(operationListener);
			payListView.setAdapter(adapter);
		}
		return view;
	}
	
	private void findView(View view) {
		app = (MyApplication) getActivity().getApplication();
		list = app.getCartList();
		userList = new ArrayList<CartItemDto>();
		payList = new ArrayList<CartItemDto>();
		pro = CustomProgressDialog.createDialog(getActivity());
		sp = getActivity().getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		md = (MemberDto) StoreObject.readOAuth("md", sp);
		payListView = (MyListView) view.findViewById(R.id.cart);
		
		allSelect = (CheckBox) view.findViewById(R.id.allselect);
		
		sum = ((TextView) view.findViewById(R.id.sum));
		pay = (Button) view.findViewById(R.id.pay);
		
		pay.setOnClickListener(instance);
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sum.setText("0.0");
		payList.clear();
		if (null != md) {
			updateCart();
			pro.setMessage("加载中，请稍后");
			pro.show();
		} else {
			adapter = new CartPayAdapter(list, getActivity(), true);
//			adapter.setListener(operationListener);
			payListView.setAdapter(adapter);
		}
	}
	
//	CartPayAdapter.OperationListener operationListener = new CartPayAdapter.OperationListener() {
//
//		@Override
//		public void operationOnclick(final List<CartItemDto> list) {
//			// TODO Auto-generated method stub
//			try {
//				double d = 0d  ;
//				for (int i = 0; i < list.size(); i++) {
//					BigDecimal price = list.get(i).getProduct().getPrice();
//					BigDecimal quantity = new BigDecimal(list.get(i).getQuantity());
//					Log.i("sum", "price:"+ price.toString() + "quantity:" + quantity.toString());
//					d = d + price.multiply(quantity).doubleValue();
//				}
//				sum.setText(d + "");
//				pay.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Intent intent = new Intent(getActivity(), ConfirmOrder.class);
//						Bundle bundle = new Bundle();
//						bundle.putSerializable("list", (Serializable) list);
//						intent.putExtra("bundle", bundle);
//						startActivity(intent);
//						Log.i("参数", list.toString());
//					}
//				});
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//	};
	private Button pay;
	private CheckBox allSelect;
	private void updateCart(){
		HttpUtil.post("listCarItems.action", NetWork.getCart(null, md), new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				System.err.println("CartPayFragment获取购物车:"+response.toString());
				try {
					PdaResponse<List<CartItemDto>> response2 = JsonToCartList.parserLoginJson(response.toString());
					userList = response2.getData();
					Log.i("服务器购物车size", userList.size() + "");
					if (userList.isEmpty()) {
						fragmentManager.beginTransaction().replace(R.id.cart_fragment, CartEmptyFragment.getInstance(), "购物车为空").commitAllowingStateLoss();
						textView.setVisibility(View.GONE);
					} else {
					app.setCartList(null);
//					app.setNewCartList(userList);
					adapter = new CartPayAdapter(userList, getActivity(), true);
//					adapter.setListener(operationListener);
					payListView.setAdapter(adapter);
					allSelect.setOnClickListener(instance);
					payListView.setOnItemClickListener(instance);
					adapter.notifyDataSetChanged();
//					double d = 0d  ;
//					for (int i = 0; i < userList.size(); i++) {
//						BigDecimal price = userList.get(i).getProduct().getPrice();
//						BigDecimal quantity = new BigDecimal(userList.get(i).getQuantity());
//						Log.i("sum", "price:"+ price.toString() + "quantity:" + quantity.toString());
//						d = d + price.multiply(quantity).doubleValue();
//					}
//					sum.setText(d + "");
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
	private int checkNum; // 记录选中的条目数量
	private List<CartItemDto> payList;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.allselect:
			try {
				if (allSelect.isChecked()) {
					for (int i = 0; i < userList.size(); i++) {
						CartPayAdapter.getIsSelected().put(i, true);
						payList.add(userList.get(i));
					}
					
					sum.setText(getPrice(payList));
					adapter.notifyDataSetChanged();
					checkNum = userList.size();
				} else {
					for (int i = 0; i < userList.size(); i++) {
						CartPayAdapter.getIsSelected().put(i, false);
						payList.remove(userList.get(i));
					}
//					payList = null;
					sum.setText(getPrice(payList));
					adapter.notifyDataSetChanged();
					checkNum = 0;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case R.id.pay:
			if (Double.parseDouble(sum.getText().toString()) > 0) {
				Intent intent = new Intent(getActivity(), ConfirmOrder.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("payList", (Serializable) payList);
				intent.putExtra("bundle", bundle);
				startActivity(intent);
			} else {
				Toast.makeText(getActivity(), "您未选中商品", Toast.LENGTH_LONG).show();
			}
			break;
		}
	}
	private String getPrice(List<CartItemDto> list){
		double d = 0d  ;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BigDecimal price = list.get(i).getProduct().getPrice();
				BigDecimal quantity = new BigDecimal(list.get(i).getQuantity());
				Log.i("sum", "price:"+ price.toString() + "quantity:" + quantity.toString());
				d = d + price.multiply(quantity).doubleValue();
			}
		}
		return d+"";
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		try {
			ViewHolder vh = (ViewHolder) view.getTag();
			vh.checkBox.toggle();
			CartPayAdapter.getIsSelected().put(position, vh.checkBox.isChecked());
			if (vh.checkBox.isChecked()) {
				payList.add(userList.get(position));
				checkNum ++;
			} else {
				if (payList.contains(userList.get(position))) {
				payList.remove(userList.get(position));
				checkNum --;
				}
			}
			sum.setText(getPrice(payList));
			if (checkNum == 0) {
				allSelect.setChecked(false);
			} else if (checkNum == userList.size()) {
				allSelect.setChecked(true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
