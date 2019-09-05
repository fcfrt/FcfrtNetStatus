package com.fcfrt.netdemo

import android.app.Application
import com.fcfrt.netbua.FcfrtNetStatusBus

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FcfrtNetStatusBus.getInstance().init(this)
    }
}