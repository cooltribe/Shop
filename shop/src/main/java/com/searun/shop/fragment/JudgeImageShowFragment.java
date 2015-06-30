package com.searun.shop.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.searun.shop.R;
import com.searun.shop.data.ProductImage;
import com.searun.shop.util.HttpUtil;

public class JudgeImageShowFragment extends Fragment {
	private ProductImage productImage;
	private DisplayImageOptions options;

	@SuppressLint("ValidFragment")
	public JudgeImageShowFragment(ProductImage productImage,DisplayImageOptions options) {
		super();
		this.productImage = productImage;
		this.options = options;
	}
	public JudgeImageShowFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_judge_image_show, container, false);
		ImageView imageView = (ImageView) view.findViewById(R.id.judge_show_image);
		if (null != productImage.getBigProductImagePath() && !productImage.getBigProductImagePath().equals("")) {
			ImageLoader.getInstance().displayImage(HttpUtil.IMG_PATH + (productImage == null ? "" :productImage.getBigProductImagePath()), imageView, options);
		} else {
			imageView.setImageResource(R.drawable.mrpic);
		}
		return view;
	}
}
