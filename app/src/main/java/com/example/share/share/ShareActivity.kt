package com.example.share.share

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.base.library.share.base.BaseShareActivity
import com.base.library.share.common.constants.ShareConstants
import com.example.share.R
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

        /**获取 READ_EXTERNAL_STORAGE 权限*/
        btn_get_permission.setOnClickListener {
            requestPermission()
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

    private fun checkLocalImageUri(): Boolean {
        if (localImageUri == Uri.EMPTY) {
            val noImageHint = "please click \"Choose Local Image\" button to select a local image first"
            tv_local_image_uri.text = noImageHint
            Toast.makeText(this, noImageHint, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun checkLocalVideoUri(): Boolean {
        if (localVideoUri == Uri.EMPTY) {
            val noVideoHint = "please click \"Choose Local Video\" button to select a local video first"
            tv_local_video_uri.text = noVideoHint
            Toast.makeText(this, noVideoHint, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
            return
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1)
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
        val loginResult = "${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())}: $type 分享成功\n\n"
        tv_share_status.append(loginResult)
        Toast.makeText(this, loginResult, Toast.LENGTH_SHORT).show()
    }

    override fun onShareFail(type: String, cause: String) {
        val loginResult = "${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())}: $type 分享失败\ncause:$cause\n\n"
        tv_share_status.append(loginResult)
        Toast.makeText(this, loginResult, Toast.LENGTH_SHORT).show()
    }

}
