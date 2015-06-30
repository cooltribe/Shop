package com.searun.shop.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.searun.shop.R;

public class MyEdittext extends LinearLayout {

    private TextView tv;
    private TextView tv1;
    private EditText et;

    public MyEdittext(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public MyEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public MyEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    private void initView(Context context) {
        View layout = LayoutInflater.from(context).inflate(R.layout.my_edittext, this, true);
        tv = (TextView) layout.findViewById(R.id.my_edittext_tv);
        et = (EditText) layout.findViewById(R.id.my_edittext_et);
        tv1 = (TextView) layout.findViewById(R.id.my_edittext_tv_pre);
    }

    public void setTv(String string) {
        tv.setText(string);
    }

    /**
     *
     * 设置et和tv1是否显示
     * @param etVisibility
     * @param tvVisibility
     */
    public void setVisibility(int etVisibility, int tvVisibility){
        et.setVisibility(etVisibility);
        tv1.setVisibility(tvVisibility);
    }
    public void setEt(String string) {
        et.setText(string);
        tv1.setText(string);
    }

    public String getEdittext() {

        return et.getText().toString();

    }
    public void setDrawable(Drawable drawable) {
        tv.setCompoundDrawables(drawable, null, null, null);
    }

    public void setType(int inputType) {
        et.setInputType(inputType);
    }

}
