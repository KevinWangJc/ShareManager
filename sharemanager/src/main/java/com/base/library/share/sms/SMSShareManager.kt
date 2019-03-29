package com.base.library.share.sms

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.base.library.share.common.listener.OnShareListener

/**
 * Description:
 * 短信分享管理类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/5
 */
class SMSShareManager(private val activity: Activity, private val onShareListener: OnShareListener) {

    /**
     * 发送短信
     * @param smsBody 短信内容
     * @param phoneNumber 手机号码
     */
    fun sendSMS(smsBody: String, phoneNumber: String) {
        val sendIntent = Intent(Intent.ACTION_VIEW, Uri.parse("smsto:"))
        sendIntent.putExtra("address", phoneNumber)
        sendIntent.putExtra("sms_body", smsBody)
        sendIntent.type = "vnd.android-dir/mms-sms"
        activity.startActivity(Intent.createChooser(sendIntent,"Choose App"))
    }

}