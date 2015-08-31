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
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean isConnected = NetUtils.checkURL(HttpUtil.BASE_URL + "/apps/");
                    Log.i("XXXXXYYYYYYYY" , isConnected + "");
                    if (!isConnected){
     //                   BASE_URL = "http://221.226.22.84/apps/";
                       HttpUtil.setBaseUrl("http://221.226.22.84:8081/shopxx");
                        Log.i("111111111", "111111111111111111");
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        HttpUtil.setBaseUrl("http://www.51egoods.com");
                        Log.i("22222222", "2222222222222222");
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("3333333333333", "333333333333333");
            HttpUtil.setBaseUrl("http://221.226.22.84:8081/shopxx");
        }
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
