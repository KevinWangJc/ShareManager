package com.base.library.share.twitter

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import com.base.library.share.common.constants.ShareConstants.Companion.TWITTER
import com.base.library.share.common.listener.OnShareListener
import com.base.library.share.common.util.ShareUtils
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.tweetcomposer.ComposerActivity
import com.twitter.sdk.android.tweetcomposer.TweetUploadService
import io.reactivex.disposables.Disposable

/**
 * Description:
 * Twitter分享管理类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class TwitterShareManager(private val activity: Activity, private val onShareListener: OnShareListener) {

    // 接收分享结果
    private var resultDisposable: Disposable? = null

    /**
     * 分享文字
     * @param text 文字内容
     */
    fun shareText(text: String) {
        //分享卡片参考：https://developer.twitter.com/en/docs/tweets/optimize-with-cards/overview/player-card
        shareImage(text = text)
    }

    /**
     * 分享本地图片
     * @param image 图片Bitmap或者Uri
     * @param text 文字内容
     */
    fun shareImage(image: Any? = Uri.EMPTY, text: String = "") {
        getSession() ?: let {
            onShareListener.onShareFail(TWITTER, "Twitter share fail, need Login by Twitter first")
            return
        }
        val imageUri = when (image) {
            is Bitmap -> ShareUtils.bitmap2Uri(activity, image)
            is Uri -> image
            else -> Uri.EMPTY
        }
        activity.startActivity(
            ComposerActivity.Builder(activity)
                .image(imageUri)
                .text(text)
                .session(getSession())
                .createIntent()
        )
        resultDisposable = TwitterResultReceiver().twitterResultObservable?.subscribe {
            ShareUtils.clearShareTempPictures(activity)
            when (it) {
                TweetUploadService.UPLOAD_SUCCESS -> onShareListener.onShareSuccess(TWITTER)
                TweetUploadService.TWEET_COMPOSE_CANCEL -> onShareListener.onShareFail(TWITTER, "Twitter share cancel")
                else -> onShareListener.onShareFail(
                    TWITTER,
                    "Twitter share fail, please check if the content is duplicate"
                )
            }
        }
    }

    private fun getSession(): TwitterSession? {
        return TwitterCore.getInstance().sessionManager.activeSession
    }


    fun release() {
        resultDisposable?.dispose()
    }
}