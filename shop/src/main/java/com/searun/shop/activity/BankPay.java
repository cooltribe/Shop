package com.searun.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.searun.shop.R;
import com.searun.shop.data.OrderDto;
import com.searun.shop.data.PaymentConfigDto;

public class BankPay extends Activity implements OnClickListener{
	private WebView description;
	private TextView notice;
	private Button confirm;
	private Intent intent;
	private OrderDto orderDto;
	private Button back;
	private TextView title;
	private PaymentConfigDto paymentConfigDto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_bankpay);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		init();
		findView();
	}

	private void init(){
		intent = getIntent();
		orderDto = (OrderDto) intent.getSerializableExtra("order");
		paymentConfigDto = (PaymentConfigDto) intent.getSerializableExtra("paymentConfigDto");

	}
	private void findView() {
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);
		title = (TextView) findViewById(R.id.title);
		title.setText("付款信息");
		findViewById(R.id.other).setVisibility(View.GONE);
		description = (WebView) findViewById(R.id.bank_description);
		Log.i("付款信息付款信息付款信息付款信息",orderDto.getPaymentConfig().getDescription());
		if(null != orderDto){
			description.loadData(paymentConfigDto.getDescription(), "text/html;charset=UTF-8", null);
		}
		notice = (TextView) findViewById(R.id.notice);
		notice.setText("请根据您选择的支付方式来选择银行汇款。汇款以后，请立即通知我们，支付总金额￥" + orderDto.getTotalAmount().toString() + "元");
		confirm = (Button) findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		case R.id.confirm:
			Intent intent = new Intent(BankPay.this, MyOrder.class);
			intent.putExtra("tag", 3);
			startActivity(intent);
			
			finish();
			break;
		}
	}
}
