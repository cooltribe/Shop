package com.searun.shop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.searun.shop.R;
import com.searun.shop.application.MyApplication;
import com.searun.shop.data.CartItemDto;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.ProductImage;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToCartList;
import com.searun.shop.toobject.JsonToProductImage;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.view.CustomProgressDialog;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

public class CartEditAdapter extends BaseAdapter {
	MyApplication app;
	Context context;
	LayoutInflater inflater;
	List<CartItemDto> list;
	OperationListener operationListener;
	DisplayImageOptions options;
	MemberDto md;
	private CustomProgressDialog pro;
	public CartEditAdapter(List<CartItemDto> paramList, Context paramContext, MyApplication paramMyApplication,
			MemberDto md) {
		this.list = paramList;
		this.context = paramContext;
		this.inflater = LayoutInflater.from(paramContext);
		this.app = paramMyApplication;
		this.md = md;
		pro = CustomProgressDialog.createDialog(context);
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.mrpic_little)
				.showImageForEmptyUri(R.drawable.mrpic_little).showImageOnFail(R.drawable.mrpic_little).cacheInMemory(false).cacheOnDisk(true).build();
	}

	public int getCount() {
		return this.list.size();
	}

	public CartItemDto getItem(int paramInt) {
		return list.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(final int paramInt, View paramView, ViewGroup paramViewGroup) {
		// this.gd = getItem(paramInt);
		try {
			final ViewHolder vh;
			if (paramView == null) {
				vh = new ViewHolder();
				paramView = this.inflater.inflate(R.layout.item_cart_edit, paramViewGroup, false);
				vh.checkBox = ((CheckBox) paramView.findViewById(R.id.ischecked));
				vh.imageView = ((ImageView) paramView.findViewById(R.id.goods_pic));
				vh.name = ((TextView) paramView.findViewById(R.id.name));
				vh.quanlity = (Button) paramView.findViewById(R.id.quanlity);
				vh.add = (Button) paramView.findViewById(R.id.add);
				vh.submit = (Button) paramView.findViewById(R.id.submit);
				vh.delete = (Button) paramView.findViewById(R.id.delete);
				paramView.setTag(vh);

			} else {
				vh = (ViewHolder) paramView.getTag();
			}

			vh.checkBox.setChecked(true);
			List<ProductImage> imagesList = JsonToProductImage.parserLoginJson(list.get(paramInt).getProduct()
					.getProductImageListStore());
			if (null != imagesList) {
				
				ImageLoader.getInstance().displayImage(HttpUtil.BASE_URL + imagesList.get(0).getSmallProductImagePath(),
						vh.imageView, options);
			} else {
				vh.imageView.setImageResource(R.drawable.mrpic_little);
			}
			vh.name.setText(list.get(paramInt).getProduct().getName());
			vh.quanlity.setText((list.get(paramInt).getQuantity() < 1 ? 1 : list.get(paramInt).getQuantity()) + "");
			
			vh.submit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View paramAnonymousView) {
					int i = Integer.parseInt(vh.quanlity.getText().toString());
					if (i - 1 < 1) {
						vh.quanlity.setText("1");
					} else {
						vh.quanlity.setText((i - 1) + "");
					}
					list.get(paramInt).setQuantity(Integer.parseInt(vh.quanlity.getText().toString()));
					if (null == md) {
						list.get(paramInt).setQuantity(Integer.parseInt(vh.quanlity.getText().toString()));
						list.set(paramInt, list.get(paramInt));
					} else {
						Log.i("减--编辑购物车数量：", "" + list.get(paramInt).getQuantity());
//						Log.i("本地购物车产品数：", "" + app.getCartList().get(paramInt).getQuantity());
						pro.setMessage("载入中，请稍后").show();
						editCart(list.get(paramInt), md);
					}
				}
			});
			vh.add.setOnClickListener(new View.OnClickListener() {
				public void onClick(View paramAnonymousView) {
					int i = Integer.parseInt(vh.quanlity.getText().toString());
					vh.quanlity.setText((i + 1) + "");
					list.get(paramInt).setQuantity(Integer.parseInt(vh.quanlity.getText().toString()));
					if (null == md) {
						list.get(paramInt).setQuantity(Integer.parseInt(vh.quanlity.getText().toString()));
						list.set(paramInt, list.get(paramInt));
					} else {
						Log.i("加++编辑购物车数量：", "" + list.get(paramInt).getQuantity());
//						Log.i("本地购物车产品数：", "" + app.getCartList().get(paramInt).getQuantity());
						pro.setMessage("载入中，请稍后").show();
						editCart(list.get(paramInt), md);
					}
				}
			});
			
			vh.delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (null != operationListener) {
						if (null == md) {
							list.remove(paramInt);
							System.err.println("md是空");
							operationListener.operationOnclick(vh.delete, list.get(paramInt));
						} else {
							pro.setMessage("载入中，请稍后").show();
							deleteCart(list.get(paramInt), md,vh.delete);
							System.err.println("序列:"+ paramInt + ",商品名：" + list.get(paramInt).getProduct().getName());
							
						}
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paramView;
	}
	
	/**
	 * 删除购物车
	 * @param cartItemDto
	 * @param md
	 */
	private void deleteCart(final CartItemDto cartItemDto ,MemberDto md, final Button delete ){
		try {
			HttpUtil.post("deleteCartItem.action", NetWork.getParamsList(cartItemDto, md,null), new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("删除购物车", response.toString());
					operationListener.operationOnclick(delete, cartItemDto);
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
	/**
	 * 编辑购物车
	 * @param cartItemDto
	 * @param md
	 */
	private void editCart(CartItemDto cartItemDto, MemberDto md) {
		try {
			HttpUtil.post("editCartItem.action", NetWork.getParamsList(cartItemDto, md,null), new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("编辑购物车", response.toString());
					app.setCartList(null);
					getCart();
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

	public void setListener(OperationListener paramOperationListener) {
		this.operationListener = paramOperationListener;
	}

	public interface OperationListener {
		public void operationOnclick(View paramView, CartItemDto cartItemDto);
	}
	private void getCart(){
		try {
			HttpUtil.post("listCarItems.action", NetWork.getCart(null, md), new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					System.err.println("CartEditAdapter获取购物车"+response.toString());
					try {
						PdaResponse<List<CartItemDto>> response2 = JsonToCartList.parserLoginJson(response.toString());
						List<CartItemDto> userList = response2.getData();
						Log.i(userList.get(0).getProduct().getName(), userList.get(0).getQuantity() + "");
						app.setCartList(null);
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
						Toast.makeText(context, "网络异常", Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class ViewHolder {
		CheckBox checkBox;
		ImageView imageView;
		TextView name;
		Button add;
		Button submit;
		Button quanlity;
		Button delete;
	}
}
