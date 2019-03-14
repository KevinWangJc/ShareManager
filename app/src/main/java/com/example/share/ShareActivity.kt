package com.example.share

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import com.base.library.share.base.BaseShareActivity
import com.base.library.share.common.constants.ShareConstants
import kotlinx.android.synthetic.main.activity_share.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Description:
 * Share Page
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/3/14
 */
@SuppressLint("SetTextI18n")
class ShareActivity : BaseShareActivity() {

    private var localVideoUri: Uri = Uri.EMPTY
    private var localImageUri: Uri = Uri.EMPTY

    companion object {
        const val REQUEST_CODE_CHOOSE_LOCAL_IMAGE = 1008
        const val REQUEST_CODE_CHOOSE_LOCAL_VIDEO = 1009
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        initView()
    }

    private fun initView() {
        /**facebook分享文字*/
        btn_share_text_by_facebook.setOnClickListener {
            shareText(
                ShareConstants.FACEBOOK,
                "This manager is as steady as an old dog!"
            )
        }

        /**facebook分享链接*/
        btn_share_link_by_facebook.setOnClickListener {
            shareLink(
                ShareConstants.FACEBOOK,
                "https://github.com/wkxjc/ShareManager",
                "#ShareManager",
                "This manager is as steady as an old dog!"
            )
        }

        /**facebook分享bitmap图片*/
        btn_share_bitmap_by_facebook.setOnClickListener {
            shareImage(
                ShareConstants.FACEBOOK,
                BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                "#ShareManager"
            )
        }

        /**选择本地图片*/
        btn_choose_local_image.setOnClickListener {
            startActivityForResult(Intent.createChooser(Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }, "Choose App"), REQUEST_CODE_CHOOSE_LOCAL_IMAGE)
        }

        /**facebook分享本地图片*/
        btn_share_local_image_by_facebook.setOnClickListener {
            if (!checkLocalImageUri()) return@setOnClickListener
            shareImage(
                ShareConstants.FACEBOOK,
                localImageUri,
                "#ShareManager"
            )
        }

        /**选择本地视频*/
        btn_choose_local_video.setOnClickListener {
            startActivityForResult(Intent.createChooser(Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "video/*"
            }, "Choose App"), REQUEST_CODE_CHOOSE_LOCAL_VIDEO)
        }

        /**facebook分享本地视频*/
        btn_share_video_by_facebook.setOnClickListener {
            if (!checkLocalVideoUri()) return@setOnClickListener
            shareVideo(
                ShareConstants.FACEBOOK,
                localVideoUri,
                "#ShareManager"
            )
        }

        /**facebook分享多张图片、视频*/
        btn_share_media_by_facebook.setOnClickListener {
            if (!checkLocalVideoUri()) return@setOnClickListener
            shareMedia(
                ShareConstants.FACEBOOK,
                listOf(
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round),
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
                ),
                listOf(localVideoUri, localVideoUri, localVideoUri),
                "#ShareManager"
            )
        }

        /**twitter分享文字*/
        btn_share_text_by_twitter.setOnClickListener {
            shareText(
                ShareConstants.TWITTER,
                "This manager is as steady as an old dog!"
            )
        }

        /**twitter分享bitmap图片*/
        btn_share_bitmap_by_twitter.setOnClickListener {
            shareImage(
                ShareConstants.TWITTER,
                BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                "#ShareManager"
            )
        }

        /**twitter分享本地图片*/
        btn_share_local_image_by_twitter.setOnClickListener {
            if (!checkLocalImageUri()) return@setOnClickListener
            shareImage(
                ShareConstants.TWITTER,
                localImageUri,
                "#ShareManager"
            )
        }

        /**Email分享*/
        btn_send_text_email.setOnClickListener {
            sendEmail(
                "This manager is as steady as an old dog!",
                "ShareManager"
            )
        }

        btn_send_image_email.setOnClickListener {
            if (!checkLocalImageUri()) return@setOnClickListener
            sendImageEmail(
                localImageUri,
                "This manager is as steady as an old dog!",
                "ShareManager"
            )
        }

        btn_send_video_email.setOnClickListener {
            if (!checkLocalVideoUri()) return@setOnClickListener
            sendVideoEmail(
                localVideoUri,
                "This manager is as steady as an old dog!",
                "ShareManager"
            )
        }

        btn_send_media_email.setOnClickListener {
            sendMediaEmail(
                listOf(
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round),
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
                ),
                listOf(localVideoUri),
                "This manager is as steady as an old dog!",
                "ShareManager"
            )
        }

        /**SMS分享*/
        btn_send_sms.setOnClickListener {
            sendSMS(
                "This manager is as steady as an old dog!",
                "10086"
            )
        }
    }

    private fun checkLocalVideoUri(): Boolean {
        if (localVideoUri == Uri.EMPTY) {
            tv_local_video_uri.text = "please click \"Choose Local Video\" button to select a local video first"
            return false
        }
        return true
    }

    private fun checkLocalImageUri(): Boolean {
        if (localImageUri == Uri.EMPTY) {
            tv_local_image_uri.text = "please click \"Choose Local Image\" button to select a local image first"
            return false
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE_LOCAL_VIDEO && resultCode == Activity.RESULT_OK && data != null) {
            localVideoUri = data.data
            tv_local_video_uri.text = "Local video uri: $localVideoUri"
        }
        if (requestCode == REQUEST_CODE_CHOOSE_LOCAL_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            localImageUri = data.data
            tv_local_image_uri.text = "Local image uri: $localImageUri"
        }
    }

    override fun onShareSuccess(type: String) {
        tv_share_status.append("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())}: $type 分享成功\n\n")
    }

    override fun onShareFail(type: String, cause: String) {
        tv_share_status.append("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())}: $type 分享失败\ncause:$cause\n\n")
    }

}
