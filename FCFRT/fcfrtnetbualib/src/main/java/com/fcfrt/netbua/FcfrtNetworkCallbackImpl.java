package com.fcfrt.netbua;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.support.annotation.RequiresApi;


import com.fcfrt.netbua.utils.FcfrtNetworkUtils;

/**
 * 项目名称：
 * 类名称：FcfrtNetworkCallbackImpl
 * 类描述：
 * 作者：FCFRT
 * 创建时间： 2019/7/1-14:33
 * 邮箱：FCFRT_ADMIN@163.COM
 * 修改简介：
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FcfrtNetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
    private FcfrtNetStatusReceiver mReceiver;

    public FcfrtNetworkCallbackImpl(FcfrtNetStatusReceiver receiver) {
        mReceiver = receiver;
    }


    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        mReceiver.post(FcfrtNetworkUtils.getNetType());
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        mReceiver.post(FcfrtNetworkUtils.getNetType());
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
    }

}
