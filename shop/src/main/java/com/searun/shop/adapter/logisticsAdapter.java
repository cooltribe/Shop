package com.searun.shop.adapter;

import java.util.ArrayList;
import java.util.List;

import com.searun.shop.R;
import com.searun.shop.data.AreaDto;
import com.searun.shop.data.KuaidiTracking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class logisticsAdapter extends BaseAdapter {
	List<KuaidiTracking> list;
	Context context;
	LayoutInflater inflater;
	public logisticsAdapter(List<KuaidiTracking> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	private List<KuaidiTracking> createList(List<KuaidiTracking> kList){
		List<KuaidiTracking> list = new ArrayList<KuaidiTracking>();
		if (kList.isEmpty()) {
			KuaidiTracking kuaidiTracking = new KuaidiTracking();
			kuaidiTracking.setContent("暂时没有相关记录");
			kuaidiTracking.setTime("");
			list.add(kuaidiTracking);
		} else {
			list = kList;
		}
		return list;
		
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
			ViewHolder vhHolder;
			if (null == convertView) {
				vhHolder = new ViewHolder();
				convertView = inflater.inflate(R.layout.item_singel_text_left, parent, false);
				vhHolder.tiao = (TextView) convertView.findViewById(R.id.tiao);
				vhHolder.icon = (ImageView) convertView.findViewById(R.id.imageView1);
				vhHolder.textView = (TextView) convertView.findViewById(R.id.textview);
				vhHolder.time = (TextView) convertView.findViewById(R.id.time);
				convertView.setTag(vhHolder);
			} else {
				vhHolder = (ViewHolder) convertView.getTag();
			}	
				if (position + 1 == list.size()) {
					vhHolder.icon.setImageResource(R.drawable.star_empty);
				} else {
					vhHolder.icon.setImageResource(R.drawable.star_full);
				}
				vhHolder.textView.setText(list.get(position).getContent());
				vhHolder.time.setText(list.get(position).getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	class ViewHolder{
		TextView tiao;
		ImageView icon;
		TextView textView;
		TextView time;
	}
}
