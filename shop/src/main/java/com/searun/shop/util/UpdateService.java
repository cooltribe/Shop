package com.searun.shop.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.Header;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.activity.MainActivity;
import com.searun.shop.data.PdaVersionInfoDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToVersion;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;



/**
 * 版本更新服务
 */
public class UpdateService extends Service {
	
	private String updateUrl = "";
	// 标题
	private int titleName = R.string.app_name;

	// 文件存储
	private File updateDir = null;
	private File updateFile = null;
	// 下载状态
	private final static int DOWNLOAD_COMPLETE = 0;
	private final static int DOWNLOAD_FAIL = 1;
	// 通知栏
	private NotificationManager updateNotificationManager = null;
	private Notification updateNotification = null;
	// 通知栏跳转Intent
	private Intent updateIntent = null;
	private PendingIntent updatePendingIntent = null;
	/***
	 * 创建通知栏
	 */
	RemoteViews contentView;
	// 这样的下载代码很多，我就不做过多的说明
	int downloadCount = 0;
	int currentSize = 0;
	long totalSize = 0;
	int updateTotalSize = 0;

	// 在onStartCommand()方法中准备相关的下载工作：
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		getUpdate(intent, flags, startId);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 自动更新dialog
	 * @param pvid
	 * @return
	 */
	private Dialog getDialog(final PdaVersionInfoDto pvid){
		View view = View.inflate(getApplicationContext(), R.layout.dialog_update, null);
		updateDialog = new Dialog(getApplicationContext(), R.style.DeliveryDialog);
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView content = (TextView) view.findViewById(R.id.content);
		content.setText("1.注册界面UI美化\n2.修改信息界面美化\n3.自动更新对话框美化\n4.购买方式对话框美化" );
		Button update = (Button) view.findViewById(R.id.update);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		if (pvid.getIsUpgrade().equals("1")) {
			cancel.setVisibility(View.GONE);
			updateDialog.setCancelable(false);//设置点击其它地方dialog不取消
		}
		/**
		 * 设置点击返回键停止service同时退出app
		 */
		updateDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
					stopSelf();
					System.exit(0);
				}
					return false;
			}
		});
		updateDialog.setContentView(view,
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT  , LinearLayout.LayoutParams.WRAP_CONTENT));
		update.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateUrl = pvid.getUrl();
				Log.i("uriiiiiiiiiiii", updateUrl);
				// 创建文件
				if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
						.getExternalStorageState())) {
					updateDir = new File(Environment.getExternalStorageDirectory(),
							"update");
					updateFile = new File(updateDir.getPath(), getResources()
							.getString(titleName) + ".apk");
				}

				UpdateService.this.updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				UpdateService.this.updateNotification = new Notification();

				// 设置下载过程中，点击通知栏，回到主界面
				updateIntent = new Intent(UpdateService.this, MainActivity.class);
				updatePendingIntent = PendingIntent.getActivity(UpdateService.this, 0, updateIntent,
						0);
				// 设置通知栏显示内容
				updateNotification.icon = R.drawable.ic_launcher;
				updateNotification.tickerText = "开始下载";
				updateNotification.setLatestEventInfo(UpdateService.this, "易袋购", "0%",
						updatePendingIntent);
				// 发出通知
				updateNotificationManager.notify(0, updateNotification);
				new Thread(new updateRunnable(updateUrl)).start();// 这个是下载的重点，是下载的过程
				updateDialog.dismiss();
			}
			
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateDialog.cancel();
			}
		});
		updateDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		return updateDialog;
		
	}
	private void getUpdate(final Intent intent, int flags, int startId){
		HttpUtil.post("checkPdaVersionInfo.action", NetWork.getParamsList(null,null,null), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.i("版本信息", response.toString());
				
				try {
					PdaResponse<PdaVersionInfoDto> pdaResponse = JsonToVersion.parserLoginJson(response.toString());
					PdaVersionInfoDto pvid = pdaResponse.getData();
					int vercodeServer = Integer.parseInt(pvid.getVersion().replace(".", "").trim());
					
					int vercodeClient = Integer.parseInt(getPackageManager().getPackageInfo(getPackageName(), 0).versionName.replace(".", "").trim());
//					Toast.makeText(getApplicationContext(), pvid.getVersion(), Toast.LENGTH_LONG).show();
					Log.i("是否更新", vercodeServer +   "???" +vercodeClient);
					if (vercodeServer > vercodeClient) {
						// 获取传值
//						updateUrl = intent.getStringExtra(pvid.getUrl());
						

						// 开启一个新的线程下载，如果使用Service同步下载，会导致ANR问题，Service本身也会阻塞
						getDialog(pvid).show();;
//						AlertDialog dialog = new AlertDialog.Builder(getApplicationContext()).setTitle("检测到新版本,是否更新")
//										.setNegativeButton("立即更新", new OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								// TODO Auto-generated method stub
//								updateUrl = pvid.getUrl();
//								Log.i("uriiiiiiiiiiii", updateUrl);
//								// 创建文件
//								if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
//										.getExternalStorageState())) {
//									updateDir = new File(Environment.getExternalStorageDirectory(),
//											"update");
//									updateFile = new File(updateDir.getPath(), getResources()
//											.getString(titleName) + ".apk");
//								}
//
//								UpdateService.this.updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//								UpdateService.this.updateNotification = new Notification();
//
//								// 设置下载过程中，点击通知栏，回到主界面
//								updateIntent = new Intent(UpdateService.this, MainActivity.class);
//								updatePendingIntent = PendingIntent.getActivity(UpdateService.this, 0, updateIntent,
//										0);
//								// 设置通知栏显示内容
//								updateNotification.icon = R.drawable.ic_launcher;
//								updateNotification.tickerText = "开始下载";
//								updateNotification.setLatestEventInfo(UpdateService.this, "易袋购", "0%",
//										updatePendingIntent);
//								// 发出通知
//								updateNotificationManager.notify(0, updateNotification);
//								new Thread(new updateRunnable(updateUrl)).start();// 这个是下载的重点，是下载的过程
//								dialog.dismiss();
//							}
//						}).setPositiveButton("稍后更新", new OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								// TODO Auto-generated method stub
//								dialog.dismiss();
//							}
//						}).create();
//						//声明我们要弹出的这个提示框是一个系统的提示框，即全局性质的提示框，所以只要手机处于开机状态，无论它现在处于何种界面之下，只要调用alter.show()，就会弹出提示框来。
//						dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//						dialog.show();
						
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
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				System.err.println("完成完成完成完成完成完成完成");
			}
		});
	}
	private Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				
			case DOWNLOAD_COMPLETE:
				// 点击安装PendingIntent
				Uri uri = Uri.fromFile(updateFile);
				Intent installIntent = new Intent(Intent.ACTION_VIEW);
				installIntent.setDataAndType(uri,
						"application/vnd.android.package-archive");

				updatePendingIntent = PendingIntent.getActivity(
						UpdateService.this, 0, installIntent, 0);

				updateNotification.defaults = Notification.DEFAULT_SOUND;// 铃声提醒
				updateNotification.setLatestEventInfo(UpdateService.this,
						"易袋购", "下载完成,点击安装。", updatePendingIntent);
				updateNotificationManager.notify(0, updateNotification);

				// 停止服务
				stopService(updateIntent);
			case DOWNLOAD_FAIL:
				// 下载失败
				updateNotification.setLatestEventInfo(UpdateService.this,
						"易袋购", "下载完成,点击安装。", updatePendingIntent);
				updateNotificationManager.notify(0, updateNotification);
			default:
				stopService(updateIntent);
			}
		}
	};
	private Dialog updateDialog;

	public long downloadUpdateFile(String downloadUrl, File saveFile)
			throws Exception {

		HttpURLConnection httpConnection = null;
		InputStream is = null;
		FileOutputStream fos = null;

		try {
			URL url = new URL(downloadUrl);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection
					.setRequestProperty("User-Agent", "PacificHttpClient");
			if (currentSize > 0) {
				httpConnection.setRequestProperty("RANGE", "bytes="
						+ currentSize + "-");
			}
			httpConnection.setConnectTimeout(10000);
			httpConnection.setReadTimeout(20000);
			updateTotalSize = httpConnection.getContentLength();
			if (httpConnection.getResponseCode() == 404) {
				throw new Exception("fail!");
			}
			is = httpConnection.getInputStream();
			fos = new FileOutputStream(saveFile, false);
			byte buffer[] = new byte[4096];
			int readsize = 0;
			while ((readsize = is.read(buffer)) > 0) {
				fos.write(buffer, 0, readsize);
				totalSize += readsize;
				// 为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
				if ((downloadCount == 0)
						|| (int) (totalSize * 100 / updateTotalSize) - 10 > downloadCount) {
					downloadCount += 10;

					updateNotification.setLatestEventInfo(UpdateService.this,
							"正在下载", (int) totalSize * 100 / updateTotalSize
									+ "%", updatePendingIntent);

					
					/***
					 * 在这里我们用自定的view来显示Notification
					 */
					updateNotification.contentView = new RemoteViews(
							getPackageName(), R.layout.notification_item);
					updateNotification.contentView.setTextViewText(
							R.id.notificationTitle, "正在下载");
					updateNotification.contentView.setProgressBar(
							R.id.notificationProgress, 100, downloadCount, false);
					
					updateNotificationManager.notify(0, updateNotification);
				}
			}
		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
		return totalSize;
	}

	class updateRunnable implements Runnable {
		Message message = updateHandler.obtainMessage();
		private String updateUrl1;
		
		public updateRunnable(String updateUrl1) {
			super();
			this.updateUrl1 = updateUrl1;
		}

		public void run() {
			message.what = DOWNLOAD_COMPLETE;
			
			try {
				// 增加权限<USES-PERMISSION
				// android:name="android.permission.WRITE_EXTERNAL_STORAGE">;
				if (!updateDir.exists()) {
					updateDir.mkdirs();
				}
				if (!updateFile.exists()) {
					updateFile.createNewFile();
				}
				// 下载函数，以QQ为例子
				// 增加权限<USES-PERMISSION
				// android:name="android.permission.INTERNET">;
				Log.i("url","?"+ updateUrl1);
				long downloadSize = downloadUpdateFile(
						updateUrl1,
						updateFile);
				
				if (downloadSize > 0) {
					// 下载成功
					updateHandler.sendMessage(message);
				} 
			} catch (Exception ex) {
				ex.printStackTrace();
				message.what = DOWNLOAD_FAIL;
				// 下载失败
				updateHandler.sendMessage(message);
			}
		}
	}
}
