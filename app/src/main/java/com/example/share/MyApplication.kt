package com.example.share

import android.app.Application
import com.base.library.login.LoginManager
import com.base.library.share.ShareManager

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
        ShareManager.init(this)
    }
}