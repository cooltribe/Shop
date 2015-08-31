package com.searun.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.searun.shop.R;
import com.searun.shop.activity.ProductListActivity;
import com.searun.shop.data.ProductCategoryDto;

import java.util.List;

public class ClassificationRightAdapter extends BaseAdapter {
	List<ProductCategoryDto> list;
	Context context;
	LayoutInflater inflater;
	public ClassificationRightAdapter(List<ProductCategoryDto> list, Context context) {
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
		try {
			ViewHolder vh;
			if (null == convertView) {
				convertView = inflater.inflate(R.layout.item_sort_right, parent, false);
				vh = new ViewHolder();
				vh.textView = (TextView) convertView.findViewById(R.id.item);
				
				vh.gridView = (GridView) convertView.findViewById(R.id.sort_grid);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
//			vh.textView.getPaint().setFakeBoldText(true);
				vh.textView.setText(list.get(position).getName());
				final List<ProductCategoryDto> cellList = list.get(position).getChildren();
				if (cellList.size() > 0) {
					vh.gridView.setAdapter(new CellClassficationAdapter(cellList, context));
				} else {
					vh.gridView.setVisibility(View.GONE);
				}
//				Utility.setGridViewHeightBasedOnChildren(vh.gridView);
				vh.gridView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context, ProductListActivity.class);
						intent.putExtra("id", cellList.get(position).getId());
						intent.putExtra("name", cellList.get(position).getName());
						context.startActivity(intent);
					
					}
				});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder{
		TextView textView;
		GridView gridView;
	}
}
