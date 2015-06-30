package com.searun.shop.util;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.searun.shop.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

public class CommonUtils {

	/**
	 * 获取文件默认保存路径
	 * 
	 * @return
	 */
	public static String getFileSavePath(String folderName) {
		String path = Environment.getExternalStorageDirectory().getAbsolutePath();
		if (TextUtils.isEmpty(path)) {
			path = Environment.getDataDirectory().getAbsolutePath() + "/";
		} else {
			path += "/data/com/seayuan/shop/";
		}
		path += folderName + "/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		return path;
	}

	/**
	 * 检测SDCard是否
	 * 
	 * @param context
	 * @return
	 */
	public static boolean CheckExternStorage(Context context) {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将bitmap转换为byte[]
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] getBitmapByByte(Bitmap bitmap) {
		if (null == bitmap)
			return null;
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		// byte[] byteArray = baos.toByteArray();
		// return byteArray;
		return compressImage(bitmap);
	}

	/**
	 * 压缩图片
	 * 
	 * @param image
	 * @return
	 */
	public static byte[] compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		// ByteArrayInputStream isBm = new
		// ByteArrayInputStream(baos.toByteArray());//
		// 把压缩后的数据baos存放到ByteArrayInputStream中
		// Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
		// 把ByteArrayInputStream数据生成图片
		// return bitmap;
		byte[] byteArray = baos.toByteArray();
		return byteArray;
	}

	public static Bitmap getBitmapFromByte(byte[] temp) {
		if (temp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
			return bitmap;
		} else {
			return null;
		}
	}

	public static void selectSystemPhone(final int photoCode, Activity activity) {

		if (!CommonUtils.CheckExternStorage(activity)) {
			ToastUtil.show(activity, activity.getResources().getString(R.string.msg_no_sdcard));
			return;
		}
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		activity.startActivityForResult(intent, photoCode);
	}
}
