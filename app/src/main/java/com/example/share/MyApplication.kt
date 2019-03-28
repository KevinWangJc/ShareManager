package com.example.share

import android.app.Application
import com.base.library.login.LoginManager

/**
 * Description:
 * App Entry
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/3/14
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LoginManager.init(this)
    }
}