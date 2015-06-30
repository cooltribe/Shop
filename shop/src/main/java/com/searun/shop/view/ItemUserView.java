package com.searun.shop.view;

import com.searun.shop.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemUserView extends LinearLayout {
	private View topLine;
	private View bottomLine;
	private ImageView icon;
	private TextView textView;
	private Button arrow;
	private TextView tv2;

	public ItemUserView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	public ItemUserView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	public ItemUserView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	private void initView(Context context){
		View view = LayoutInflater.from(context).inflate(R.layout.item_user, this);
		topLine = view.findViewById(R.id.line_top);
		bottomLine = view.findViewById(R.id.line_bottom);
		icon = (ImageView) view.findViewById(R.id.user_icon);
		textView = (TextView) view.findViewById(R.id.user_tv);
		tv2 = (TextView) view.findViewById(R.id.user_tv2);
		arrow = (Button) view.findViewById(R.id.user_enter);
	}
	public void setTv(String text){
		tv2.setText(text);
	}
	public void setTvVisibility(int visibility){
		tv2.setVisibility(visibility);
	}
	public void setTopLineVisibility(int visibility){
		topLine.setVisibility(visibility);
	}
	public void setBottomLineVisibility(int visibility){
		bottomLine.setVisibility(visibility);
	}
	public void setIcon(int resId){
		icon.setImageResource(resId);
	}
	public void setText(String text){
		textView.setText(text);
	}
	public void setArrow(int resId){
		arrow.setBackgroundResource(resId);
	}
}
