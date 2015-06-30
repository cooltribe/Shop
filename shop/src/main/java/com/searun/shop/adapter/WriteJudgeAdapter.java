package com.searun.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.searun.shop.R;
import com.searun.shop.activity.WriteJudgeActivity;
import com.searun.shop.application.MyApplication;
import com.searun.shop.data.CartItemDto;
import com.searun.shop.data.OrderItemDto;
import com.searun.shop.data.ProductDto;
import com.searun.shop.data.ProductImage;
import com.searun.shop.toobject.JsonToProductImage;
import com.searun.shop.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

public class WriteJudgeAdapter extends BaseAdapter
{
  Context context;
  LayoutInflater inflater;
  List<OrderItemDto> list;
  OperationListener operationListener;
  private DisplayImageOptions options;
  String orderId;
  public WriteJudgeAdapter(List<OrderItemDto> paramList, Context paramContext ,String orderId)
  {
    this.list = paramList;
    this.context = paramContext;
    this.inflater = LayoutInflater.from(paramContext);
    this.orderId = orderId;
    	options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.mrpic_little)
				.showImageForEmptyUri(R.drawable.mrpic_little)
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
		  paramView = this.inflater.inflate(R.layout.item_judge, paramViewGroup, false);
		  vh.judgeButton = (Button) paramView.findViewById(R.id.judge_button);
		  vh.imageView = ((ImageView)paramView.findViewById(R.id.goods_pic));
		  vh.name = ((TextView)paramView.findViewById(R.id.name));
		  vh.price = ((TextView)paramView.findViewById(R.id.price));
		  vh.yuanjia = (TextView) paramView.findViewById(R.id.yuanjia);
		  vh.yuanjia.getPaint().setFlags(16);
		  paramView.setTag(vh);
		  
		} else {
			vh = (ViewHolder)paramView.getTag();
		}
		
		List<ProductImage> imagesList = JsonToProductImage.parserLoginJson(list.get(paramInt).getProduct().getProductImageListStore());
		if (null != imagesList) {
			ImageLoader.getInstance().displayImage(HttpUtil.IMG_PATH + imagesList.get(0).getSmallProductImagePath(), vh.imageView, options);
		} else {
			vh.imageView.setImageResource(R.drawable.mrpic_little);
		}
		vh.name.setText(list.get(paramInt).getProduct().getName());
		vh.price.setText("￥" + list.get(paramInt).getProduct().getPrice().toString());
		vh.yuanjia.setText("￥" + list.get(paramInt).getProduct().getMarketPrice().toString());
		vh.judgeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, WriteJudgeActivity.class);
				intent.putExtra("orderId", orderId);
				intent.putExtra("product", list.get(paramInt).getProduct());
				context.startActivity(intent);
			}
		});
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      return paramView;
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
	Button judgeButton;
    ImageView imageView;
    TextView name;
    TextView price;
    TextView yuanjia;
  }
}
