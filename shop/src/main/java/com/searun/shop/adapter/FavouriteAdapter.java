package com.searun.shop.adapter;

import android.content.Context;
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
import com.searun.shop.data.ProductDto;
import com.searun.shop.data.ProductImage;
import com.searun.shop.toobject.JsonToProductImage;
import com.searun.shop.util.HttpUtil;

import java.util.List;

public class FavouriteAdapter extends BaseAdapter {
	List<ProductDto> list;
	Context context;
	LayoutInflater inflater;
	private DisplayImageOptions options;

	public FavouriteAdapter(List<ProductDto> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.mrpic_little)
				.showImageForEmptyUri(R.drawable.mrpic_little)
				.showImageOnFail(R.drawable.mrpic_little)
				.cacheInMemory(false)
				.cacheOnDisk(true).build();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		try {
			ViewHolder vh;
			if (convertView == null) {
				vh = new ViewHolder();
				convertView = this.inflater.inflate(R.layout.item_cart_pay, parent, false);
				vh.checkBox = ((CheckBox) convertView.findViewById(R.id.ischecked));
				vh.checkBox.setVisibility(View.GONE);
				vh.imageView = ((ImageView) convertView.findViewById(R.id.goods_pic));
				vh.name = ((TextView) convertView.findViewById(R.id.name));
				vh.price = ((TextView) convertView.findViewById(R.id.price));
				vh.yuanjia = (TextView) convertView.findViewById(R.id.yuanjia);
				vh.yuanjia.getPaint().setFlags(16);
				vh.quanlity = ((TextView) convertView.findViewById(R.id.quanlity));
				vh.quanlity.setVisibility(View.GONE);
				convertView.setTag(vh);

			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			List<ProductImage> imagesList = JsonToProductImage.parserLoginJson(list.get(position).getProductImageListStore());
			if (null != imagesList) {
				ImageLoader.getInstance().displayImage(HttpUtil.BASE_URL + imagesList.get(0).getSmallProductImagePath(), vh.imageView, options);
			} else {
				vh.imageView.setImageResource(R.drawable.mrpic_little);
			}
			vh.name.setText(list.get(position).getName());
			vh.price.setText("￥" + list.get(position).getPrice().toString());
			vh.yuanjia.setText("￥" + list.get(position).getMarketPrice().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder {
		CheckBox checkBox;
		ImageView imageView;
		TextView name;
		TextView price;
		TextView yuanjia;
		TextView quanlity;
	}
}
