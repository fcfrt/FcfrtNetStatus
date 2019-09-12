package com.fcfrt.netdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.fcfrt.netbua.FcfrtNetStatusBus
import com.fcfrt.netbua.annotation.FcfrtNetSubscribe
import com.fcfrt.netbua.type.Mode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //如果点击某一个按钮需要判断网络状态可以使用
        //FcfrtNetworkUtils.isNetworkAvailable()
    }

    /**
     * 当监听到 WIFI 连接时，会调用使用该模式的所有方法
     */
    @FcfrtNetSubscribe(mode = Mode.WIFI_CONNECT)
    fun wifiChange() {
        runOnUiThread {
            tv_type.text = "wifi模式"
            iv_type.setImageResource(R.drawable.ic_wifi)
        }
    }

    /**
     * 当监听到 移动网络 连接时，会调用使用该模式的所有方法
     */
    @FcfrtNetSubscribe(mode = Mode.MOBILE_CONNECT)
    fun mobileChange() {
        runOnUiThread {
            tv_type.text = "移动模式"
            iv_type.setImageResource(R.drawable.ic_mobile)
        }

    }

    /**
     * 当失去网络连接时，会调用使用该模式的所有方法
     */
    @FcfrtNetSubscribe(mode = Mode.NONE)
    fun noneNet() {
        runOnUiThread {
            tv_type.text = "暂无网络"
            iv_type.setImageResource(R.drawable.ic_no)
        }
    }



    override fun onStart() {
        super.onStart()
        //最好放在BaseActivity内
        FcfrtNetStatusBus.getInstance().register(this)
    }

    override fun onStop() {
        super.onStop()
        //最好放在BaseActivity内
        FcfrtNetStatusBus.getInstance().unregister(this)
    }
}
