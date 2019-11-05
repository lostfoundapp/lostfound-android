package com.lostfoundapp.storage

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class SharingImage {

    companion object {
        fun storeImage(image: Bitmap, context: Context) {
            val pictureFile = getOutputMediaFile(context)
            if (pictureFile == null) {
                Log.d(
                        "caminho",
                        "Error creating media file, check storage permissions: "
                )
                return
            }
            try {
                val fos = FileOutputStream(pictureFile)
                image.compress(Bitmap.CompressFormat.PNG, 90, fos)
                fos.close()
            } catch (e: FileNotFoundException) {
                Log.d("caminho", "File not found: " + e.message)
            } catch (e: IOException) {
                Log.d("caminho", "Error accessing file: " + e.message)
            }

        }
        fun getOutputMediaFile(context: Context): File? {
            val mediaStorageDir = File(
                    context.getExternalCacheDir().toString(), "image"
            )
            if (!mediaStorageDir.exists())
            {
                if (!mediaStorageDir.mkdirs())
                {
                    return null
                }
            }
            val mediaFile: File
            val mImageName = "LostFound.png"
            mediaFile = File(mediaStorageDir.getPath() + File.separator + mImageName)
            Log.d("caminho", "file: " + mediaFile.toString())
            return mediaFile
        }
    }
}