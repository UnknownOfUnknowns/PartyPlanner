package com.example.partyplanner.domain

import android.graphics.Bitmap
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

object ImagePicker{
    var launcher : ActivityResultLauncher<PickVisualMediaRequest>? = null
    var callback : (Bitmap?) -> Unit = {}
    fun getImage(onPictureFound : (Bitmap?) -> Unit) {
        callback = onPictureFound
        if(launcher == null) {
            onPictureFound(null)
            return
        }
        launcher!!.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}