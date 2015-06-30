package com.searun.shop.adapter;

import java.util.List;

import com.searun.shop.R;
import com.searun.shop.data.ProductCategoryDto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CellClassficationAdapter extends BaseAdapter {

	List<ProductCategoryDto> List;
	Context context;
	LayoutInflater inflater;
	public CellClassficationAdapter(java.util.List<ProductCategoryDto> list, Context context) {
		super();
		List = list;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return List.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return List.get(position);
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
				convertView = inflater.inflate(R.layout.item_cell, parent, false);
				vh = new ViewHolder();
				vh.textView = (TextView) convertView.findViewById(R.id.cell_name);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			vh.textView.setText(List.get(position).getName());
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
