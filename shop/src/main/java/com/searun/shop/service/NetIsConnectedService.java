package com.searun.shop.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.searun.shop.application.MyApplication;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.utils.NetUtils;

public class NetIsConnectedService extends Service {
    private static MyApplication app;
    public static  String URL ;
    public NetIsConnectedService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        app = (MyApplication) getApplication();
        URL = app.getBaseUrl();
       new Thread(new Runnable() {
           @Override
           public void run() {
               Log.i("XXXXXYYYYYYYY" , NetUtils.checkURL("http://www.51egoods.com" + "/apps/") + "");
               if (!NetUtils.checkURL("http://www.51egoods.com")){
//                   BASE_URL = "http://221.226.22.84/apps/";
                  HttpUtil.setBaseUrl("http://221.226.22.84");
               } else {
                   HttpUtil.setBaseUrl("http://www.51egoods.com");
               }
           }
       }).start();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
