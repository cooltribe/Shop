package com.searun.shop.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.searun.shop.R;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.ImageDto;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.ProductDto;
import com.searun.shop.data.ProductImage;
import com.searun.shop.data.ReviewDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.toobject.JsonToProductImage;
import com.searun.shop.util.CommonUtils;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;
import com.searun.shop.util.ToastUtil;
import com.searun.shop.view.CustomProgressDialog;
import com.searun.shop.view.MyGridView;
import com.searun.shop.view.SelectPicPopupWindow;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WriteJudgeActivity extends Activity implements OnClickListener {

	private Button backButton;
	private ImageView productImg;
	private TextView productName;
	private RatingBar ratingBar;
	private EditText judgeContent;
	private String orderId;
	private ProductDto productDto;
	private DisplayImageOptions options;
	private MemberDto member;
	private SharedPreferences sp;
	private static WriteJudgeActivity instance;
	private ImageView img1;
	private ArrayList<HashMap<String, Object>> imageItem;
	public static final int TO_SELECT_PHOTO = 3;
	public static String picPath = null;
	private static final int HEADER_REQUEST_CODE_PHOTOALBUM = 500;
	private static final int HEADER_REQUEST_CODE_PHOTO = 501;
	private static final int HEADER_REQUEST_CODE_PHOTOOK = 502;
	private static final int HEADER_REQUEST_CODE_PICK = 503;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_write_judge);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		init();
		try {
			findView();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init() {
		Intent intent = getIntent();
		orderId = intent.getStringExtra("orderId");
		productDto = (ProductDto) intent.getSerializableExtra("product");
		sp = getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		instance = this;
		pro = CustomProgressDialog.createDialog(instance);
		pro.setMessage(getResources().getString(R.string.loading));
		member = (MemberDto) StoreObject.readOAuth("md", sp);
		imageList = new ArrayList<ImageDto>();
		imageDto = new ImageDto();
		options = new DisplayImageOptions.Builder()
						.showImageOnLoading(R.drawable.mrpic_little)
						.showImageForEmptyUri(R.drawable.mrpic_little)
						.cacheInMemory(true)
						.cacheOnDisk(true)
						.build();
	}

	private SimpleAdapter simpleAdapter; 
	private void findView() throws Exception {
		backButton = (Button) findViewById(R.id.back);
		backButton.setOnClickListener(this);
		TextView titleTextView = (TextView) findViewById(R.id.title);
		titleTextView.setText("商品评价");
		Button submit = (Button) findViewById(R.id.submit);
		submit.setText("提交");
		submit.setOnClickListener(instance);
		findViewById(R.id.other).setVisibility(View.GONE);
		LinearLayout judgeLayout = (LinearLayout) findViewById(R.id.judge_layout);
		judgeLayout.setVisibility(View.GONE);
		findViewById(R.id.judge_view).setVisibility(View.GONE);
		productImg = (ImageView) findViewById(R.id.goods_pic);

		List<ProductImage> imagesList = JsonToProductImage.parserLoginJson(productDto.getProductImageListStore());
		if (null != imagesList) {
			ImageLoader.getInstance().displayImage(HttpUtil.IMG_PATH + imagesList.get(0).getSmallProductImagePath(), productImg, options);
		} else {
			productImg.setImageResource(R.drawable.mrpic_little);
		}
		
		productName = (TextView) findViewById(R.id.name);
		productName.setText(productDto.getName());
		
		ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
		ratingBar.setMax(100);
		judgeContent = (EditText) findViewById(R.id.judge_content);
		
		addImage();
		
		
//		img1 = (ImageView) findViewById(R.id.include1).findViewById(R.id.judge_pic1);
//		img1.setOnClickListener(instance);
//		ImageView imageView1 = new ImageView(instance);
//		judgeLayout.addView(imageView1, 1);
		TextView config_hidden = (TextView) findViewById(R.id.config_hidden);
		config_hidden.requestFocus();
	}

	 /*
     * Dialog对话框提示用户删除操作
     * position为删除图片位置
     */
    protected void dialog(final int position) {
    	try {
			AlertDialog.Builder builder = new Builder(instance);
			builder.setMessage("确认移除已添加图片吗？");
			builder.setTitle("提示");
			builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					imageItem.remove(position);
					imageList.remove(position);
			        simpleAdapter.notifyDataSetChanged();
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					}
				});
			builder.create().show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private final int IMAGE_OPEN = 1;        //打开图片标记

	
	private void addImage(){
		try {
			imageGridView = (MyGridView) findViewById(R.id.judge_gv);
			
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.mrpic_little);
			imageItem = new ArrayList<HashMap<String,Object>>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", bmp);
			imageItem.add(map);
			simpleAdapter = new SimpleAdapter(instance, imageItem, R.layout.judge_image_item, new String[]{"itemImage"}, new int[]{R.id.imageView1});
			simpleAdapter.setViewBinder(new ViewBinder() {
				
				@Override
				public boolean setViewValue(View view, Object data, String textRepresentation) {
					// TODO Auto-generated method stub
					if (view instanceof ImageView && data instanceof Bitmap) {
						ImageView i = (ImageView) view;
						i.setImageBitmap((Bitmap) data);
						return true;
					}
					return false;
				}
			});
			
			imageGridView.setAdapter(simpleAdapter);
			imageGridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
//				if( imageItem.size() == 10 && position == 0) { //第一张为默认图片
//  					
//  				}
//  				else 
						if(position == imageItem.size() -1) { //点击图片位置为+ 0对应0张图片
							if (position == 10) {
							Toast.makeText(instance, "图片数9张已满", Toast.LENGTH_SHORT).show();
						} else {
//						Toast.makeText(instance, "添加图片", Toast.LENGTH_SHORT).show();
							//选择图片
							doJudgePhoto();
						}
					}
					else {
						dialog(position);
						//Toast.makeText(MainActivity.this, "点击第"+(position + 1)+" 号图片", 
						//		Toast.LENGTH_SHORT).show();
					}
     }
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	}
	private void getJudge() {
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setContent(judgeContent.getText().toString());
		reviewDto.setScore(ratingBar.getRating() + "");
		reviewDto.setOrderId(orderId);
		reviewDto.setProduct(productDto);
		reviewDto.setMember(member);
		
		
//		imageDto.setImageSuffix("png");
//		img1.setDrawingCacheEnabled(true);
//		imageDto.setFile(CommonUtils.getBitmapByByte(img1.getDrawingCache()));
//		imageList.add(imageDto);
//		Log.i("图片", img1.getDrawingCache().toString());
//		img1.setDrawingCacheEnabled(false);
		Log.i("图片数量", imageList.size() + "");
		reviewDto.setProductImages(imageList);
		Log.i("分数", ratingBar.getRating() + "");
		HttpUtil.post("saveReview.action", NetWork.getParamsList(reviewDto, member, null), new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("评价结果:", response.toString());
				try {
					if (response.optBoolean("success")) {
						instance.finish();
					} else {
						Toast.makeText(instance, response.optString("message"), Toast.LENGTH_LONG).show();
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
				Toast.makeText(instance, getResources().getString(R.string.network_error_hint), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				Log.i("评价结束:", "评价结束评价结束评价结束");
				if (pro.isShowing()) {
					pro.cancel();
				}
			}
		});
	}

	private Bitmap resultBitmap;
	/**
	 * 上传评价信息
	 */
	private Bitmap uploadBitmap;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// if (resultCode == Activity.RESULT_OK && requestCode ==
		// TO_SELECT_PHOTO) {
		// // imageView不设null, 第一次上传成功后，第二次在选择上传的时候会报错。
		// img1.setImageBitmap(null);
		// picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
		// Log.i("JudgeActivity", "最终选择的图片=" + picPath);
		// // txt.setText("文件路径"+picPath);
		// Bitmap bm = BitmapFactory.decodeFile(picPath);
		// img1.setImageBitmap(bm);
		// }
		try {
			if (resultCode != Activity.RESULT_OK) {
				return;
			}
			switch (requestCode) {
			case HEADER_REQUEST_CODE_PHOTOALBUM:
				if (data != null) {
					startPhotoZoom(data.getData(), HEADER_REQUEST_CODE_PHOTOOK);
				}
				break;

			case HEADER_REQUEST_CODE_PHOTOOK:
				resultBitmap = BitmapFactory.decodeFile(ApplicationData.DEFAULT_ICON_PATH + "image_diy_resultphoto_temp.jpg");
				uploadBitmap = resultBitmap;
//			img1.setImageBitmap(resultBitmap);
				showJudgeImage(resultBitmap);
				ImageDto imageDto = new ImageDto();
				imageDto.setImageSuffix("png");
				imageDto.setFile(CommonUtils.getBitmapByByte(resultBitmap));
				imageList.add(0,imageDto);
				break;
			case HEADER_REQUEST_CODE_PHOTO:
				filePath = "file://" + ApplicationData.DEFAULT_ICON_PATH + "image_diy_takephoto.jpg";

				// resultBitmap = BitmapFactory.decodeFile(filePath);
				// img1.setImageBitmap(resultBitmap);
				if (filePath != null) {
					cropPhoto(filePath, HEADER_REQUEST_CODE_PICK);
				}
				break;
			case HEADER_REQUEST_CODE_PICK:
				resultBitmap = BitmapFactory.decodeFile(ApplicationData.DEFAULT_ICON_PATH + "image_diy_resultphoto.jpg");
				uploadBitmap = resultBitmap;
//			img1.setImageBitmap(resultBitmap);
				showJudgeImage(resultBitmap);
				ImageDto imageDto1 = new ImageDto();
				imageDto1.setImageSuffix("png");
				imageDto1.setFile(CommonUtils.getBitmapByByte(resultBitmap));
				imageList.add(0,imageDto1);
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void showJudgeImage(Bitmap bitmap){
//		Bitmap addbmp=BitmapFactory.decodeFile(bitmap);
		 try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			 map.put("itemImage", bitmap);
			 imageItem.add(0,map);
			 simpleAdapter = new SimpleAdapter(instance, imageItem, R.layout.judge_image_item, new String[]{"itemImage"}, new int[]{R.id.imageView1});
				simpleAdapter.setViewBinder(new ViewBinder() {
					
					@Override
					public boolean setViewValue(View view, Object data, String textRepresentation) {
						// TODO Auto-generated method stub
						if (view instanceof ImageView && data instanceof Bitmap) {
							ImageView i = (ImageView) view;
							i.setImageBitmap((Bitmap) data);
							return true;
						}
						return false;
					}
				});
				
				imageGridView.setAdapter(simpleAdapter);
				simpleAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 拍照裁切
	 * 
	 * @param filePath
	 * @param pick
	 */
	public void cropPhoto(String filePath, int pick) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(Uri.parse(filePath), "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 5);
		intent.putExtra("aspectY", 5);
		intent.putExtra("outputX", 480);
		intent.putExtra("outputY", 480);
		intent.putExtra("scale", true);
		
		File tempFile = new File(ApplicationData.DEFAULT_ICON_PATH + "image_diy_resultphoto.jpg");
		intent.putExtra("output", Uri.fromFile(tempFile));
		intent.putExtra("outputFormat", "JPEG");// 返回格式
		try {
			startActivityForResult(intent, pick);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void doJudgePhoto() {
		showOptionDialog(HEADER_REQUEST_CODE_PHOTOALBUM, HEADER_REQUEST_CODE_PHOTO);

	}
	
	/**
	 * 
	 * 裁剪图片方法实现
	 * 
	 * 
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri, int photoook) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 5);
		intent.putExtra("aspectY", 5);
		intent.putExtra("outputX", 480);
		intent.putExtra("outputY", 480);
		intent.putExtra("scale", true);

		File tempFile = new File(ApplicationData.DEFAULT_ICON_PATH + "image_diy_resultphoto_temp.jpg");
		intent.putExtra("output", Uri.fromFile(tempFile));
		intent.putExtra("outputFormat", "JPEG");// 返回格式
		try {
			startActivityForResult(intent, photoook);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showOptionDialog(final int photoCode, final int cameraCode) {
		final SelectPicPopupWindow dialog = new SelectPicPopupWindow(WriteJudgeActivity.this);
		dialog.setFirstButtonContent("拍照");
		dialog.setFirstButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// CommonUtils.selectCameraPhone(cameraCode, resultPath,
				// AddNewDriverActivity.this);
				takePhoto(cameraCode);
				dialog.dismiss();
			}
		});
		dialog.setSecendButtonContent("从相册选择");
		dialog.setSecendButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonUtils.selectSystemPhone(photoCode, WriteJudgeActivity.this);
				dialog.dismiss();
			}
		});
		dialog.setThirdButtonContent("取消");
		dialog.setThirdButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		// 显示窗口
		dialog.showAtLocation(WriteJudgeActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
	}

	private String filePath;
	private CustomProgressDialog pro;
	private MyGridView imageGridView;
	private List<ImageDto> imageList;
	private ImageDto imageDto;

	private void takePhoto(int photo) {
		Intent intent = new Intent();
		intent.setAction("android.media.action.IMAGE_CAPTURE");
		Bundle bundle = new Bundle();

		String path = ApplicationData.DEFAULT_ICON_PATH;
		if (path != null) {
			filePath = "file://" + path + "image_diy_takephoto.jpg";
			Log.i("filePath", filePath);
			Uri uri = Uri.parse(filePath);
			bundle.putParcelable(MediaStore.EXTRA_OUTPUT, uri);
			intent.putExtras(bundle);
			try {
				startActivityForResult(intent, photo);
			} catch (Exception e) {
				ToastUtil.show(this, getResources().getString(R.string.msg_send_nophoto_prompt));
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		case R.id.submit:
			pro.show();
			getJudge();
			break;
		case R.id.judge_pic1:
			// Intent intent = new Intent(this, SelectPicActivity.class);
			// startActivityForResult(intent, TO_SELECT_PHOTO);
			doJudgePhoto();
			break;
		}
	}
}
