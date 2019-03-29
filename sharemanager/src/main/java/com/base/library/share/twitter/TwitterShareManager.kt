package com.base.library.share.twitter

import android.app.Activity
import android.net.Uri
import com.base.library.share.common.constants.ShareConstants.Companion.TWITTER
import com.base.library.share.common.listener.OnShareListener
import com.base.library.share.common.util.ShareUtils
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.tweetcomposer.ComposerActivity
import com.twitter.sdk.android.tweetcomposer.TweetUploadService
import io.reactivex.functions.Consumer

/**
 * Description:
 * Twitter分享管理类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class TwitterShareManager(private val activity: Activity, private val onShareListener: OnShareListener) {

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
        if (!isValid(image, text)) return
        TwitterResultReceiver.resultObserver = Consumer {
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
        val builder = ComposerActivity.Builder(activity).session(getSession())
        if (image != Uri.EMPTY) builder.image(image as Uri)
        if (!text.isEmpty()) builder.text(text)
        activity.startActivity(builder.createIntent())
    }

    private fun isValid(image: Any? = Uri.EMPTY, text: String = ""): Boolean {
        if (image !is Uri) {
            onShareListener.onShareFail(TWITTER, "Twitter share fail, Twitter share only support local image uri")
            return false
        }
        getSession() ?: let {
            onShareListener.onShareFail(TWITTER, "Twitter share fail, need Login by Twitter first")
            return false
        }
        if (image == Uri.EMPTY && text.isEmpty()) {
            onShareListener.onShareFail(TWITTER, "Twitter share fail, both image and text is empty")
            return false
        }
        return true
    }

    private fun getSession(): TwitterSession? {
        return TwitterCore.getInstance().sessionManager.activeSession
    }

    fun release() {
        TwitterResultReceiver.disposable?.dispose()
        TwitterResultReceiver.resultObserver = null
        TwitterResultReceiver.disposable = null
    }
}