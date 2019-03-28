[![](https://jitpack.io/v/wkxjc/ShareManager.svg)](https://jitpack.io/#wkxjc/ShareManager)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

# ShareManager [English Doc](https://github.com/wkxjc/ShareManager/blob/master/README.MD) | 中文文档

这是一个帮助添加Facebook、Twitter分享，邮件、短信分享的库。

## 下载

第一步. 在根目录的 build.gradle 中添加:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

第二步. 在app模块的build.gradle中添加

```
dependencies {
        implementation 'com.github.wkxjc:ShareManager:1.0.1'
        // make sure you have added support-design library, you can use your own version
        implementation 'com.android.support:design:28.0.0'
}
```

## 怎么使用

Facebook、Twitter分享，邮件、短信分享仅需一行代码:

```
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
            val noVideoHint = "please click \"Choose Local Video\" button to select a local video first"
            tv_local_video_uri.text = noVideoHint
            Toast.makeText(this,noVideoHint,Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun checkLocalImageUri(): Boolean {
        if (localImageUri == Uri.EMPTY) {
            val noImageHint = "please click \"Choose Local Image\" button to select a local image first"
            tv_local_image_uri.text = noImageHint
            Toast.makeText(this,noImageHint,Toast.LENGTH_SHORT).show()
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
        val loginResult = "${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())}: $type 分享成功\n\n"
        tv_share_status.append(loginResult)
        Toast.makeText(this,loginResult,Toast.LENGTH_SHORT).show()
    }

    override fun onShareFail(type: String, cause: String) {
        val loginResult = "${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())}: $type 分享失败\ncause:$cause\n\n"
        tv_share_status.append(loginResult)
        Toast.makeText(this,loginResult,Toast.LENGTH_SHORT).show()
    }

}
```

## 配置

在这之前，你需要做一些准备工作.

第一步. Twitter分享必须在用户使用Twitter登录之后. 所以如果你需要使用Twitter分享，首先确保用户已经使用了Twitter登录.
我推荐你使用我的 [LoginManager](https://github.com/wkxjc/LoginManager) 库. 它和这个ShareManager库一样方便.
如果你不需要使用Twitter分享，当我没说。

第二步. 如果你需要使用Facebook登录，将以下代码添加到你的AndroidManifest:
```
<provider
    android:name="com.facebook.FacebookContentProvider"
    android:authorities="com.facebook.app.FacebookContentProvider你的FACEBOOK_APP_ID"
    android:exported="true"/>
```

将provider中 "你的FACEBOOK_APP_ID" 替换为你的app的 facebook_app_id.

## 如何获取 facebook_app_id?

你可以在 [LoginManager](https://github.com/wkxjc/LoginManager) 库中找到获取facebook_app_id的步骤.

## 不希望继承 BaseShareActivity?

你可以仿照 BaseShareActivity, 直接使用 ShareManager:

```
abstract class BaseShareActivity : AppCompatActivity(), OnShareListener {

    private val shareManager by lazy { ShareManager(this, this) }

    fun shareText(type: String, text: String) {
        shareManager.shareText(type, text)
    }

    fun shareLink(type: String, link: String, tag: String = "", quote: String = "") {
        shareManager.shareLink(type, link, tag, quote)
    }

    fun shareImage(type: String, image: Any?, tag: String = "") {
        shareManager.shareImage(type, image, tag)
    }

    fun shareVideo(type: String, videoUri: Uri, tag: String = "") {
        shareManager.shareVideo(type, videoUri, tag)
    }

    fun shareMedia(type: String, imageList: List<Any>, videoUriList: List<Uri>, tag: String = "") {
        shareManager.shareMedia(type, imageList, videoUriList, tag)
    }

    fun sendEmail(emailBody: String = "", emailSubject: String = "") {
        shareManager.sendEmail(emailBody, emailSubject)
    }

    fun sendImageEmail(image: Any, emailBody: String = "", emailSubject: String = "") {
        shareManager.sendImageEmail(image, emailBody, emailSubject)
    }

    fun sendVideoEmail(video: Uri, emailBody: String = "", emailSubject: String = "") {
        shareManager.sendVideoEmail(video, emailBody, emailSubject)
    }

    fun sendMediaEmail(imageList: List<Any> = ArrayList(), videoList: List<Uri> = ArrayList(), emailBody: String = "", emailSubject: String = "") {
        shareManager.sendMediaEmail(imageList, videoList, emailBody, emailSubject)
    }

    fun sendSMS(smsBody: String = "", phoneNumber: String = "") {
        shareManager.sendSMS(smsBody, phoneNumber)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        shareManager.handleActivityResult(requestCode, resultCode, data)
    }

    override fun onStop() {
        super.onStop()
        if(isFinishing){
            shareManager.release()
        }
    }
}
```

## 不希望添加全部的分享方式？

你可以单独使用其中任何一个功能。

## Bug 反馈

如果您有任何反馈或建议，欢迎提交到 [Github issues](https://github.com/wkxjc/ShareManager/issues).

## LICENSE

    Copyright (c) 2019-present, Alpinist Wang Contributors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.