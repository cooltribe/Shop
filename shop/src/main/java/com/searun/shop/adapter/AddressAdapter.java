package com.searun.shop.adapter;

import java.util.List;

import com.searun.shop.R;
import com.searun.shop.data.ReceiverDto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class AddressAdapter extends BaseAdapter {
	List<ReceiverDto> list;
	Context context;
	LayoutInflater inflater;
	public AddressAdapter(List<ReceiverDto> list, Context context) {
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
	public ReceiverDto getItem(int position) {
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
			ReceiverDto rd = getItem(position);
			ViewHolder vh;
			if (null == convertView) {
				vh = new ViewHolder();
				convertView = inflater.inflate(R.layout.item_address, parent, false);
				
				vh.addressess = (TextView) convertView.findViewById(R.id.addressee);
				vh.mobile = (TextView) convertView.findViewById(R.id.phone);
				vh.city = (TextView) convertView.findViewById(R.id.city);
				vh.street = (TextView) convertView.findViewById(R.id.street);
				vh.postCode = (TextView) convertView.findViewById(R.id.postcode);
				vh.checkBox = (CheckBox) convertView.findViewById(R.id.isdefault);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
				vh.addressess.setText(rd.getName());
				vh.mobile.setText(rd.getPhone() == null ? rd.getMobile() : rd.getPhone());
				vh.city.setText(rd.getAreaPath());
				vh.street.setText(rd.getAddress());
				vh.postCode.setText(rd.getZipCode());
				vh.checkBox.setChecked(rd.getIsDefault());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder {
		TextView addressess;
		TextView mobile;
		TextView city;
		TextView street;
		TextView postCode;
		CheckBox checkBox;
	}
}
