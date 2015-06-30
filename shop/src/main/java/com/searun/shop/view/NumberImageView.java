package com.searun.shop.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.searun.shop.R;
public class NumberImageView extends LinearLayout {

	private ImageView icon;
	private TextView number;
	private TextView title;

	public NumberImageView(Context context) {
		super(context);
		initView(context);
	}

	public NumberImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		View layout = LayoutInflater.from(context).inflate(
				R.layout.activity_number_textview, this);
		// TypedArray array = context.obtainStyledAttributes(attrs,
		// R.styleable.NumberImageView);
		icon = (ImageView) layout.findViewById(R.id.number_textview_icon);
		// icon.setBackground(array
		// .getDrawable(R.styleable.NumberImageView_number_imageview_background));
		number = (TextView) layout.findViewById(R.id.number_textview_number);
		// number.setText(array
		// .getString(R.styleable.NumberImageView_number_imageview_number));
		title = (TextView) layout.findViewById(R.id.number_textview_title);
		// title.setText(array
		// .getString(R.styleable.NumberImageView_number_imageview_title));
		// array.recycle();
	}

	public void setBackground(int backgroudID) {
		icon.setBackgroundResource(backgroudID);
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public void setNumber(String number) {
		if (TextUtils.isEmpty(number)) {
			this.number.setVisibility(View.GONE);
		} else {
			this.number.setVisibility(View.VISIBLE);
		}
		this.number.setText(number);
	}
}
