package com.searun.shop.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.searun.shop.R;
import com.searun.shop.activity.RefundActivity;
import com.searun.shop.activity.WriteJudgeActivity;
import com.searun.shop.data.CartItemDto;
import com.searun.shop.data.OrderItemDto;
import com.searun.shop.data.ProductImage;
import com.searun.shop.toobject.JsonToProductImage;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.view.CustomProgressDialog;

import java.util.List;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class OrderChildAdapter extends BaseAdapter
{
  Context context;
  LayoutInflater inflater;
  List<OrderItemDto> list;
  OperationListener operationListener;
  private DisplayImageOptions options;
  CustomProgressDialog pro;
  String orderId;
  boolean isRefund;
  boolean isJudge;
  public OrderChildAdapter(List<OrderItemDto> paramList, Context paramContext,CustomProgressDialog pro,String orderId,boolean isRefund,boolean isJudge)
  {
    this.list = paramList;
    this.context = paramContext;
    this.inflater = LayoutInflater.from(paramContext);
    this.pro = pro;
    this.orderId = orderId;
    this.isRefund = isRefund;
    this.isJudge = isJudge;
    	options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.mrpic_little)
				.showImageForEmptyUri(R.drawable.mrpic_little)
				.showImageOnFail(R.drawable.mrpic_little)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.build();
  }

  public int getCount()
  {
    return this.list.size();
  }

  public OrderItemDto getItem(int paramInt)
  {
    return list.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(final int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    try {
		final ViewHolder vh;
		if (paramView == null)
		{
		  vh = new ViewHolder();
		  paramView = this.inflater.inflate(R.layout.item_myorder_child, paramViewGroup, false);
		  vh.imageView = ((ImageView)paramView.findViewById(R.id.goods_pic));
		  vh.name = ((TextView)paramView.findViewById(R.id.name));
		  vh.price = ((TextView)paramView.findViewById(R.id.price));
		  vh.orderItemStatus = (TextView) paramView.findViewById(R.id.orderItemStatus);
		  vh.quanlity = ((TextView)paramView.findViewById(R.id.quanlity));
		  vh.judge = (TextView) paramView.findViewById(R.id.judge);
		  paramView.setTag(vh);
		  
		} else {
			vh = (ViewHolder) paramView.getTag();
		}
		List<ProductImage> imagesList = JsonToProductImage.parserLoginJson(list.get(paramInt).getProduct().getProductImageListStore());
		if (null != imagesList) {
			ImageLoader.getInstance().displayImage(HttpUtil.BASE_URL + imagesList.get(0).getSmallProductImagePath(), vh.imageView, options);
		} else {
			vh.imageView.setImageResource(R.drawable.mrpic_little);
		}
		vh.name.setText(list.get(paramInt).getProduct().getName());
		vh.price.setText("￥" + list.get(paramInt).getProduct().getPrice().toString());
		if (orderId == null) {
			vh.orderItemStatus.setVisibility(View.GONE);
			
		} else {
			
			setText(list.get(paramInt).getOrderItemStatus(), vh.orderItemStatus);
		}
		if (isJudge) {
			vh.judge.setVisibility(View.VISIBLE);
			vh.judge.setText("评价");
			vh.judge.setBackgroundResource(R.drawable.refund_button_bj);
		} else {
			vh.judge.setVisibility(View.GONE);
		}
//		if (list.get(paramInt).getOrderItemStatus().equals("normal")) {
//			
//			vh.orderItemStatus.setText("退款"); 
//			vh.orderItemStatus.setBackgroundColor(context.getResources().getColor(R.color.refund));
//		} else if (list.get(paramInt).getOrderItemStatus().equals("applyRefund")) {
//			vh.orderItemStatus.setEnabled(false);
//			vh.orderItemStatus.setText("退款中");
//			
//		} else if (list.get(paramInt).getOrderItemStatus().equals("applyReship")) {
//			vh.orderItemStatus.setEnabled(false);
//			vh.orderItemStatus.setText("退货中");
//			
//		} else if (list.get(paramInt).getOrderItemStatus().equals("refund")) {
//			vh.orderItemStatus.setEnabled(false);
//			vh.orderItemStatus.setText("退款完成");
//			
//		} else if (list.get(paramInt).getOrderItemStatus().equals("reship")) {
//			vh.orderItemStatus.setEnabled(false);
//			vh.orderItemStatus.setText("退货完成");
//			
//		}
		vh.judge.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent judgeIntent = new Intent(context, WriteJudgeActivity.class);
				judgeIntent.putExtra("orderId", orderId);
				judgeIntent.putExtra("product", list.get(paramInt).getProduct());
				context.startActivity(judgeIntent);
			}
		});
		vh.orderItemStatus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent refundIntent = new Intent(context, RefundActivity.class);
				OrderItemDto orderItemDto = list.get(paramInt);
				orderItemDto.setOrderId(orderId);
				refundIntent.putExtra("OrderItemDto", orderItemDto);
				context.startActivity(refundIntent);
//				pro.show();
//				refund(paramInt, vh.orderItemStatus);
			}
		});
//		vh.orderItemStatus.setText("￥" + list.get(paramInt).getProduct().getMarketPrice().toString());
		vh.quanlity.setText( "x" + (list.get(paramInt).getProductQuantity() < 1 ? 1 :list.get(paramInt).getProductQuantity() )+ "");
		Log.i("信息", list.get(paramInt).getProduct().getName() + "--" + list.get(paramInt).getProduct().getMarketPrice().toString() +"--" + (list.get(paramInt).getProductQuantity() < 1 ? 1 :list.get(paramInt).getProductQuantity() ));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      return paramView;
  }
  /**
   * 设置状态
   * 正常交易   退款状态                   退货状态                退款完成        退货完成
   *normal applyRefund   applyReship   refund    reship
   * @param status
   * @param textView
   */
  private void setText(String status,TextView textView){
	  Log.i("状态", status);
	  if (status.equals("normal")&&isRefund) {
			textView.setText("退款"); 
			textView.setBackgroundResource(R.drawable.refund_button_bj);
			
		} else if (status.equals("applyRefund")) {
			textView.setEnabled(false);
			textView.setBackground(null);
			textView.setText("退款中");
			
		} else if (status.equals("applyReship")) {
			textView.setEnabled(false);
			textView.setBackground(null);
			textView.setText("退货中");
			
		} else if (status.equals("refund")) {
			textView.setEnabled(false);
			textView.setBackground(null);
			textView.setText("退款完成");
			
		} else if (status.equals("reship")) {
			textView.setEnabled(false);
			textView.setBackground(null);
			textView.setText("退货完成");
			
		} else {
			textView.setVisibility(View.GONE);
		}
  }
 
  public void setListener(OperationListener paramOperationListener)
  {
    this.operationListener = paramOperationListener;
  }

  public  interface OperationListener
  {
    public  void operationOnclick(List<CartItemDto> list);
  }

  class ViewHolder
  {
    ImageView imageView;
    TextView name;
    TextView price;
    TextView orderItemStatus;
    TextView quanlity;
    TextView judge;
  }
}
