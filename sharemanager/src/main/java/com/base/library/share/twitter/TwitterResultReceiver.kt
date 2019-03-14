package com.base.library.share.twitter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.reactivex.Observable

/**
 * Description:
 * Twitter分享结果广播接收器
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/5
 */
class TwitterResultReceiver : BroadcastReceiver() {

    // 分享结果观察者
    var twitterResultObservable: Observable<String>? = null

    override fun onReceive(context: Context, intent: Intent) {
        twitterResultObservable = Observable.just(intent.action)
    }
}