[![](https://jitpack.io/v/wkxjc/ShareManager.svg)](https://jitpack.io/#wkxjc/ShareManager)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

# ShareManager English Doc | [中文文档](https://github.com/wkxjc/ShareManager/blob/master/README_CN.MD)

This is a library to help you add share by Facebook, Twitter, Email and SMS to your Android app.

## Screenshot

<img src="screenshots/login.gif" width=25%>
<img src="screenshots/shareByFacebook.gif" width=25%>
<img src="screenshots/shareLocalByFacebook.gif" width=25%>
<img src="screenshots/shareByTwitter.gif" width=25%>
<img src="screenshots/sendEmail.gif" width=25%>
<img src="screenshots/sendSms.gif" width=25%>

## Download

Step 1. Add it in your root build.gradle at the end of repositories:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add the dependency

```
dependencies {
        implementation 'com.github.wkxjc:ShareManager:1.0.1'
        // make sure you have added support-design library, you can use your own version
        implementation 'com.android.support:design:28.0.0'
}
```

## How to use

Share by Facebook, Twitter, Email or SMS just need one line of code:

```
btn_share_text_by_facebook.setOnClickListener {
    shareText(
            ShareConstants.FACEBOOK,
            "This manager is as steady as an old dog!"
    )
}

btn_share_link_by_facebook.setOnClickListener {
    shareLink(
            ShareConstants.FACEBOOK,
            "https://github.com/wkxjc/ShareManager",
            "#ShareManager",
            "This manager is as steady as an old dog!"
    )
}

btn_share_bitmap_by_facebook.setOnClickListener {
    shareImage(
            ShareConstants.FACEBOOK,
            BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
            "#ShareManager"
    )
}

btn_share_local_image_by_facebook.setOnClickListener {
    shareImage(
            ShareConstants.FACEBOOK,
            localImageUri,
            "#ShareManager"
    )
}

btn_share_video_by_facebook.setOnClickListener {
    shareVideo(
            ShareConstants.FACEBOOK,
            localVideoUri,
            "#ShareManager"
    )
}

btn_share_media_by_facebook.setOnClickListener {
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

btn_share_text_by_twitter.setOnClickListener {
    shareText(
            ShareConstants.TWITTER,
            "This manager is as steady as an old dog!"
    )
}

btn_share_local_image_by_twitter.setOnClickListener {
    shareImage(
            ShareConstants.TWITTER,
            localImageUri,
            "#ShareManager"
    )
}

btn_send_text_email.setOnClickListener {
    sendEmail(
        "This manager is as steady as an old dog!",
        "ShareManager",
        arrayOf("wangkaixuanjc@gmail.com"),
        arrayOf("1932746358@qq.com")
    )
}
btn_send_image_email.setOnClickListener {
    sendImageEmail(
        localImageUri,
        "This manager is as steady as an old dog!",
        "ShareManager",
        arrayOf("wangkaixuanjc@gmail.com"),
        arrayOf("1932746358@qq.com")
    )
}
btn_send_video_email.setOnClickListener {
    sendVideoEmail(
        localVideoUri,
        "This manager is as steady as an old dog!",
        "ShareManager",
        arrayOf("wangkaixuanjc@gmail.com"),
        arrayOf("1932746358@qq.com")
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
        "ShareManager",
        arrayOf("wangkaixuanjc@gmail.com"),
        arrayOf("1932746358@qq.com")
    )
}

btn_send_sms.setOnClickListener {
    sendSMS(
            "This manager is as steady as an old dog!",
            "10086"
    )
}
```

## Configuration

Before that, you need some prepare work.

Step 1. If you want to add share by Facebook. add this code in your AndroidManifest:
```
<provider
    android:name="com.facebook.FacebookContentProvider"
    android:authorities="com.facebook.app.FacebookContentProviderYOUR_FACEBOOK_APP_ID"
    android:exported="true"/>
```

Replace "YOUR_FACEBOOK_APP_ID" in provider with your app's facebook_app_id.

Step 2. Share by Twitter can only be used after the user logs in by Twitter. So firstly make sure the user is logged in by Twitter if you want to use it.
I recommend you use my [LoginManager](https://github.com/wkxjc/LoginManager) library. It's as convenient as this ShareManager.If you don't need Twitter
share function, ignore it.

Step 3. Share local image by Twitter need READ_EXTERNAL_STORAGE permission, make sure you've got it before use this function.

## How to get facebook_app_id?

You can find the steps in [LoginManager](https://github.com/wkxjc/LoginManager) library.

## Do not want to extends BaseShareActivity?

You can directly use ShareManager like BaseShareActivity:

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

    fun sendEmail(
        emailBody: String = "",
        emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        shareManager.sendEmail(emailBody, emailSubject, tos, ccs)
    }

    fun sendImageEmail(
        image: Any,
        emailBody: String = "",
        emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        shareManager.sendImageEmail(image, emailBody, emailSubject, tos, ccs)
    }

    fun sendVideoEmail(
        video: Uri,
        emailBody: String = "",
        emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        shareManager.sendVideoEmail(video, emailBody, emailSubject, tos, ccs)
    }

    fun sendMediaEmail(
        imageList: List<Any> = ArrayList(),
        videoList: List<Uri> = ArrayList(),
        emailBody: String = "",
        emailSubject: String = "",
        tos: Array<String> = arrayOf(),
        ccs: Array<String> = arrayOf()
    ) {
        shareManager.sendMediaEmail(imageList, videoList, emailBody, emailSubject, tos, ccs)
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
        if (isFinishing) {
            shareManager.release()
        }
    }
}
```

## Do not want to add all share method?

You can use any of these features separately.

## Bug Feedback

If you have any questions or suggestions, feel free to create an [issue](https://github.com/wkxjc/ShareManager/issues).

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