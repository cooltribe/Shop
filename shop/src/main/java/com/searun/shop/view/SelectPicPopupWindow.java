package com.searun.shop.view;

import com.searun.shop.R;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;


public class SelectPicPopupWindow extends PopupWindow {

	private Button first_btn, secend_btn, third_btn;
	private View mMenuView;

	public SelectPicPopupWindow(Activity context) {
		super(context);
		mMenuView = LayoutInflater.from(context).inflate(
				R.layout.custom_view_single_select_alertdialog2, null);
		first_btn = (Button) mMenuView
				.findViewById(R.id.single_select_alertdialog2_first_btn);
		secend_btn = (Button) mMenuView
				.findViewById(R.id.single_select_alertdialog2_secend_btn);
		third_btn = (Button) mMenuView
				.findViewById(R.id.single_select_alertdialog2_third_btn);
		// 取消按钮
		// third_btn.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// // 销毁弹出框
		// dismiss();
		// }
		// });
		// 设置按钮监听
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(
						R.id.single_select_button_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});

	}

	/**
	 * 为第一个按钮添加文字
	 * 
	 * @param content
	 */
	public void setFirstButtonContent(String content) {
		first_btn.setText(content);
	}

	/**
	 * 为第一个按钮添加监听
	 * 
	 * @param listener
	 */
	public void setFirstButtonOnClickListener(OnClickListener listener) {
		first_btn.setOnClickListener(listener);
	}

	/**
	 * 为第二个按钮添加文字
	 * 
	 * @param content
	 */
	public void setSecendButtonContent(String content) {
		secend_btn.setText(content);
	}

	/**
	 * 为第二个按钮添加监听
	 * 
	 * @param listener
	 */
	public void setSecendButtonOnClickListener(OnClickListener listener) {
		secend_btn.setOnClickListener(listener);
	}

	/**
	 * 为第三个按钮添加文字
	 * 
	 * @param content
	 */
	public void setThirdButtonContent(String content) {
		third_btn.setText(content);
	}

	/**
	 * 为第三个按钮添加监听
	 * 
	 * @param listener
	 */
	public void setThirdButtonOnClickListener(OnClickListener listener) {
		third_btn.setOnClickListener(listener);
	}
}
