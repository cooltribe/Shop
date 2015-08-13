package com.searun.shop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.searun.shop.R;
import com.searun.shop.data.CartItemDto;
import com.searun.shop.data.ProductImage;
import com.searun.shop.toobject.JsonToProductImage;
import com.searun.shop.util.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CartPayAdapter extends BaseAdapter
{
  Context context;
  LayoutInflater inflater;
  List<CartItemDto> list;
  OperationListener operationListener;
  List<CartItemDto> confirmList;
  boolean isSHow;
  private static HashMap<Integer, Boolean> isSelected;
  private DisplayImageOptions options;

  public CartPayAdapter(List<CartItemDto> paramList, Context paramContext, boolean isShow)
  {
    this.list = paramList;
    this.context = paramContext;
    this.inflater = LayoutInflater.from(paramContext);
    this.confirmList = new ArrayList<CartItemDto>();
    this.isSHow = isShow;
    isSelected = new HashMap<Integer, Boolean>();
    this.options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.mrpic_little)
				.showImageForEmptyUri(R.drawable.mrpic_little)
				.showImageOnFail(R.drawable.mrpic_little)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.build();
    initDate();
  }

  
public static HashMap<Integer, Boolean> getIsSelected() {
	return isSelected;
}


public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
	CartPayAdapter.isSelected = isSelected;
}


//初始化isSelected的数据
  private void initDate(){
      for(int i=0; i<list.size();i++) {
          getIsSelected().put(i,false);
      }
  }

  public int getCount()
  {
    return this.list.size();
  }

  public Object getItem(int paramInt)
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
		  paramView = this.inflater.inflate(R.layout.item_cart_pay, paramViewGroup, false);
		  vh.checkBox = ((CheckBox)paramView.findViewById(R.id.ischecked));
		  if (isSHow) {
			vh.checkBox.setVisibility(View.VISIBLE);
		} else {
			vh.checkBox.setVisibility(View.GONE);
		}
		  vh.imageView = ((ImageView)paramView.findViewById(R.id.goods_pic));
		  vh.name = ((TextView)paramView.findViewById(R.id.name));
		  vh.price = ((TextView)paramView.findViewById(R.id.price));
		  vh.yuanjia = (TextView) paramView.findViewById(R.id.yuanjia);
		  vh.yuanjia.getPaint().setFlags(16);
		  vh.quanlity = ((TextView)paramView.findViewById(R.id.quanlity));
		  paramView.setTag(vh);
		  
		} else {
			vh = (ViewHolder)paramView.getTag();
		}
		List<ProductImage> imagesList = JsonToProductImage.parserLoginJson(list.get(paramInt).getProduct().getProductImageListStore());
		if (imagesList != null) {
			ImageLoader.getInstance().displayImage(HttpUtil.BASE_URL + imagesList.get(0).getSmallProductImagePath(), vh.imageView, options);
		} else {
			vh.imageView.setImageResource(R.drawable.mrpic_little);
		}
		Log.i("名字", list.get(paramInt).getProduct().getName());
		vh.name.setText(list.get(paramInt).getProduct().getName());
		vh.price.setText("￥" + list.get(paramInt).getProduct().getPrice().toString());
		vh.yuanjia.setText("￥" + list.get(paramInt).getProduct().getMarketPrice().toString());
		vh.quanlity.setText( "x" + (list.get(paramInt).getQuantity() < 1 ? 1 :list.get(paramInt).getQuantity() )+ "");
		vh.checkBox.setChecked(getIsSelected().get(paramInt));
//		if (getIsSelected().get(paramInt)) {
//			confirmList.add(list.get(paramInt));
//			operationListener.operationOnclick(confirmList);
//		} 
//		vh.checkBox.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (vh.checkBox.isChecked()) {
//					confirmList.add(list.get(paramInt));
//				} else {
//					confirmList.remove(list.get(paramInt));
//				}
//				operationListener.operationOnclick(confirmList);
//			}
//		});
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

 public class ViewHolder
  {
    public CheckBox checkBox;
 public ImageView imageView;
 public TextView name;
 public TextView price;
 public TextView yuanjia;
 public TextView quanlity;
  }
}
