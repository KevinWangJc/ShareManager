package com.base.library.share.common.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

/**
 * Description:
 * Share工具类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/5
 */
object ShareUtils {
    /**
     * Bitmap转成Uri，保存在app内部存储的 share_temp_{System.currentTimeMillis()}.png 中
     */
    fun bitmap2Uri(context: Context?, image: Bitmap): Uri {
        context ?: return Uri.EMPTY
        val fileDir = context.getExternalFilesDir(null)
        fileDir.mkdirs()
        val file = File(fileDir, "share_temp_${System.currentTimeMillis()}.png")
        val outputStream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()
        return Uri.fromFile(file)
    }

    /**
     * 删除分享时用的临时图片
     */
    fun clearShareTempPictures(context: Context?) {
        context ?: return
        synchronized(this) {
            val fileDir = context.getExternalFilesDir(null)
                .apply { mkdirs() }
            fileDir
                .list()
                .map {
                    if (it.startsWith("share_temp_")) {
                        deleteFile(File("$fileDir${File.separatorChar}$it"))
                    }
                }
        }
    }

    /**
     * 删除文件
     */
    private fun deleteFile(file: File?): Boolean {
        return file != null && (!file.exists() || file.isFile && file.delete())
    }
}