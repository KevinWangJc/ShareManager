package com.base.library.share.email

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import com.base.library.share.common.listener.OnShareListener
import com.base.library.share.common.util.ShareUtils

/**
 * Description:
 * 邮件分享管理类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/5
 */
class EmailShareManager(private val activity: Activity, private val onShareListener: OnShareListener) {
    /**
     * 发送邮件
     * @param emailBody 邮件内容
     * @param emailSubject 邮件主题
     */
    fun sendTextEmail(
        emailBody: String = "", emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        sendMediaEmail(emailBody = emailBody, emailSubject = emailSubject, tos = tos, ccs = ccs)
    }

    fun sendImageEmail(
        image: Any, emailBody: String = "", emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        sendMediaEmail(
            imageList = listOf(image),
            emailBody = emailBody,
            emailSubject = emailSubject,
            tos = tos,
            ccs = ccs
        )
    }

    fun sendVideoEmail(
        video: Uri, emailBody: String = "", emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        sendMediaEmail(
            videoList = listOf(video),
            emailBody = emailBody,
            emailSubject = emailSubject,
            tos = tos,
            ccs = ccs
        )
    }

    @Suppress("DEPRECATION")
    fun sendMediaEmail(
        imageList: List<Any> = ArrayList(),
        videoList: List<Uri> = ArrayList(),
        emailBody: String = "",
        emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        val email = Intent(Intent.ACTION_SEND_MULTIPLE)
        email.type = "application/octet-stream"
        val uriList = ArrayList<Uri>()
        for (image in imageList) {
            val imageUri = when (image) {
                is Bitmap -> ShareUtils.bitmap2Uri(activity, image)
                is Uri -> image
                else -> Uri.EMPTY
            }
            uriList.add(imageUri)
        }
        uriList.addAll(videoList)
        val uris = ArrayList(uriList.filter {
            it != Uri.EMPTY
        })
        email.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
        email.putExtra(Intent.EXTRA_TEXT, emailBody)
        email.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        email.putExtra(Intent.EXTRA_EMAIL, tos)
        email.putExtra(Intent.EXTRA_CC, ccs)
        activity.startActivity(Intent.createChooser(email, "Choose App"))
    }
}