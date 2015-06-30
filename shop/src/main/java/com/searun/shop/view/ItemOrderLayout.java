package com.searun.shop.view;

import com.searun.shop.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class ItemOrderLayout extends LinearLayout {

	private RadioButton titleRB;
	private View bottomView;
	private View view;
	public ItemOrderLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	public ItemOrderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	public ItemOrderLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	private void initView(Context context){
		view = LayoutInflater.from(context).inflate(R.layout.item_order_layout, this);
		titleRB = (RadioButton) view.findViewById(R.id.title_rb);
		bottomView = view.findViewById(R.id.bottom_view);
		if (titleRB.isChecked()) {
			bottomView.setVisibility(View.VISIBLE);
		} else {
			bottomView.setVisibility(View.INVISIBLE);
		}
	}
	public void setTitle(String title){
		titleRB.setText(title);
	}
	public boolean setChecked(){
		return titleRB.isChecked();
		
	}
}
