package com.searun.shop.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.activity.AliPay;
import com.searun.shop.activity.BankPay;
import com.searun.shop.activity.GoodsInformation;
import com.searun.shop.activity.LogisticsActivity;
import com.searun.shop.activity.WriteJudgeListActivity;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.OrderDto;
import com.searun.shop.data.OrderItemDto;
import com.searun.shop.data.PaymentConfigDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToPaymentList;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.view.CustomProgressDialog;
import com.searun.shop.view.MyListView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends BaseAdapter {

	List<OrderDto> list;
	Context context;
	LayoutInflater inflater;
	List<OrderItemDto> orderItemList;
	MemberDto memberDto;
	CustomProgressDialog pro;
	boolean isRefund ;
	boolean isJudge;
	
	public OrderAdapter(List<OrderDto> list, Context context, MemberDto memberDto, CustomProgressDialog pro) {
		super();
		this.list = list;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.memberDto = memberDto;
		this.pro = pro;
		orderItemList = new ArrayList<OrderItemDto>();
		dialogView = View.inflate(context, R.layout.dialog_payment, null);
		paymentDialog = new Dialog(context, R.style.DeliveryDialog);
		listView = (ListView) dialogView.findViewById(R.id.payment_select_listview);
		cancelButton = (Button) dialogView.findViewById(R.id.payment_cancel);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		try {
			ViewHolder vh;
			if (null == convertView) {
				vh = new ViewHolder();
				convertView = inflater.inflate(R.layout.item_myorder, parent, false);
				vh.orderNumber = (TextView) convertView.findViewById(R.id.order_number);
				vh.orderState = (TextView) convertView.findViewById(R.id.order_state);
				vh.payState = (TextView) convertView.findViewById(R.id.pay_state);
				vh.shipedState = (TextView) convertView.findViewById(R.id.shiped_state);
				vh.orderProductList = (MyListView) convertView.findViewById(R.id.order_product_list);
				vh.Count = (TextView) convertView.findViewById(R.id.count);
				vh.freight = (TextView) convertView.findViewById(R.id.freight);
				vh.total = (TextView) convertView.findViewById(R.id.total);
				vh.returnButton = (Button) convertView.findViewById(R.id.return_product);
				vh.payButton = (Button) convertView.findViewById(R.id.pay_button);
				vh.refundButton = (Button) convertView.findViewById(R.id.refund_button);
				vh.judge = (Button) convertView.findViewById(R.id.judge);
				vh.look = (Button) convertView.findViewById(R.id.look);
				vh.confirm = (Button) convertView.findViewById(R.id.confirm);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			vh.orderNumber.setText("订单编号：" + list.get(position).getOrderSn());
			if (!list.get(position).getShippingStatus().equals("unshipped")) {
				vh.look.setVisibility(View.VISIBLE);
			} else {
				vh.look.setVisibility(View.GONE);
			}
			
			if (list.get(position).getPaymentStatus().equals("unpaid")) {
				vh.payButton.setVisibility(View.VISIBLE);
			} else {
				vh.payButton.setVisibility(View.GONE);
			}
			if ((!list.get(position).getPaymentStatus().equals("unpaid"))&& (!list.get(position).getPaymentStatus().equals("refunded"))  
					&&(!list.get(position).getOrderStatus().equals("received")) && (!list.get(position).getOrderStatus().equals("completed"))
					&& (!list.get(position).getShippingStatus().equals("reshiped"))) {
				isRefund = true;
			} else {
				isRefund = false;
			}
			if (list.get(position).getOrderStatus().equals("received")) {
				vh.judge.setVisibility(View.GONE);
				isJudge = true;
			} else {
				vh.judge.setVisibility(View.GONE);
				isJudge = false;
			}
			if (list.get(position).getShippingStatus().equals("shipped") && (!list.get(position).getOrderStatus().equals("received"))) {
				vh.confirm.setVisibility(View.VISIBLE);
			} else {
				vh.confirm.setVisibility(View.GONE);
			}
			if (list.get(position).getOrderStatus().equals("unprocessed")) {
				 vh.orderState.setText("未处理");
			} else if (list.get(position).getOrderStatus().equals("processed")) {
				vh.orderState.setText("已处理");
			} else if (list.get(position).getOrderStatus().equals("completed")) {
				vh.orderState.setText("已完成");
			} else if (list.get(position).getOrderStatus().equals("cancel")) {
				vh.orderState.setText("已取消");
			} else if (list.get(position).getOrderStatus().equals("delete")) {
				vh.orderState.setText("已删除");
			} else if (list.get(position).getOrderStatus().equals("invalid")) {
				vh.orderState.setText("已作废");
			} else if (list.get(position).getOrderStatus().equals("received")) {
				vh.orderState.setText("已收货");
			} else if (list.get(position).getOrderStatus().equals("evaluation")) {
				vh.orderState.setText("已评价");
			}
			if (list.get(position).getShippingStatus().equals("unshipped")) {
				vh.shipedState.setText("未发货");
			} else if (list.get(position).getShippingStatus().equals("partShipped")) {
				vh.shipedState.setText("部分发货");
			} else if (list.get(position).getShippingStatus().equals("shipped")) {
				vh.shipedState.setText("已发货");
			} else if (list.get(position).getShippingStatus().equals("partReshiped")) {
				vh.shipedState.setText("部分退货");
			} else if (list.get(position).getShippingStatus().equals("reshiped")) {
				vh.shipedState.setText("已退货");
			}
			// 付款状态（未支付、部分支付、已支付、部分退款、全额退款）
			//unpaid, partPayment, paid, partRefund, refunded
			if (list.get(position).getPaymentStatus().equals("unpaid")) {
				vh.payState.setText("未支付");
			} else if (list.get(position).getPaymentStatus().equals("partPayment")) {
				vh.payState.setText("部分支付");
			} else if (list.get(position).getPaymentStatus().equals("paid")) {
				vh.payState.setText("已支付");
			} else if (list.get(position).getPaymentStatus().equals("partRefund")) {
				vh.payState.setText("部分退款");
			} else if (list.get(position).getPaymentStatus().equals("refunded")) {
				vh.payState.setText("全额退款");
			}
			vh.judge.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, WriteJudgeListActivity.class);
					intent.putExtra("orderId", list.get(position).getId());
					intent.putExtra("judgeList", (Serializable) list.get(position).getOrderItems());
					context.startActivity(intent);
				}
			});
			vh.payButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if ( !paymentDialog.isShowing()) {
						pro.show();
						getPaymentFromServer(position);
					}
					
				}
			});
			
			vh.confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new AlertDialog.Builder(context).setTitle("是否确认收货").setNegativeButton("取消", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					}).setPositiveButton("确认", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							pro.show();
							getTakeOver(list.get(position).getId());
							dialog.cancel();
							
						}
					}).create().show();
				}
			});
			vh.look.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, LogisticsActivity.class);
					intent.putExtra("orderId", list.get(position).getId());
					context.startActivity(intent);
				}
			});
			
//			orderItemList = list.get(position).getOrderItems();
			if (list.get(position).getOrderItems() != null) {
				// Utility.setListViewHeightBasedOnChildren(vh.orderProductList);
				vh.orderProductList.setAdapter(new OrderChildAdapter(list.get(position).getOrderItems(), context,pro,list.get(position).getId(),isRefund,isJudge));
				vh.orderProductList.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
						// TODO Auto-generated method stub
						Log.i("position", position1+"");
						Log.i("订单id", list.get(position).getOrderSn() + "");
						Intent intent = new Intent(context, GoodsInformation.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("pd", list.get(position).getOrderItems().get(position1).getProduct());
						intent.putExtras(bundle);
						context.startActivity(intent);
					}
				});
				vh.Count.setText("共" + list.get(position).getOrderItems().size() + "件商品      运费：");
			}
			vh.freight.setText("￥" + list.get(position).getDeliveryFee().toString());
			vh.total.setText("￥" + list.get(position).getTotalAmount().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	private Dialog paymentDialog;
	private ListView listView;
	private Button cancelButton;
	private List<PaymentConfigDto> paymentList;
	private View dialogView;
	private void getPaymentFromServer(final int position){
		try {
			HttpUtil.post("getPaymentConfigs.action", NetWork.getParamsList(null, memberDto, null), new JsonHttpResponseHandler(){
				
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("支付方式", response.toString());
					try {
						
						PdaResponse<List<PaymentConfigDto>> pdaResponse = JsonToPaymentList.parserLoginJson(response.toString());
						paymentList = pdaResponse.getData();
						if (null != paymentList) {
							selectPayment(paymentList,position).show();
							
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 选择付款方式
	 * @param payList
	 */
	private  Dialog selectPayment(final List<PaymentConfigDto> payList ,final int location){
		
		try {
			
			cancelButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					paymentDialog.cancel();
				}
			});
			listView.setAdapter(new PaymentAdapter(payList, context));
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
//					paymentConfigDto = list.get(position);
//					if (payList.get(position).getPaymentConfigType().equals("alipay")){
//						Intent aliIntent = new Intent(context, AliPay.class);
//						aliIntent.putExtra("order", list.get(location));
//						context.startActivity(aliIntent);
//					} else if (payList.get(position).getPaymentConfigType().equals("offline")){
//						Intent bankIntent = new Intent(context, BankPay.class);
//						bankIntent.putExtra("order", list.get(location));
//						bankIntent.putExtra("paymentConfigDto",payList.get(position));
//						context.startActivity(bankIntent);
//					}
					switch (position) {
					case 0:
						Intent aliIntent = new Intent(context, AliPay.class);
						aliIntent.putExtra("order", list.get(location));
						context.startActivity(aliIntent);
						break;

					case 1:
						Intent bankIntent = new Intent(context, BankPay.class);
						bankIntent.putExtra("order", list.get(location));
						bankIntent.putExtra("paymentConfigDto",payList.get(position));
						context.startActivity(bankIntent);
						break;
					}
					paymentDialog.cancel();
				}
			});
			paymentDialog.setContentView(dialogView,
					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT - 20, LinearLayout.LayoutParams.WRAP_CONTENT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paymentDialog;
	}
	/**
	 * 确认收货
	 * 
	 * @param id
	 *            订单id
	 */
	private void getTakeOver(String id) {
		HttpUtil.post("confirmOrder.action", NetWork.getParamsList(id, memberDto,null), new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("收货信息", response.toString());
				try {
					if (response.optBoolean("success")) {
						confirm.onConfirm(5);
//						notifyDataSetChanged();
					}
					Toast.makeText(context, response.optString("message"), Toast.LENGTH_LONG).show();
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
	public interface Confirm{
		void onConfirm(int tag);
	}
	private Confirm confirm;
	public void setConfirm(Confirm confirm){
		this.confirm = confirm;
	}
	class ViewHolder {
		TextView orderNumber;
		TextView orderState;
		TextView payState;
		TextView shipedState;
		MyListView orderProductList;
		TextView Count;
		TextView freight;
		TextView total;
		Button payButton;
		Button returnButton;
		Button refundButton;
		Button judge;
		Button look;
		Button confirm;
	}
}
