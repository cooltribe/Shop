package com.searun.shop.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.searun.shop.R;
import com.searun.shop.data.ProductCategoryDto;
import com.searun.shop.util.HttpUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ClassificationLeftAdapter extends BaseAdapter {
	List<ProductCategoryDto> list;
	Context context;
	LayoutInflater inflater;
	DisplayImageOptions options;
	AnimateFirstDisplayListener animateFirstDisplayListener;
	public ClassificationLeftAdapter(List<ProductCategoryDto> list,
			Context context ,DisplayImageOptions options) {
		super();
		this.list = list;
		this.context = context;
		this.options = options;
		this.inflater = LayoutInflater.from(context);
		this.animateFirstDisplayListener = new AnimateFirstDisplayListener();
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
			if (null == convertView) {
				vh = new ViewHolder();
				convertView = inflater.inflate(R.layout.item_class_left, parent, false);
//			vh.pic = (ImageView) convertView.findViewById(R.id.sortpic);
//			vh.pic.setVisibility(View.GONE);
				vh.name = (TextView) convertView.findViewById(R.id.sortname);
				vh.bottomLine = convertView.findViewById(R.id.bottom_line);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
//			System.err.println("图片路径：" + HttpUtil.IMG_PATH + list.get(position).getImage());
//			ImageLoader.getInstance().displayImage(HttpUtil.IMG_PATH + list.get(position).getImage(), vh.pic, options);
			System.out.println("xxxxxxxxxxxxxxxxx"+list.get(position).getName());
				vh.name.setText(list.get(position).getName());
				if (position == selectItem) {
					vh.name.setTextColor(Color.rgb(0xcc, 0x00, 0x33));
					vh.bottomLine.setVisibility(View.VISIBLE);
				} else {
					vh.name.setTextColor(Color.rgb(0x88, 0x88, 0x88));
					vh.bottomLine.setVisibility(View.INVISIBLE);
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
	public void serSelectItem(int selectItem){
		this.selectItem = selectItem;
	}
	private int selectItem = 0;
	class ViewHolder{
//		ImageView pic;
		TextView name;
		View bottomLine;
	}
}
