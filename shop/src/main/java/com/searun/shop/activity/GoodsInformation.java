package com.searun.shop.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.searun.shop.R;
import com.searun.shop.adapter.ReadJudgeAdapter;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.application.MyApplication;
import com.searun.shop.data.CartItemDto;
import com.searun.shop.data.DeliveryCenterDto;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.ProductDto;
import com.searun.shop.data.ProductImage;
import com.searun.shop.data.ReviewDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaPagination;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToProductDto;
import com.searun.shop.toobject.JsonToProductImage;
import com.searun.shop.toobject.JsonToReviewList;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CircleFlowIndicator;
import com.searun.shop.view.CustomProgressDialog;
import com.searun.shop.view.InfoImageAdapter;
import com.searun.shop.view.MyListView;
import com.searun.shop.view.ViewFlow;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("SetJavaScriptEnabled")
public class GoodsInformation extends Activity implements View.OnClickListener {
	private static final String TAG = "GoodsInformation";
	private static GoodsInformation instance;
	private Button add;
	private MyApplication app;
	private ProductDto pd;
	private TextView goodsName;
	// private ImageView goodsPic;
	private Intent intent;
	private TextView price;
	private TextView yuanJia;
	private DisplayImageOptions options;
	private ViewFlow viewFlow;
	private Button back;
	private LinearLayout imageText;
	private LinearLayout norms;
	private View imageTextView;
	private View normsView;
	private WebView webView;
	private LinearLayout topLayout;
	private MemberDto md;
	private SharedPreferences sp;
	private CustomProgressDialog pro;
	private TextView volume;
	private TextView stock;
	private TextView number;
	private TextView weight;
	private Button store;
	private Button share;
	private MyListView judgeListView;
	private ProductDto productDto;
	private List<ReviewDto> reviewList;
	private TextView points;
	private TextView sendAddress;
	
	private void findView() {

		try {
			back = (Button) findViewById(R.id.back);
			back.setOnClickListener(instance);

			topLayout = (LinearLayout) findViewById(R.id.top);

			viewFlow = (ViewFlow) findViewById(R.id.viewflow);

			store = (Button) findViewById(R.id.store);
			share = (Button) findViewById(R.id.share);

			goodsName = ((TextView) findViewById(R.id.goods_name));

			yuanJia = ((TextView) findViewById(R.id.yuanjia));
			yuanJia.getPaint().setFlags(16);

			price = ((TextView) findViewById(R.id.goods_price));

			volume = (TextView) findViewById(R.id.volume);
			stock = (TextView) findViewById(R.id.stock);
			points = (TextView) findViewById(R.id.points);
			
			number = (TextView) findViewById(R.id.number);
			weight = (TextView) findViewById(R.id.weight);
			sendAddress = (TextView) findViewById(R.id.send_address);

			add = ((Button) findViewById(R.id.add_cart));

			imageText = (LinearLayout) findViewById(R.id.image_text);
			imageTextView = findViewById(R.id.image_text_view);
			imageTextView.setVisibility(View.VISIBLE);
			imageText.setOnClickListener(instance);

			norms = (LinearLayout) findViewById(R.id.norms);
			normsView = findViewById(R.id.norms_view);
			norms.setOnClickListener(instance);

			judgeListView = (MyListView) findViewById(R.id.judge_lv);
			
			webView = (WebView) findViewById(R.id.webview);
			WebSettings settings = webView.getSettings();
			// 扩大比例的缩放
			settings.setUseWideViewPort(true);
			// 设置支持缩放
			settings.setSupportZoom(true);
			// 支持javascript
			settings.setJavaScriptEnabled(true);
			// 设置出现缩放工具
			settings.setBuiltInZoomControls(true);
			// 自适应屏幕
			settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			settings.setLoadWithOverviewMode(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init() {
		try {
			instance = this;
			app = ((MyApplication) getApplication());
			intent = getIntent();
			options = new DisplayImageOptions.Builder()
						.showImageOnLoading(R.drawable.mrpic_big)
						.showImageForEmptyUri(R.drawable.mrpic_big)
						.showImageOnFail(R.drawable.mrpic_big)
						.cacheInMemory(true)
						.cacheOnDisk(true)
						.considerExifParams(true)
						.bitmapConfig(Bitmap.Config.RGB_565)
						.build();
			sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
			md = (MemberDto) StoreObject.readOAuth("md", sp);
			pro = CustomProgressDialog.createDialog(instance);
			pro.setMessage("加载中，请稍后").show();
			reviewList = new ArrayList<ReviewDto>();
			productDto = (ProductDto) intent.getSerializableExtra("pd");
			getProductInformation(productDto);
			getJudgeList(productDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setViewFlow(List<ProductImage> imagesList) {
		try {
			if (imagesList != null) {

				viewFlow.setAdapter(new InfoImageAdapter(instance, imagesList, options));
				viewFlow.setmSideBuffer(imagesList.size());
				CircleFlowIndicator localCircleFlowIndicator = (CircleFlowIndicator) instance.findViewById(R.id.viewflowindic);
				viewFlow.setFlowIndicator(localCircleFlowIndicator);
				viewFlow.setTimeSpan(4500L);
				viewFlow.setSelection(3000);
				viewFlow.stopAutoFlowTimer();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取商品详细信息
	 * 
	 * @param productDto
	 */
	private void getProductInformation(ProductDto productDto) {
		try {
			HttpUtil.post("getProductProperty.action", NetWork.getParamsList(productDto, null, null), new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("商品详情：", response.toString());
					try {
						PdaResponse<ProductDto> response2 = JsonToProductDto.parserLoginJson(response.toString());
						pd = response2.getData();
						if (null != pd) {
							List<ProductImage> imagesList = JsonToProductImage.parserLoginJson(pd.getProductImageListStore());
							setViewFlow(imagesList);
							// ImageLoader.getInstance().displayImage(
							// HttpUtil.IMG_PATH + (imagesList == null ? ""
							// :imagesList.get(0).getSourceProductImagePath()),
							// goodsPic, options);
							goodsName.setText(pd.getName());
							yuanJia.setText("￥" + pd.getMarketPrice().toString());
							price.setText("￥" + pd.getPrice().toString());
							volume.setText(pd.getFreezeStore() + "件");
							stock.setText(pd.getStore() + "件");
							points.setText("" + pd.getPoint());
							number.setText(pd.getProductSn());
							weight.setText(pd.getWeight() + " " + pd.getWeightUnit());
							DeliveryCenterDto deliveryCenterDto = pd.getDeliveryCenterDto();
							if (null != deliveryCenterDto) {
								Log.i("发货地点：", deliveryCenterDto.toString());
								sendAddress.setText(deliveryCenterDto.getName());
							}
							webView.loadData(pd.getDescription(), "text/html;charset=UTF-8", null);
							add.setOnClickListener(instance);
							store.setOnClickListener(instance);
						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
					try {
						Toast.makeText(instance, "网络异常", Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					super.onFinish();
					if (pro.isShowing()) {
						pro.cancel();
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addCart(CartItemDto cartItemDto, MemberDto md) {
		try {
			HttpUtil.post("addCarItem.action", NetWork.getParamsList(cartItemDto, md,null), new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("加入购物车", response.toString());
					// PdaResponse<String> response2 = response.p
					Toast.makeText(instance, response.optString("message"), Toast.LENGTH_LONG).show();
					instance.finish();
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
					try {
						Toast.makeText(instance, "网络异常", Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					super.onFinish();
					if (pro.isShowing()) {
						pro.cancel();
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addFavourite() {
		try {
			HttpUtil.post("addFavourite.action", NetWork.getParamsList(pd.getId(), md,null), new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("收藏结果", response.toString());
					try {
						Toast.makeText(instance, response.optString("message"), Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
					try {
						Toast.makeText(instance, "网络异常", Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					super.onFinish();
					if (pro.isShowing()) {
						pro.cancel();
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void getJudgeList(ProductDto productDto){
		PdaPagination page = new PdaPagination();
		page.setAmount(10);
		page.setPageNumber(1);
		HttpUtil.post("listReviews.action", NetWork.getParamsList(productDto.getId(),null,page), new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i(TAG, "评价列表："+response.toString());
//				SaveString.stringSave("图片", instance, response.toString());
				try {
					PdaResponse<List<ReviewDto>> pdaResponse = JsonToReviewList.parserLoginJson(response.toString());
					reviewList = pdaResponse.getData();
					judgeListView.setAdapter(new ReadJudgeAdapter(reviewList, instance, options));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				try {
					Toast.makeText(instance, getResources().getString(R.string.network_error_hint), Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if (pro.isShowing()) {
					pro.cancel();
				}
			}
		});
	}
	public void onClick(View paramView) {
		try {
			switch (paramView.getId()) {
			case R.id.back:
				finish();
				break;
			case R.id.add_cart:

				CartItemDto cartItemDto = new CartItemDto();
				cartItemDto.setQuantity(1);
				cartItemDto.setProduct(pd);
				cartItemDto.setMember(md);
				if (pd.getStore() < 1) {
					Toast.makeText(instance, "库存不足", Toast.LENGTH_LONG).show();
				} else {

					if (null != md) {
						pro.setMessage("加载中，请稍后").show();
						addCart(cartItemDto, md);

					} else {
						startActivity(new Intent(instance, LoginActivity.class));
						// Boolean b = false;
						// int location = 0;
						// List<CartItemDto> localList = app.getCartList();
						// for (int i = 0; i < localList.size(); i++) {
						// System.err.println("1:"+pd.getId());
						// System.err.println("2:"+localList.get(i).getProduct().getId()
						// );
						// if
						// (pd.getId().equals(localList.get(i).getProduct().getId()))
						// {
						//
						// b = true;
						// location = i;
						// }
						// }
						// if (!b) {
						// // pd.setIsChecked(Boolean.valueOf(true));
						// localList.add(cartItemDto);
						// app.setCartList(localList);
						// } else {
						// // pd.setIsChecked(Boolean.valueOf(true));
						// localList.get(location).setQuantity(localList.get(location).getQuantity()
						// + 1);
						// app.setCartList(localList);
						// }
						// finish();
					}
				}

				break;
			case R.id.store:
				if (null == md) {
					startActivity(new Intent(this, LoginActivity.class));
				} else {
					pro.setMessage("加载中，请稍后").show();
					addFavourite();
				}
				break;
			case R.id.image_text:
				imageTextView.setVisibility(View.VISIBLE);
				normsView.setVisibility(View.INVISIBLE);
//				topLayout.setVisibility(View.VISIBLE);
				webView.setVisibility(View.VISIBLE);
				judgeListView.setVisibility(View.GONE);
				Log.i(TAG, "图文显示");
				break;
			case R.id.norms:
				normsView.setVisibility(View.VISIBLE);
				imageTextView.setVisibility(View.INVISIBLE);
//				topLayout.setVisibility(View.GONE);
				webView.setVisibility(View.GONE);
				judgeListView.setVisibility(View.VISIBLE);
				Log.i(TAG, "规格参数显示");
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_goodsinformation);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.titlebar);
		init();
		findView();
	}
}
