package com.searun.shop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.searun.shop.data.ProductImage;
import com.searun.shop.util.HttpUtil;

import java.util.List;

public class GalleryAdapter extends BaseAdapter {
	List<ProductImage> list;
	Context context;
	DisplayImageOptions options;
	public GalleryAdapter(List<ProductImage> list, Context context,DisplayImageOptions options) {
		super();
		this.list = list;
		this.context = context;
		this.options = options;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
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
		ImageView imageView = new ImageView(context);
		String imgPath = list == null ? "" :list.get(position % list.size()).getBigProductImagePath();
		Log.i("图片路径", imgPath);
		ImageLoader.getInstance().displayImage(HttpUtil.BASE_URL + (list == null ? "" :imgPath), imageView, options);
//		imageView.setImageResource(list.get(position % list.size()));
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		imageView.setLayoutParams(new Gallery.LayoutParams(
				Gallery.LayoutParams.FILL_PARENT,
				Gallery.LayoutParams.FILL_PARENT));
		return imageView;
	}

}
