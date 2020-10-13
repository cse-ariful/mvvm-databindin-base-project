package com.inverse.photoeditor.screens.imagePicker

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inverse.core.async.Courotines
import com.inverse.photoeditor.models.ImageFile
import kotlinx.coroutines.delay


class ImagePickerViewModel : ViewModel() {
    val title = MutableLiveData<String>("Images")
    val images = MutableLiveData<ArrayList<ImageFile>>()
    val loading = MutableLiveData<Boolean>(false)
    var initialized = false

    fun getAllImages(crsr: Cursor?, activity: Activity) {
        initialized=true
        loading.postValue(true)
        val imageList: ArrayList<ImageFile> = ArrayList()
        Courotines.ioThenMain({
            var cursor =crsr
            var i = 0
            if (cursor == null) {
                val resolver: ContentResolver = activity.contentResolver
                cursor = resolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null,
                    null,
                    null,
                    null
                )
                if (cursor != null) {
                    while (i < cursor.count) {
                        Log.d(TAG, "getAllImages: $i")
                        cursor.moveToPosition(i)
                        val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                        val id = cursor.getLong(fieldIndex)
                        val imageUri: Uri =
                            ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                        val photo = ImageFile("",imageUri.toString(),"",false)
                        imageList.add(photo)
                        i++
                    }
                    cursor.close()
                }
            }
        },{
            loading.postValue(false)
            images.postValue(imageList)
        })
    }
    private fun getArraysPhotos(alImages: ArrayList<ImageFile>): ArrayList<ImageFile> {
        val arrayList: ArrayList<ImageFile> = ArrayList()
        for (i in 0 until alImages.size) {
            /* if (getFileName(alImages[i].getUri()).contains("YOURIMAGENAME_")) {
                 arrayList.add(alImages[i])
             }*/
        }
        return arrayList
    }
    companion object{
        const val TAG="imageViewModel"
    }
}