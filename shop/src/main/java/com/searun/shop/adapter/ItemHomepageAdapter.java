package com.searun.shop.adapter;

import android.content.Context;
import android.hardware.Camera.Size;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.searun.shop.R;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.ProductDto;
import com.searun.shop.data.ProductImage;
import com.searun.shop.toobject.JsonToProductImage;
import com.searun.shop.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class ItemHomepageAdapter extends BaseAdapter {
	private Context context;
	LayoutInflater inflater;
	private List<ProductDto> list;
	DisplayImageOptions options;
	private List<ProductImage> imageList;

	public ItemHomepageAdapter(Context context, List<ProductDto> paramList, DisplayImageOptions options) {
		this.context = context;
		this.list = paramList;
		this.inflater = LayoutInflater.from(context);
		this.options = options;
		imageList = new ArrayList<ProductImage>();
	}

	private List<ProductDto> getList() {
		List<ProductDto> localArrayList = new ArrayList<ProductDto>();
		Log.i("长度", list.size() + "");
		if (list.size() > 5) {
			for (int i = 0; i < 6; i++) {
				localArrayList.add((ProductDto) list.get(i));
			}
		} else {
			localArrayList = list;
		}
		return localArrayList;
	}

	public int getCount() {
		return getList().size();
	}

	public Object getItem(int paramInt) {
		return getList().get(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		try {
			ViewHolder localViewHolder = null;
			if (paramView == null) {
				localViewHolder = new ViewHolder();
				paramView = inflater.inflate(R.layout.item_homepage, paramViewGroup, false);
				localViewHolder.imageView = ((ImageView) paramView.findViewById(R.id.goods_pic));
				localViewHolder.name = ((TextView) paramView.findViewById(R.id.goods_name));
				localViewHolder.price = ((TextView) paramView.findViewById(R.id.goods_price));
				localViewHolder.yuanJia = ((TextView) paramView.findViewById(R.id.yuanjia));
				localViewHolder.yuanJia.getPaint().setFlags(16);
				paramView.setTag(localViewHolder);
			} else {
				localViewHolder = (ViewHolder) paramView.getTag();
			}
			try {
				imageList = JsonToProductImage.parserLoginJson(getList().get(paramInt).getProductImageListStore());
				if (null != imageList) {
					
					Log.i("图片路径"+paramInt, HttpUtil.IMG_PATH + imageList.get(0).getSmallProductImagePath());
				ImageLoader.getInstance().displayImage(HttpUtil.IMG_PATH  + imageList.get(0).getSmallProductImagePath(), localViewHolder.imageView,
						options);
				} else {
					localViewHolder.imageView.setImageResource(R.drawable.mrpic_little);
				}
				localViewHolder.name.setText((getList().get(paramInt)).getName());
				localViewHolder.price.setText("￥" + (getList().get(paramInt)).getPrice());
				localViewHolder.yuanJia.setText("￥" + (getList().get(paramInt)).getMarketPrice());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paramView;

	}

	class ViewHolder {
		View bottomLine;
		ImageView imageView;
		View leftLine;
		TextView name;
		TextView price;
		View rightLine;
		View topLine;
		TextView yuanJia;
	}
}
