package com.searun.shop.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.searun.shop.R;
import com.searun.shop.activity.JudgeImageShowActivity;
import com.searun.shop.data.ProductImage;
import com.searun.shop.data.ReviewDto;
import com.searun.shop.toobject.JsonToProductImage;
import com.searun.shop.view.MyGridView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ReadJudgeAdapter extends BaseAdapter {

	List<ReviewDto> list;
	Context context;
	LayoutInflater inflater;
	DisplayImageOptions options;
	
	public ReadJudgeAdapter(List<ReviewDto> list, Context context,DisplayImageOptions options) {
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
		try {
			if (null == convertView) {
				vh = new ViewHolder();
				convertView = inflater.inflate(R.layout.item_judge_show, parent, false);
				vh.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar_item);
				vh.name = (TextView) convertView.findViewById(R.id.name);
				vh.content = (TextView) convertView.findViewById(R.id.content);
				vh.gv = (MyGridView) convertView.findViewById(R.id.item_judge_gv);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
				vh.ratingBar.setRating(Float.parseFloat(list.get(position).getScore()));
				vh.name.setText(list.get(position).getMember().getUsername());
				vh.content.setText(list.get(position).getContent());
				List<ProductImage> imagesList = JsonToProductImage.parserLoginJson(list.get(position).getReviewImageListStore());
				final List<ProductImage> list2 = imagesList;
				if (!imagesList.isEmpty()) {
					vh.gv.setAdapter(new JudgeImageAdapter(imagesList, context,options));
					vh.gv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(context, JudgeImageShowActivity.class);
							Log.i("size", list2.size()+ "," + position);
							intent.putExtra("big", position);
							intent.putExtra("list", (Serializable)list2);
							context.startActivity(intent);
						}
					});
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return convertView;
	}
	class ViewHolder{
		RatingBar ratingBar;
		TextView name;
		TextView content;
		MyGridView gv;
	}
	
}
