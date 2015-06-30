package com.searun.shop.view;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.searun.shop.R;
import com.searun.shop.data.ProductDto;
import com.searun.shop.data.ProductImage;
import com.searun.shop.util.HttpUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class InfoImageAdapter extends BaseAdapter
{
	private Context mContext;
	private LayoutInflater mInflater;
	private  List<ProductImage> list;
	private DisplayImageOptions options;
	
	
	public InfoImageAdapter(Context mContext, List<ProductImage> list, DisplayImageOptions options) {
		super();
		this.mContext = mContext;
		this.list = list;
		this.options = options;
		this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

//	public InfoImageAdapter(Context context ,List<ProductImage> list ,DisplayImageOptions options) {
//		this.mContext = context;
//		this.list =list;
//		this.options = options;
//		
//	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;   //返回很大的值使得getView中的position不断增大来实现循环
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.image_item, parent ,false);
			vh = new ViewHolder();
			vh.imageView = (ImageView) convertView.findViewById(R.id.imgView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		Log.i("是否为空", (list == null) + "");
		String imgPath = list == null ? "" :list.get(position % list.size()).getBigProductImagePath();
		Log.i("图片路径", imgPath);
		if (!list.isEmpty()) {
			ImageLoader.getInstance().displayImage(HttpUtil.IMG_PATH + (list == null ? "" :imgPath), vh.imageView, options);
		} else {
			vh.imageView.setImageResource(R.drawable.mrpic);
		}
		
//		vh.imageView.setImageResource(ids[position%ids.length]);
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(mContext,DetailActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putInt("image_id", ids[position%ids.length]);
//				intent.putExtras(bundle);
//				mContext.startActivity(intent);
			}
		});
		return convertView;
	}
	class ViewHolder{
		ImageView imageView;
	}
}
