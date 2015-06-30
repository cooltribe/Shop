package com.searun.shop.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.alipay.sdk.app.PayTask;
import com.searun.shop.R;
import com.searun.shop.alipay.PayResult;
import com.searun.shop.alipay.SignUtils;
import com.searun.shop.data.OrderDto;
import com.searun.shop.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AliPay extends Activity implements OnClickListener{
	// 商户PID
		// 商户PID(合作身份者id，以2088开头的16位纯数字，这个你申请支付宝签约成功后就会看见)
		public static final String PARTNER = "2088811454929814";
		// 商户收款账号
		// 商户收款账号(这里填写收款支付宝账号，即你付款后到账的支付宝账号)
		public static final String SELLER = "searunbugbase@163.com";
		// 商户私钥，pkcs8格式
		// 商户私钥，pkcs8格式(商户私钥，自助生成，即rsa_private_key.pem中去掉首行，最后一行，空格和换行最后拼成一行的字符串，
		// rsa_private_key.pem这个文件等你申请支付宝签约成功后，按照文档说明你会生成的,如果android版本太高，这里要用PKCS8格式用户私钥，
		// 不然调用不会成功的，那个格式你到时候会生成的，表急。)
		public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN5NjU42gne7LGT3id/xXogwWN4DTG70XiixNOAeWoQIqu3rB9uFW747Xw3EFUT95cp37bQAoSfgoJxJZ/4IBrzx527YCsq1/3yVcFKzeI0s3smmw8LkupHnxd5CEKluP5GvZ3W/9hFT/YaDkBEqPBDAhKAKZizoYJIKqdYDxkTPAgMBAAECgYA7reIzjxqzfgJLENFo12mjcidJYuVQHDZzAi/JwxxVueX5fVFcs46PoWzBS1TScr8P/eZInqqlA/7aNjK+1fTIoKEqStr5PoNJjjC/TVKYHcFSBRtJBu5zHeCGUHkUcoJZpfOFVS5Lm2+uAzaOs27XZYg3TSDbWPRkOaKrwvStAQJBAPQ2o5b8CLayRXQh30zddfn+o3VXc36lmOJBxhaG1kR/HS/vAWBg0mTAUoTNzphduL0SDq5CI1arStFoOJWiGcECQQDpCDMOdkSb+QaRg8zMK1KPMQ2CZEbArjAayz4/oHOwcqqQGWSA0lEn0ktv+1tsC0riMOAHbQtgTHgN8PTL3mKPAkEA1S8lW1YkXSf+TUSMY9Mne9Z35qUyoyn37fsw6tVGEoFMf12KvBGJWH4zCs+GO6gE7rfmrOP7aVsacvch/i2FgQJBAKElUmlFz9wsMSafhhgKPWVX/oeU4HiN/CYLNli5lEcIhHpxlNagmg53lkMyBt6IUJhqRAHenmdRehPp9N6mQnECQE1U0UdQ1AUxg37f6Z+1HzO/nRynknXkNSkHofW3bFxgPXzj8JnFQOQWosqd7RQc/tffZY3RJRrv1fzyfGT06GY=";
		// 支付宝公钥
		// 支付宝公钥(支付宝（RSA）公钥,用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取；或者文档上也有。 )
		public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDeTY1ONoJ3uyxk94nf8V6IMFjeA0xu9F4osTTgHlqECKrt6wfbhVu+O18NxBVE/eXKd+20AKEn4KCcSWf+CAa88edu2ArKtf98lXBSs3iNLN7JpsPC5LqR58XeQhCpbj+Rr2d1v/YRU/2Gg5ARKjwQwISgCmYs6GCSCqnWA8ZEzwIDAQAB";

		private static final int SDK_PAY_FLAG = 1;

		private static final int SDK_CHECK_FLAG = 2;

		Intent intent;
		
		private Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case SDK_PAY_FLAG: {
					PayResult payResult = new PayResult((String) msg.obj);

					// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
					String resultInfo = payResult.getResult();

					String resultStatus = payResult.getResultStatus();

					// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
					if (TextUtils.equals(resultStatus, "9000")) {
						Toast.makeText(AliPay.this, "支付成功", Toast.LENGTH_SHORT).show();
						Intent backIntent = new Intent(AliPay.this, MyOrder.class);
						backIntent.putExtra("tag", 3);
						startActivity(backIntent);
						finish();
					} else {
						// 判断resultStatus 为非“9000”则代表可能支付失败
						// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						if (TextUtils.equals(resultStatus, "8000")) {
							Toast.makeText(AliPay.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

						} else {
							// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
							Toast.makeText(AliPay.this, "支付失败", Toast.LENGTH_SHORT).show();

						}
					}
					break;
				}
				case SDK_CHECK_FLAG: {
					Toast.makeText(AliPay.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
					break;
				}
				default:
					break;
				}
			};
		};
//		private String productName;
//		private String description;
//		private double price;
		private TextView nameTextView;
		private TextView descriptionTextView;
		private TextView priceTextView;
		private Button payButton;
		private Button back;
		private OrderDto orderDto;
		private String productName;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			setContentView(R.layout.activity_pay);
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
			intent = getIntent();
			orderDto = (OrderDto) intent.getSerializableExtra("order");
			productName = intent.getStringExtra("name");
//			Log.i("支付单号：", orderDto.getPaymentSn());
//			description = productName + "加运费";
//			price = intent.getExtras().getDouble("price");
			findView();
		}

		private void findView(){
			back = (Button) findViewById(R.id.back);
			back.setOnClickListener(this);
			TextView title = (TextView) findViewById(R.id.title);
			title.setText("支付");
			findViewById(R.id.other).setVisibility(View.GONE);
			nameTextView = (TextView) findViewById(R.id.product_subject);
			nameTextView.setText(orderDto.getShipName() + orderDto.getShipArea() + orderDto.getShipAddress() + (orderDto.getShipPhone().equals("")?orderDto.getShipMobile():orderDto.getShipPhone()));
//			descriptionTextView = (TextView) findViewById(R.id.description);
//			descriptionTextView.setText(description);
			priceTextView = (TextView) findViewById(R.id.product_price);
			priceTextView.setText(orderDto.getTotalAmount().toString() + "元");
			payButton = (Button) findViewById(R.id.pay_button);
		}
		/**
		 * call alipay sdk pay. 调用SDK支付
		 * 
		 */
		public void pay(View v) {
			// 订单
			String orderInfo = getOrderInfo(productName, orderDto.getPaymentSn(), orderDto.getTotalAmount().toString() ,orderDto.getOrderSn());

			// 对订单做RSA 签名
			String sign = sign(orderInfo);
			try {
				// 仅需对sign 做URL编码
				Log.i("sign", sign); 
				sign = URLEncoder.encode(sign, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// 完整的符合支付宝参数规范的订单信息
			final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
			Log.i("完整的支付信息：", payInfo); 
			Runnable payRunnable = new Runnable() {

				@Override
				public void run() {
					// 构造PayTask 对象
					PayTask alipay = new PayTask(AliPay.this);
					// 调用支付接口，获取支付结果
					String result = alipay.pay(payInfo);

					Message msg = new Message();
					msg.what = SDK_PAY_FLAG;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			};

			// 必须异步调用
			Thread payThread = new Thread(payRunnable);
			payThread.start();
		}

		/**
		 * check whether the device has authentication alipay account.
		 * 查询终端设备是否存在支付宝认证账户
		 * 
		 */
		public void check(View v) {
			Runnable checkRunnable = new Runnable() {

				@Override
				public void run() {
					// 构造PayTask 对象
					PayTask payTask = new PayTask(AliPay.this);
					// 调用查询接口，获取查询结果
					boolean isExist = payTask.checkAccountIfExist();

					Message msg = new Message();
					msg.what = SDK_CHECK_FLAG;
					msg.obj = isExist;
					mHandler.sendMessage(msg);
				}
			};
			
			Thread checkThread = new Thread(checkRunnable);
			checkThread.start();

		}

		/**
		 * get the sdk version. 获取SDK版本号
		 * 
		 */
		public void getSDKVersion() {
			PayTask payTask = new PayTask(this);
			String version = payTask.getVersion();
			Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
		}

		/**
		 * create the order info. 创建订单信息
		 * 
		 */
		public String getOrderInfo(String subject, String body, String price,String orderSn) {
			// 签约合作者身份ID
			String orderInfo = "partner=" + "\"" + PARTNER + "\"";

			// 签约卖家支付宝账号
			orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

			// 商户网站唯一订单号
			orderInfo += "&out_trade_no=" + "\"" + orderSn + "-" + System.currentTimeMillis() +  "\"";

			// 商品名称
			orderInfo += "&subject=" + "\"" + subject + "\"";

			// 商品详情
			orderInfo += "&body=" + "\"" + body + "\"";

			// 商品金额
			orderInfo += "&total_fee=" + "\"" + price + "\"";

			// 服务器异步通知页面路径
			orderInfo += "&notify_url=" + "\"" + HttpUtil.RETURN_URL + "/shop/payment_return_alipayAjaxReturn.action" + "\"";

			// 服务接口名称， 固定值
			orderInfo += "&service=\"mobile.securitypay.pay\"";

			// 支付类型， 固定值
			orderInfo += "&payment_type=\"1\"";

			// 参数编码， 固定值
			orderInfo += "&_input_charset=\"utf-8\"";

			// 设置未付款交易的超时时间
			// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
			// 取值范围：1m～15d。
			// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
			// 该参数数值不接受小数点，如1.5h，可转换为90m。
			orderInfo += "&it_b_pay=\"30m\"";

			// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
			// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

			// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
			orderInfo += "&return_url=\"m.alipay.com\"";

			// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
			// orderInfo += "&paymethod=\"expressGateway\"";

			return orderInfo;
		}

		/**
		 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
		 * 
		 */
		public String getOutTradeNo(String orderSn) {
			SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
			Date date = new Date();
			String key = format.format(date);

			Random r = new Random();
			key = key + r.nextInt();
			key = orderSn + key.substring(0, 15);
			return key;
		}

		/**
		 * sign the order info. 对订单信息进行签名
		 * 
		 * @param content
		 *            待签名订单信息
		 */
		public String sign(String content) {
			return SignUtils.sign(content, RSA_PRIVATE);
		}

		/**
		 * get the sign type we use. 获取签名方式
		 * 
		 */
		public String getSignType() {
			return "sign_type=\"RSA\"";
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back:
				finish();
				break;

			default:
				break;
			}
		}
}
