package com.spidy.instagramclone.Utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri: Uri, folderName: String, callback: (String?) -> Unit) {
    // in FireStore if it didn't get the reference of the folderName it will simply create and
    // if it get the reference it will put data into that.
    var imageUrl: String?
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener { it ->
            it.storage.downloadUrl.addOnSuccessListener {
                imageUrl = it.toString()
                callback(imageUrl)
            }
        }
}

fun uploadVideo(
    uri: Uri,
    folderName: String,
    progressDialog: ProgressDialog,
    callback: (String?) -> Unit
) {
    // in FireStore if it didn't get the reference of the folderName it will simply create and
    // if it get the reference it will put data into that.
    var imageUrl: String?
    progressDialog.setTitle("Uploading...")
    progressDialog.show()
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener { it ->
            it.storage.downloadUrl.addOnSuccessListener {
                imageUrl = it.toString()
                progressDialog.dismiss()
                callback(imageUrl)
            }
        }.addOnProgressListener {
            var uploadedValue: Float = (it.bytesTransferred / it.totalByteCount * 100).toFloat()
            progressDialog.setMessage("Uploaded $uploadedValue %")
        }
}