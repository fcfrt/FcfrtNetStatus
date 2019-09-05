package com.fcfrt.netbua;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

/**
 * 项目名称：
 * 类名称：FcfrtNetStatusBus
 * 类描述：
 * 作者：FCFRT
 * 创建时间： 2019/7/1-14:33
 * 邮箱：FCFRT_ADMIN@163.COM
 * 修改简介：
 */
public class FcfrtNetStatusBus {

    private Application application;
    private FcfrtNetStatusReceiver receiver;

    public FcfrtNetStatusBus() {
        receiver = new FcfrtNetStatusReceiver();
    }

    private static class HolderClass {
        private static final FcfrtNetStatusBus instance = new FcfrtNetStatusBus();
    }

    public static FcfrtNetStatusBus getInstance() {
        return HolderClass.instance;
    }

    public Application getApplication() {
        if (application == null) {
            throw new RuntimeException("application is empty");
        }
        return application;
    }



    public void init(Application application) {
        if (application == null) {
            throw new IllegalArgumentException("application is empty");
        }
        this.application = application;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager.NetworkCallback networkCallback = new FcfrtNetworkCallbackImpl(receiver);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            ConnectivityManager manager = (ConnectivityManager) FcfrtNetStatusBus
                    .getInstance().getApplication()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                manager.registerNetworkCallback(request, networkCallback);
            }
        }

    }

    public void register(Object mContext) {
        if (application == null) {
            throw new IllegalArgumentException("you must NetStatusBus.getInstance().init(getApplication) first");
        }
        receiver.registerObserver(mContext);
    }

    public void unregister(Object mContext) {
        receiver.unRegisterObserver(mContext);
    }

    public void unregisterAllObserver() {
        receiver.unRegisterAllObserver();
    }
}
