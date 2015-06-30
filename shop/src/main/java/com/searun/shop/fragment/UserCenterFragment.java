package com.searun.shop.fragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.activity.AddressManage;
import com.searun.shop.activity.AddressSelect;
import com.searun.shop.activity.FavouriteListActivity;
import com.searun.shop.activity.LoginActivity;
import com.searun.shop.activity.MyMessage;
import com.searun.shop.activity.MyOrder;
import com.searun.shop.activity.UpdateInformationActivity;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.MemberRankDto;
import com.searun.shop.data.OrderDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToMemberDto;
import com.searun.shop.util.ClickInterface;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.view.CustomProgressDialog;
import com.searun.shop.view.ItemUserView;
import com.searun.shop.view.NumberImageView;

public class UserCenterFragment extends Fragment implements View.OnClickListener {
	private static final String TAG = "UserCenterFragment";
	private static UserCenterFragment instance;
	private Button login;
//	private Button loginOut;
	private SharedPreferences sp;
	private TextView userName;
	private ItemUserView storeUp;
	private ItemUserView footmark;
	private NumberImageView pay;
	private ItemUserView message;
	private ItemUserView address;
	private ItemUserView kefu;
	private ItemUserView feedback;
	private ItemUserView aboutUs;
	private ItemUserView set;
	private NumberImageView send;
	private NumberImageView takeOver;
	private NumberImageView judge;
	private NumberImageView shouHou;
	private ItemUserView order;
	private TextView memberLevel;
	private CustomProgressDialog pro;
	private TextView balance;
	private TextView score;
	private RelativeLayout userLayout;
	private void dataInit() {
		try {
			md = (MemberDto) StoreObject.readOAuth("md", sp);
			if (null != md) {
				pro = CustomProgressDialog.createDialog(getActivity());
				pro.setMessage("加载中，请稍后").show();
				System.err.println("md不为空1");
				getMember(md);
				System.err.println("md不为空2");
				
			} else {
				System.err.println("md?????????????????");
				userName.setText("登陆");
				memberLevel.setText("注册");
			}
			
			userLayout.setOnClickListener(instance);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findView(View view) {
		
		userLayout = (RelativeLayout) view.findViewById(R.id.user_layout);
//		userLayout.setOnClickListener(instance);
		
		memberLevel = (TextView) view.findViewById(R.id.member_level);
		userName = ((TextView) view.findViewById(R.id.username));
		login = ((Button) view.findViewById(R.id.login));
		balance = (TextView) view.findViewById(R.id.balance);
		score = (TextView) view.findViewById(R.id.score);
		
		pay = (NumberImageView) view.findViewById(R.id.pay);
		pay.setBackground(R.drawable.icon_dfk);
		pay.setTitle("待付款");
		pay.setOnClickListener(instance);
		
		send = (NumberImageView) view.findViewById(R.id.send);
		send.setBackground(R.drawable.icon_dfh);
		send.setTitle("待发货");
		send.setOnClickListener(instance);
		
		takeOver = (NumberImageView) view.findViewById(R.id.take_over);
		takeOver.setBackground(R.drawable.icon_dfh);
		takeOver.setTitle("待收货");
		takeOver.setOnClickListener(instance);
		
		judge = (NumberImageView) view.findViewById(R.id.judge);
		judge.setBackground(R.drawable.icon_dpj);
		judge.setTitle("待评价");
		judge.setOnClickListener(instance);
		
		shouHou = (NumberImageView) view.findViewById(R.id.shouhou);
		shouHou.setBackground(R.drawable.icon_sh);
		shouHou.setTitle("售后");
		shouHou.setOnClickListener(instance);
		
		
		order = (ItemUserView) view.findViewById(R.id.order);
		order.setTopLineVisibility(View.VISIBLE);
		order.setIcon(R.drawable.icon_wddd);
		order.setText("我的订单");
		order.setTv("查看全部订单");
		order.setTvVisibility(View.VISIBLE);
		order.setOnClickListener(instance);
		
		view.findViewById(R.id.back).setVisibility(View.GONE);
		view.findViewById(R.id.operate).setVisibility(View.GONE);
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText("个人中心");
		title.setTextSize(18f);
		
		
//		loginOut = ((Button) view.findViewById(R.id.login_out));
		
		
		storeUp = (ItemUserView) view.findViewById(R.id.store_up);
		storeUp.setTopLineVisibility(View.VISIBLE);
		storeUp.setIcon(R.drawable.icon_wdsc);
		storeUp.setText("我的收藏");
		storeUp.setOnClickListener(instance);
		
		footmark = (ItemUserView) view.findViewById(R.id.footmark);
		footmark.setTopLineVisibility(View.VISIBLE);
		footmark.setIcon(R.drawable.icon_wdzj);
		footmark.setText("我的足迹");
		
		message = (ItemUserView) view.findViewById(R.id.message);
		message.setTopLineVisibility(View.VISIBLE);
		message.setIcon(R.drawable.icon_wdxx);
		message.setText("我的消息");
		message.setBottomLineVisibility(View.VISIBLE);
		message.setOnClickListener(instance);
		
		
		address = (ItemUserView) view.findViewById(R.id.address);
		address.setTopLineVisibility(View.VISIBLE);
		address.setIcon(R.drawable.icon_shdzgl);
		address.setText("收货地址管理");
		address.setOnClickListener(instance);
		
		kefu = (ItemUserView) view.findViewById(R.id.kefu);
		kefu.setTopLineVisibility(View.VISIBLE);
		kefu.setIcon(R.drawable.icon_kfzx);
		kefu.setText("客服中心");
		
		feedback = (ItemUserView) view.findViewById(R.id.feedback);
		feedback.setTopLineVisibility(View.VISIBLE);
		feedback.setIcon(R.drawable.icon_yhfk);
		feedback.setText("用户反馈");
		
		aboutUs = (ItemUserView) view.findViewById(R.id.about_us);
		aboutUs.setTopLineVisibility(View.VISIBLE);
		aboutUs.setIcon(R.drawable.icon_gywm);
		aboutUs.setText("关于我们");
		
		set = (ItemUserView) view.findViewById(R.id.set);
		set.setTopLineVisibility(View.VISIBLE);
		set.setIcon(R.drawable.icon_sz);
		set.setText("设置");
		set.setBottomLineVisibility(View.VISIBLE);
		
		loginOut = (Button) view.findViewById(R.id.login_out);
		loginOut.setOnClickListener(instance);
		
	}

	public static UserCenterFragment getInstance() {
		if (instance == null)
			instance = new UserCenterFragment();
		return instance;
	}
	/**
	 * 获取个人信息
	 * @param md
	 */
	private void getMember(MemberDto md){
		HttpUtil.post("memberIndex.action", NetWork.getCart(null, md), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i(TAG, "用户信息：" + response.toString());
				try {
					if (response.optBoolean("success")) {
						
						analyzeMemer(response);
					}
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
					Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
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
					System.err.println("dddddddddddddddddddddd");
					pro.cancel();
				}
			}
		});
	}
	/**
	 * 解析获取到的个人信息
	 * @param jsonObject
	 * @throws JSONException 
	 */
	private void analyzeMemer(JSONObject jsonObject) throws Exception{
		try {
			PdaResponse<MemberDto> response = JsonToMemberDto.parserLoginJson(jsonObject.toString());
			MemberDto memberDto = response.getData();
			MemberRankDto mrd = new MemberRankDto();
			mrd = memberDto.getMemberRank();
//				mrd = memberDto.getMemberRank();
			userName.setText(memberDto.getUsername());
			if (null != mrd) {
				memberLevel.setText(mrd.getName());
			} else {
				memberLevel.setText("普通会员");
			}
			balance.setText("￥" + memberDto.getDeposit().toString());
			score.setText(memberDto.getPoint().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	ClickInterface ci;
	private MemberDto md;
	private Button loginOut;
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case Activity.RESULT_OK:
			ci.onClick(1);
			break;

		default:
			break;
		}
		
	}
	public void onClick(View v) {
		Intent orderIntent = new Intent(getActivity(), MyOrder.class);
		OrderDto orderDto = new OrderDto();
		switch (v.getId()) {
		case R.id.user_layout:
			if (null == md) {
				startActivity(new Intent(getActivity(), LoginActivity.class));
			} else {
				startActivity(new Intent(getActivity(), UpdateInformationActivity.class));
			}
			break;
			//我的订单
		case R.id.order:
			orderIntent.putExtra("order", orderDto);
			orderIntent.putExtra("tag", 1);
			startActivity(orderIntent);
			break;
			//待付款
		case R.id.pay:
			orderDto.setPaymentStatus("unpaid");
			orderIntent.putExtra("order", orderDto);
			orderIntent.putExtra("tag", 2);
			startActivity(orderIntent);
			break;
			//待发货
		case R.id.send:
			orderDto.setShippingStatus("unshipped");
			orderIntent.putExtra("order", orderDto);
			orderIntent.putExtra("tag", 3);
			startActivity(orderIntent);
			break;
			//待收货
		case R.id.take_over:
			orderDto.setShippingStatus("shipped");
			orderIntent.putExtra("order", orderDto);
			orderIntent.putExtra("tag", 4);
			startActivity(orderIntent);
			break;
			//待评价
		case R.id.judge:
			orderDto.setOrderStatus("received");
			orderIntent.putExtra("order", orderDto);
			orderIntent.putExtra("tag", 5);
			startActivity(orderIntent);
			break;
			//售后
		case R.id.shouhou:
			orderDto.setOrderStatus("evaluation");
			orderIntent.putExtra("order", orderDto);
			orderIntent.putExtra("tag", 6);
			startActivity(orderIntent);
			break;
			//我的收藏
		case R.id.store_up:
			startActivityForResult(new Intent(getActivity(), FavouriteListActivity.class), 1);
			break;
			//我的足迹
		case R.id.footmark:
			break;
			//我的消息
		case R.id.message:
			if (null == md) {
				startActivity(new Intent(getActivity(), LoginActivity.class));
			} else {
				startActivity(new Intent(getActivity(), MyMessage.class));
			}
			break;
			//收货地址管理
		case R.id.address:
			Intent intent = new Intent(getActivity(), AddressSelect.class);
			intent.putExtra("title", "地址管理");
			
			startActivity(intent);
			break;
			//我的客服
		case R.id.kefu:
			break;
			//用户反馈
		case R.id.feedback:
			break;
			//关于我们
		case R.id.about_us:
			break;
			//设置
		case R.id.set:
//			startActivity(new Intent(getActivity(), LoginActivity.class));
			break;
		case R.id.login_out:
			setDialog();
			break;
		}
	}
	private void setDialog(){
		String[] items1 = {"关闭易袋购","退出登陆"};
		String[] items2 = {"关闭易袋购"};
		new AlertDialog.Builder(getActivity()).setItems(null == md?items2 : items1, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0:
					System.exit(0);
					break;

				case 1:
					
					sp.edit().remove("md").commit();
					dialog.cancel();
					Intent intent = new Intent(getActivity(), LoginActivity.class);
					startActivity(intent);
					break;
				}
			}
		}).create().show();
	}
	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		sp = getActivity().getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, 0);
		
		View localView = paramLayoutInflater.inflate(R.layout.fragment_usercenter, paramViewGroup, false);
		findView(localView);
		return localView;
	}

	public void onResume() {
		super.onResume();
		dataInit();
	}
}
