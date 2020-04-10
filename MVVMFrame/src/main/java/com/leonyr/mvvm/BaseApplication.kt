package com.leonyr.mvvm

import android.app.Application
import android.content.Context


open class BaseApplication : Application() {

    companion object {
       lateinit var App: Application
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        App = this
    }

}