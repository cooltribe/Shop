package com.searun.shop.adapter;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.searun.shop.R;
import com.searun.shop.activity.LoginActivity;
import com.searun.shop.application.MyApplication;
import com.searun.shop.data.CartItemDto;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.ProductDto;
import com.searun.shop.data.ProductImage;
import com.searun.shop.entity.NetWork;
import com.searun.shop.toobject.JsonToProductImage;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.view.CustomProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductGridAdapter extends BaseAdapter {
	List<ProductDto> list;
	Context context;
	DisplayImageOptions options;
	LayoutInflater inflater;
	 MyApplication app;
	 CustomProgressDialog pro;
	 MemberDto md;
	public ProductGridAdapter(List<ProductDto> list, Context context, DisplayImageOptions options,MyApplication app, MemberDto md) {
		super();
		this.list = list;
		this.context = context;
		this.options = options;
		this.app = app;
		this.md = md;
		pro = CustomProgressDialog.createDialog(context);
		pro.setMessage(context.getString(R.string.loading));
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		try {
			ViewHolder vh;
			if (convertView == null) {
				vh = new ViewHolder();
				convertView = inflater.inflate(R.layout.item_product_grid, parent, false);
				vh.imageView = (ImageView) convertView.findViewById(R.id.product_pic);
				vh.name = (TextView) convertView.findViewById(R.id.name);
				vh.price = (TextView) convertView.findViewById(R.id.price);
				vh.yuanjia = (TextView) convertView.findViewById(R.id.yuanjia);
				vh.cart = (Button) convertView.findViewById(R.id.cart);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}	
				List<ProductImage> productList = JsonToProductImage.parserLoginJson(list.get(position).getProductImageListStore());
				
				if (null != productList) {
					ImageLoader.getInstance().displayImage(HttpUtil.IMG_PATH + productList.get(0).getSmallProductImagePath(), vh.imageView, options);
				} else {
					vh.imageView.setImageResource(R.drawable.mrpic_little);
				}
				System.err.println("名字:" + list.get(position).getName());
				vh.name.setText(list.get(position).getName());
				vh.price.setText("￥" + list.get(position).getPrice());
				vh.yuanjia.setText("￥" + list.get(position).getMarketPrice());
				vh.cart.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (list.get(position).getStore() < 1) {
							Toast.makeText(context, "库存不足", Toast.LENGTH_LONG).show();
						} else {
							
							addCart(list.get(position), md);
						}
					}
				});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	private void addCart(CartItemDto cartItemDto, MemberDto md){
		try {
			HttpUtil.post("addCarItem.action", NetWork.getParamsList(cartItemDto, md,null), new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("加入购物车", response.toString());
//				PdaResponse<String> response2 = response.p
					Toast.makeText(context, response.optString("message"), Toast.LENGTH_LONG).show();
				}
				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
					try {
						Toast.makeText(context, "网络异常", Toast.LENGTH_LONG).show();
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
	private void addCart(ProductDto pd ,MemberDto md){
		try {
			CartItemDto cartItemDto = new CartItemDto();
			cartItemDto.setQuantity(1);
			cartItemDto.setProduct(pd);
			cartItemDto.setMember(md);
			//登陆用户
			if (null != md) {
				pro.show();
				addCart(cartItemDto, md);
			} else {
				//未登陆
				context.startActivity(new Intent(context, LoginActivity.class));
//				Boolean b = false;
//				int location = 0; 
//				List<CartItemDto> localList = app.getCartList();
//				for (int i = 0; i < localList.size(); i++) {
//					System.err.println("1:"+pd.getId());
//					System.err.println("2:"+localList.get(i).getProduct().getId() );
//					if (pd.getId().equals(localList.get(i).getProduct().getId())) {
//						
//						b = true;
//						location = i;
//					}
//				}
//				if (!b) {
//					// pd.setIsChecked(Boolean.valueOf(true));
//					localList.add(cartItemDto);
//					app.setCartList(localList);
//				} else {
//					// pd.setIsChecked(Boolean.valueOf(true));
//					localList.get(location).setQuantity(localList.get(location).getQuantity() + 1);
//					app.setCartList(localList);
//				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class ViewHolder{
		ImageView imageView;
		TextView name;
		TextView price;
		TextView yuanjia;
		Button cart;
	}
}
