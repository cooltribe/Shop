package com.searun.shop.activity;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.adapter.CartPayAdapter;
import com.searun.shop.adapter.DeliveryAdapter;
import com.searun.shop.adapter.PaymentAdapter;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.application.MyApplication;
import com.searun.shop.data.CartItemDto;
import com.searun.shop.data.DeliveryTypeDto;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.OrderDto;
import com.searun.shop.data.PaymentConfigDto;
import com.searun.shop.data.ReceiverDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaRequest;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToDeliveryList;
import com.searun.shop.toobject.JsonToOrder;
import com.searun.shop.toobject.JsonToPaymentList;
import com.searun.shop.toobject.JsonToReceive;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.util.Utility;
import com.searun.shop.view.CustomProgressDialog;
import com.searun.shop.view.MyListView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ConfirmOrder extends Activity implements OnClickListener,OnItemClickListener{
	private static final String TAG = "ConfirmOrder";
	private static ConfirmOrder instance;
	private List<CartItemDto> cartItemDtoList;
	private Intent intent;
	private MyListView orderListView;
	private MyApplication app;
	private CartPayAdapter adapter;
	private RelativeLayout delivery;
	private SharedPreferences sp;
	private MemberDto md;
	private List<DeliveryTypeDto> deliveryList;
	private List<PaymentConfigDto> paymentList;
	private TextView deliveryTextView;
	private RelativeLayout payment;
	private TextView paymentTextView;
	private Dialog deliveryDialog;
	private Dialog paymentDialog;
	private View deliveryView;
	private ListView deliveryListView;
	private Button deliveryButton;
	private Button cancelButton;
	private ListView listView;
	private TextView addressee;
	private TextView phone;
	private TextView city;
	private TextView street;
	private TextView postCode;
	private CustomProgressDialog pro;
	private Button back;
	private Button payButton;
	private View addressView;
	private String receiverId;
	private DeliveryTypeDto deliveryTypeDto;
	private PaymentConfigDto paymentConfigDto;
	private EditText memo;
	private String productName;
	private double total;
	private TextView totalTextView;
	private TextView productCount;
	private TextView productPrice;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_confirm_order);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		init();
		findView();
		getDefaultAddress();
		getDeliveryFromServer();
	}
	@SuppressWarnings("unchecked")
	private void init(){
		try {
			instance = this;
			intent = getIntent();
			pro = CustomProgressDialog.createDialog(instance);
			pro.setMessage(getString(R.string.loading));
			pro.show();
			sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
			md = (MemberDto) StoreObject.readOAuth("md", sp);
			
			app = (MyApplication) getApplication();
			deliveryList = new ArrayList<DeliveryTypeDto>();
			cartItemDtoList = new ArrayList<CartItemDto>();
			cartItemDtoList = (List<CartItemDto>) intent.getBundleExtra("bundle").getSerializable("payList");
//			deliveryTypeDto = new DeliveryTypeDto();
//			paymentConfigDto = new PaymentConfigDto();
			
			productName = "";
			total = 0d;
			for (int i = 0; i < cartItemDtoList.size(); i++) {
				productName = productName + (cartItemDtoList.get(i).getProduct().getName() + "x" +cartItemDtoList.get(i).getQuantity());
				BigDecimal price = cartItemDtoList.get(i).getProduct().getPrice();
				BigDecimal quantity = new BigDecimal(cartItemDtoList.get(i).getQuantity());
				total = total + price.multiply(quantity).doubleValue();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void findView(){
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("确认订单");
		
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(instance);
		
		findViewById(R.id.other).setVisibility(View.GONE);
		
		addressView = findViewById(R.id.address1);
		addressView.setOnClickListener(instance);
		
		addressee = (TextView) findViewById(R.id.addressee);
		phone = (TextView) findViewById(R.id.phone);
		city = (TextView) findViewById(R.id.city);
		street = (TextView) findViewById(R.id.street);
		postCode = (TextView) findViewById(R.id.postcode);
		findViewById(R.id.check_layout).setVisibility(View.GONE);
		/**
		 * 配送方式
		 */
		delivery = (RelativeLayout) findViewById(R.id.delivery);
//		delivery.setOnClickListener(this);
		
		/**
		 * 给商家附言
		 */
		memo = (EditText) findViewById(R.id.memo);
		
		deliveryTextView = (TextView) findViewById(R.id.delivery_tv);
		
		
		/**
		 * 商品数量和总价格
		 */
		productCount = (TextView) findViewById(R.id.product_count);
		productPrice = (TextView) findViewById(R.id.product_price);
		productCount.setText("共"+ cartItemDtoList.size() + "件商品     合计：");
		double price = 0d;
		for (int i = 0; i < cartItemDtoList.size(); i++) {
			price += cartItemDtoList.get(i).getProduct().getPrice().doubleValue() * cartItemDtoList.get(i).getQuantity();
		}
		productPrice.setText(price + "");
		/**
		 * 支付方式
		 */
		payment = (RelativeLayout) findViewById(R.id.payment);
		payment.setOnClickListener(instance);
		
		paymentTextView = (TextView) findViewById(R.id.payment_tv);
		
		/**
		 * 预备购买的商品
		 */
		orderListView = (MyListView) findViewById(R.id.order_list);
		adapter = new CartPayAdapter(cartItemDtoList, this,false);
		orderListView.setAdapter(adapter);
		Utility.setListViewHeightBasedOnChildren(orderListView);
		
		/**
		 * 合计金额
		 */
		totalTextView = (TextView) findViewById(R.id.total);
		totalTextView.setText("￥" + total);
		
		/**
		 * 提交订单按钮
		 */
		payButton = (Button) findViewById(R.id.pay_button);
		
		payButton.setOnClickListener(instance);
	}
	
	private void getDelivery(JSONObject jsonObject) throws JSONException{
		try {
			PdaResponse<List<DeliveryTypeDto>> response = JsonToDeliveryList.parserLoginJson(jsonObject.toString());
			deliveryList = response.getData();
			if (null != deliveryList) {
//				selectDelivery(deliveryList).show();
				deliveryTypeDto = deliveryList.get(0);
				deliveryTextView.setText(deliveryList.get(0).getName());
//				deliveryDialog.cancel();
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 选择配送方式
	 * @param list
	 */
	private  Dialog selectDelivery(List<DeliveryTypeDto> list){
		try {
			deliveryView = View.inflate(instance, R.layout.dialog_delivery, null);
			deliveryDialog = new Dialog(instance, R.style.DeliveryDialog);
			deliveryListView = (ListView) deliveryView.findViewById(R.id.delivery_select_listview);
			deliveryButton = (Button) deliveryView.findViewById(R.id.delivery_cancel);
			deliveryButton.setOnClickListener(instance);
			deliveryListView.setAdapter(new DeliveryAdapter(list, instance));
			deliveryListView.setOnItemClickListener(instance);
			deliveryDialog.setContentView(deliveryView,
					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT - 20, LinearLayout.LayoutParams.WRAP_CONTENT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deliveryDialog;
	}
	/**
	 * 获取默认地址
	 */
	private void getDefaultAddress(){
		try {
			HttpUtil.post("getDefaultReceiver.action", NetWork.getParamsList(null, md, null), new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("默认地址", response.toString());
					try {
						PdaResponse<ReceiverDto> pdaResponse = JsonToReceive.parserLoginJson(response.toString());
						ReceiverDto rd = new ReceiverDto();
						rd = pdaResponse.getData();
						if (null != rd) {
							addressee.setText(rd.getName());
							phone.setText(rd.getPhone() == null ? rd.getMobile() : rd.getPhone());
							city.setText(rd.getAreaPath());
							street.setText(rd.getAddress());
							postCode.setText(rd.getZipCode());
							receiverId = rd.getId();
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private void getDeliveryFromServer(){
		try {
			HttpUtil.post("getDeliveryTypes.action", NetWork.getParamsList(null, md, null), new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("配送方式", response.toString());
					try {
						getDelivery(response);
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
	

	private void getPaymentFromServer(){
		try {
			HttpUtil.post("getPaymentConfigs.action", NetWork.getParamsList(null, md, null), new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("支付方式", response.toString());
					try {
						
						PdaResponse<List<PaymentConfigDto>> pdaResponse = JsonToPaymentList.parserLoginJson(response.toString());
						paymentList = pdaResponse.getData();
						if (null != paymentList) {
							selectPayment(paymentList).show();
							
						}
						
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
	 * 选择付款方式
	 * @param list
	 */
	private  Dialog selectPayment(List<PaymentConfigDto> list){
		try {
			View view = View.inflate(instance, R.layout.dialog_payment, null);
			paymentDialog = new Dialog(instance, R.style.DeliveryDialog);
			TextView title = (TextView) view.findViewById(R.id.title);
			title.setText("请选择付款方式");
			listView = (ListView) view.findViewById(R.id.payment_select_listview);
			cancelButton = (Button) view.findViewById(R.id.payment_cancel);
			cancelButton.setOnClickListener(instance);
			listView.setAdapter(new PaymentAdapter(list, instance));
			listView.setOnItemClickListener(instance);
			Log.i("wh", LinearLayout.LayoutParams.WRAP_CONTENT + "," + LinearLayout.LayoutParams.WRAP_CONTENT);
			paymentDialog.setContentView(view,
					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT - 20, LinearLayout.LayoutParams.WRAP_CONTENT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paymentDialog;
	}
	
	private void submitOrder(){
		try {
			OrderDto od = new OrderDto();
			od.setCartItemDtos(cartItemDtoList);
			if (deliveryTypeDto != null) {
				od.setDeliveryType(deliveryTypeDto);
				
			} else {
				Toast.makeText(instance, "配送方式不能为空", Toast.LENGTH_LONG).show();
			}
			if (paymentConfigDto != null) {
				od.setPaymentConfig(paymentConfigDto);
			}
			od.setReceiverId(receiverId);
			od.setMemo(memo.getText().toString());
			PdaRequest<OrderDto> pdaRequest = new PdaRequest<OrderDto>();
			pdaRequest.setData(od);
			HttpUtil.post("commitOrder.action", NetWork.getParamsList(od, md, null), new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("结算信息：",response.toString() );
					try {
						PdaResponse<OrderDto> pdaResponse = JsonToOrder.parserLoginJson(response.toString());
						if (pdaResponse.isSuccess()) {
							OrderDto orderDto = pdaResponse.getData();
							if (orderDto.getPaymentConfig().getId().equals("402882134cdf2f3d014ce04ae515003a")) {
								Intent payIntent = new Intent(instance, BankPay.class);
								Bundle bundle = new Bundle();
								bundle.putSerializable("order", orderDto);
								bundle.putSerializable("paymentConfigDto",paymentConfigDto);
								payIntent.putExtras(bundle);
								startActivity(payIntent);
							}else if (orderDto.getPaymentConfig().getId().equals("402881862b9f9e78012b9fa02aca0004")) {
								
								Intent payIntent = new Intent(instance, AliPay.class);
								Bundle bundle = new Bundle();
								bundle.putSerializable("order", orderDto);
								payIntent.putExtra("name", productName);
								payIntent.putExtras(bundle);
								startActivity(payIntent);
							}
							instance.finish();
						} else {
							Toast.makeText(instance, pdaResponse.getMessage(), Toast.LENGTH_LONG).show();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//				if (response.optBoolean("success")) {
//					Intent payIntent = new Intent(instance, Pay.class);
//					payIntent.putExtra("name", productName);
//					payIntent.putExtra("price", total);
//					startActivity(payIntent);
//				} else {
//					Toast.makeText(instance, response.optString("message"), Toast.LENGTH_LONG).show();
//				}
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
					Log.i("提交完成", "提交完成");
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try {
			switch (resultCode) {
			case Activity.RESULT_OK:
				ReceiverDto rd = (ReceiverDto) data.getSerializableExtra("address");
				addressee.setText(rd.getName());
				phone.setText(rd.getPhone() == null ? rd.getMobile() : rd.getPhone());
				city.setText(rd.getAreaPath());
				street.setText(rd.getAddress());
				postCode.setText(rd.getZipCode());
				receiverId = rd.getId();
				Log.i("receiverId11", receiverId);
				break;

			default:
				break;
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
			case R.id.address1:
				Intent intent = new Intent(this, AddressSelect.class);
				intent.putExtra("title", "地址选择");
				startActivityForResult(intent, 1);
				break;
			case R.id.delivery:
				pro.show();
				getDeliveryFromServer();
				break;

			case R.id.payment:
				pro.show();
				getPaymentFromServer();
				break;
			case R.id.payment_cancel:
				paymentDialog.cancel();
				break;
			case R.id.delivery_cancel:
				deliveryDialog.cancel();
				break;
			case R.id.back:
				finish();
				break;
			case R.id.pay_button:
				if (deliveryTypeDto == null ) {
					Toast.makeText(instance, "配送方式不能为空", Toast.LENGTH_LONG).show();
				} else if (paymentConfigDto == null) {
					Toast.makeText(instance, "支付方式不能为空", Toast.LENGTH_LONG).show();
				} else {
					pro.show();
					Log.i("??????", deliveryTypeDto.toString() + "----" + paymentConfigDto.toString());
					submitOrder();
				}
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
			switch (parent.getId()) {
			case R.id.delivery_select_listview:
				deliveryTypeDto = deliveryList.get(position);
				deliveryTextView.setText(deliveryList.get(position).getName());
				deliveryDialog.cancel();
				break;
				
			case R.id.payment_select_listview:
				paymentConfigDto = paymentList.get(position);
				paymentTextView.setText(paymentList.get(position).getName());
				paymentDialog.cancel();
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}


