package com.searun.shop.adapter;

import java.util.List;

import com.searun.shop.R;
import com.searun.shop.data.MessageDto;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {
	
	List<MessageDto> list;
	Context context;
	LayoutInflater inflater;
	public MessageAdapter(List<MessageDto> list, Context context) {
		super();
		this.list = list;
		this.context = context;
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
				convertView = inflater.inflate(R.layout.item_message, parent, false);
				vh = new ViewHolder();
				vh.addressor = (TextView) convertView.findViewById(R.id.addressor);
				vh.title = (TextView) convertView.findViewById(R.id.title_message);
				vh.time = (TextView) convertView.findViewById(R.id.time);
				vh.content = (TextView) convertView.findViewById(R.id.content);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
				Drawable drawable = context.getResources().getDrawable(R.drawable.icon_red_point);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
				TextPaint tp = vh.addressor.getPaint();
				tp.setFakeBoldText(true);
				vh.addressor.setText(list.get(position).getFromMember() == null ? "管理员" :list.get(position).getFromMember().getUsername());
				vh.title.setText(list.get(position).getTitle());
				if (list.get(position).getIsRead()) {
					vh.title.setCompoundDrawables(null, null, null, null);
				} else {
					vh.title.setCompoundDrawables(drawable, null, null, null);
				}
				vh.content.setText(list.get(position).getContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return convertView;
	}
	class ViewHolder{
		TextView addressor;
		TextView title;
		TextView time;
		TextView content;
	}
}
