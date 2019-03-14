package com.base.library.share.twitter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * Description:
 * Twitter分享结果广播接收器
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/5
 */
class TwitterResultReceiver : BroadcastReceiver() {

    companion object {
        var disposable: Disposable? = null
        var resultObserver: Consumer<String>? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        val observer = resultObserver ?: return
        disposable = Observable.just(intent.action)
            .subscribe(observer)
    }

}