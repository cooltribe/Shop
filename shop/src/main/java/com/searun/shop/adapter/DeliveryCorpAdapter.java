package com.searun.shop.adapter;

import java.util.List;

import com.searun.shop.R;
import com.searun.shop.data.DeliveryCorpDto;
import com.searun.shop.data.PaymentConfigDto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DeliveryCorpAdapter extends BaseAdapter {
	
	List<DeliveryCorpDto> list;
	Context context;
	LayoutInflater inflater;
	public DeliveryCorpAdapter(List<DeliveryCorpDto> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
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
				convertView = inflater.inflate(R.layout.item_singel_text, parent, false);
				vhHolder.textView = (TextView) convertView.findViewById(R.id.textview);
				convertView.setTag(vhHolder);
			} else {
				vhHolder = (ViewHolder) convertView.getTag();
			}
				vhHolder.textView.setText(list.get(position).getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	class ViewHolder{
		TextView textView;
	}
}
