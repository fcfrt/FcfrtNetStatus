package com.fcfrt.netbua.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.fcfrt.netbua.FcfrtNetStatusBus;
import com.fcfrt.netbua.type.NetType;

/**
 * 项目名称：
 * 类名称：NetworkUtils
 * 类描述：
 * 作者：FCFRT
 * 创建时间： 2019/7/1-14:32
 * 邮箱：FCFRT_ADMIN@163.COM
 * 修改简介：
 */

public class FcfrtNetworkUtils {

    /**
     * 网络是否可用
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) FcfrtNetStatusBus.getInstance()
                .getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }

        NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
        if (info != null) {
            for (NetworkInfo networkInfo : info) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 获取当前的网络类型
     */
    public static NetType getNetType() {
        ConnectivityManager connectivityManager = (ConnectivityManager) FcfrtNetStatusBus.getInstance()
                .getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return NetType.NONE;
        }
        //获取当前激活的网络连接信息
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null) {
            return NetType.NONE;
        }
        int type = info.getType();

        if (type == ConnectivityManager.TYPE_MOBILE) {

            return NetType.MOBILE;

        } else if (type == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }
        return NetType.NONE;
    }


    /**
     * 打开网络设置界面
     */
    public static void openSetting(Context context, int requestCode) {

        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

}
