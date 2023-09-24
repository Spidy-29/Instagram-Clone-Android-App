package com.spidy.instagramclone.Post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.spidy.instagramclone.HomeActivity
import com.spidy.instagramclone.Utils.REEL
import com.spidy.instagramclone.Utils.REEL_FOLDER
import com.spidy.instagramclone.Utils.uploadVideo
import com.spidy.instagramclone.databinding.ActivityReelsBinding
import com.spidy.instagramclone.models.Reel

class ReelsActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }
    private lateinit var videoUrl: String
    lateinit var progressDialog: ProgressDialog

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER, progressDialog) { url ->
                if (url != null) {
                    videoUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)
        binding.selectReels.setOnClickListener {
            launcher.launch("video/*")
        }
        setSupportActionBar(binding.materialToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.btnPost.setOnClickListener {
            val reel: Reel = Reel(videoUrl, binding.caption.editText?.text.toString())

            Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).document()
                    .set(reel)
                    .addOnSuccessListener {
                        startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                        finish()
                    }
            }
        }
    }
}