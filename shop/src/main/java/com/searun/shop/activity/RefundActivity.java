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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.searun.shop.R;
import com.searun.shop.adapter.DeliveryCorpAdapter;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.DeliveryCorpDto;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.OrderItemDto;
import com.searun.shop.data.ProductImage;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToDeliveryCropList;
import com.searun.shop.toobject.JsonToProductImage;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

public class RefundActivity extends Activity implements OnCheckedChangeListener, OnClickListener,OnItemClickListener {
	private ImageView imageView;
	private TextView name;
	private TextView price;
	private TextView orderItemStatus;
	private TextView quanlity;
	private RadioGroup type;
	private EditText refundPrice;
	private LinearLayout logisticsLayout;
	private TextView select;
	private EditText number;
	private EditText description;
	private RefundActivity instance;
	private CustomProgressDialog pro;
	private SharedPreferences sp;
	private MemberDto md;
	private Intent refundIntent;
	private OrderItemDto orderItemDto;
	private DisplayImageOptions options;
	private Dialog deliveryCorpDialog;
	private ListView listView;
	private Button cancelButton;
	private String command; //命令
	
	private Boolean isReceived; //是否收到货物
	
	private String reshipSn; //物流编号
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_refund);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		try {
		init();
		findView();
		setView();
		submit.setOnClickListener(instance);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void init() {
		instance = this;
		refundIntent = getIntent();
		orderItemDto = (OrderItemDto) refundIntent.getSerializableExtra("OrderItemDto");
		pro = CustomProgressDialog.createDialog(instance);
		pro.setMessage("加载中，请稍后");
		options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.mrpic_little)
					.showImageForEmptyUri(R.drawable.mrpic_little)
					.showImageOnFail(R.drawable.mrpic_little)
					.cacheInMemory(true)
					.cacheOnDisk(true)
					.build();
		sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		
		md = (MemberDto) StoreObject.readOAuth("md", sp);
		command = "refund";
		isReceived = false;
	}

	private void findView() {
		Button back = (Button) findViewById(R.id.back);
		back.setOnClickListener(instance);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("申请退款");
		findViewById(R.id.other).setVisibility(View.GONE);
		
		imageView = (ImageView) findViewById(R.id.goods_pic);
		name = (TextView) findViewById(R.id.name);
		price = (TextView) findViewById(R.id.price);
		findViewById(R.id.orderItemStatus).setVisibility(View.GONE);
		logisticsLayout = (LinearLayout) findViewById(R.id.logistics_layout);
		quanlity = (TextView) findViewById(R.id.quanlity);
		type = (RadioGroup) findViewById(R.id.type);
		type.setOnCheckedChangeListener(instance);
		refundPrice = (EditText) findViewById(R.id.refund_price);
		select = (TextView) findViewById(R.id.select_logistics);
		select.setOnClickListener(instance);
		number = (EditText) findViewById(R.id.num_logistics);
		description = (EditText) findViewById(R.id.refund_description);
		submit = (Button) findViewById(R.id.submit);
		
		//屏蔽进入输入法弹出框
		TextView config_hidden = (TextView) findViewById(R.id.config_hidden);
		config_hidden.requestFocus();
	}
	private void setView() throws Exception{
		List<ProductImage> imagesList = JsonToProductImage.parserLoginJson(orderItemDto.getProduct().getProductImageListStore());
		if (null != imagesList) {
			ImageLoader.getInstance().displayImage(HttpUtil.BASE_URL + imagesList.get(0).getSmallProductImagePath(), imageView, options);
		} else {
			imageView.setImageResource(R.drawable.mrpic_little);
		}
		name.setText(orderItemDto.getProduct().getName());
		price.setText("￥" + orderItemDto.getProduct().getPrice().toString());
		quanlity.setText( "x" + (orderItemDto.getProductQuantity() < 1 ? 1 :orderItemDto.getProductQuantity() )+ "");
	}
	
	/**
	 * 退款
	 */
	private void refund() {
		orderItemDto.setCommand(command);
		orderItemDto.setReceived(isReceived);
		Log.i("id2", deliveryCorpDto.getId());
		orderItemDto.setReshipSn(number.getText().toString());
		orderItemDto.setRefundAmount(new BigDecimal(refundPrice.getText().toString()));
		HttpUtil.post("commitRefund.action", NetWork.getParamsList(orderItemDto, md, null), new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("退款", response.toString());
				if (response.optBoolean("success")) {
					try {
//						PdaResponse<OrderItemDto> pdaResponse = JsonToOrderItem.parserLoginJson(response.toString());
//						OrderItemDto orderItemDto = pdaResponse.getData();
						// setText(orderItemDto.getOrderItemStatus(), textView);
						Toast.makeText(instance, response.optString("message"), Toast.LENGTH_LONG).show();
						finish();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
					Log.i("完成", "完成");
				}
			}
		});
	}
	private List<DeliveryCorpDto> logisticsList;
	private void getLogisticsFromServer(){
		try {
			HttpUtil.post("getDeliveryCorps.action", NetWork.getParamsList(null, md, null), new JsonHttpResponseHandler(){
				
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("物流公司", response.toString());
					try {
						
						PdaResponse<List<DeliveryCorpDto>> pdaResponse = JsonToDeliveryCropList.parserLoginJson(response.toString());
						logisticsList = pdaResponse.getData();
						if (null != logisticsList) {
							selectLogistics(logisticsList).show();
							
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
	 * 选择物流公司
	 * @param list
	 */
	private  Dialog selectLogistics(List<DeliveryCorpDto> list){
		try {
			View view = View.inflate(instance, R.layout.dialog_payment, null);
			deliveryCorpDialog = new Dialog(instance, R.style.DeliveryDialog);
			listView = (ListView) view.findViewById(R.id.payment_select_listview);
			cancelButton = (Button) view.findViewById(R.id.payment_cancel);
			cancelButton.setOnClickListener(instance);
			listView.setAdapter(new DeliveryCorpAdapter(list, instance));
			listView.setOnItemClickListener(instance);
			deliveryCorpDialog.setContentView(view,
					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT - 20, LinearLayout.LayoutParams.WRAP_CONTENT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deliveryCorpDialog;
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.refund:
			command = "refund";
			isReceived = false;
			logisticsLayout.setVisibility(View.GONE);
			break;

		case R.id.return_goods:
			command = "reshiped";
			isReceived = true;
			logisticsLayout.setVisibility(View.VISIBLE);
			break;
		
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submit:
			pro.show();
			refund();
			break;

		case R.id.select_logistics:
			pro.show();
			getLogisticsFromServer();
			break;
		case R.id.back:
			finish();
			break;
		case R.id.payment_cancel:
			deliveryCorpDialog.cancel();
			break;
		}
	}
	private DeliveryCorpDto deliveryCorpDto;
	private Button submit;
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		deliveryCorpDto = logisticsList.get(position);
		Log.i("id1", deliveryCorpDto.getId());
		orderItemDto.setDeliveryCorpDto(deliveryCorpDto);
		select.setText(deliveryCorpDto.getName());
		deliveryCorpDialog.cancel();
	}
}
