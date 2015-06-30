package com.searun.shop.fragment;

import com.searun.shop.R;
import com.searun.shop.util.ClickInterface;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CartEmptyFragment extends Fragment implements OnClickListener{
	private static  CartEmptyFragment instance;
	private Button buy;
	
	public static CartEmptyFragment getInstance(){
		if (null == instance) {
			instance = new CartEmptyFragment();
		}
		return instance;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.child_fragment_cart_empty, container, false);
		findView(view);
		return view;
	}
	ClickInterface ci;
	private void findView(View view){
		
		buy = ((Button) view.findViewById(R.id.buy));
		buy.setOnClickListener(this);
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			ci = (ClickInterface) activity;
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			throw new ClassCastException(activity.toString() + " must be implements ClickInterface");
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buy:
			ci.onClick(1);
			break;

		default:
			break;
		}
	}
}
