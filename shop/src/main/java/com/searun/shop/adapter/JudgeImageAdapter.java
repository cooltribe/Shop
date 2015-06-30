package com.searun.shop.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.searun.shop.R;
import com.searun.shop.data.ImageDto;
import com.searun.shop.data.ProductImage;
import com.searun.shop.util.CommonUtils;
import com.searun.shop.util.HttpUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class JudgeImageAdapter extends BaseAdapter {
	
	List<ProductImage> list;
	Context context;
	LayoutInflater inflater;
	DisplayImageOptions options;
	public JudgeImageAdapter(List<ProductImage> list, Context context,DisplayImageOptions options) {
		super();
		this.list = list;
		this.context = context;
		this.options = options;
		this.inflater = LayoutInflater.from(context);
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
		ViewHolder vh;
		if ( null == convertView) {
			vh = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_judge_gv_image, parent, false);
			vh.imageView = (ImageView) convertView.findViewById(R.id.judge_image);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage(HttpUtil.IMG_PATH + list.get(position).getSmallProductImagePath(), vh.imageView, options);
//			vh.imageView.setImageBitmap(CommonUtils.getBitmapFromByte(list.get(position).getFile()));
		return convertView;
	}
	class ViewHolder{
		ImageView imageView;
	}
}
