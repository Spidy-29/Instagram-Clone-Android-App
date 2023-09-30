package com.spidy.instagramclone.Post

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.spidy.instagramclone.HomeActivity
import com.spidy.instagramclone.Utils.POST
import com.spidy.instagramclone.Utils.POST_FOLDER
import com.spidy.instagramclone.Utils.USER_NODE
import com.spidy.instagramclone.Utils.uploadImage
import com.spidy.instagramclone.databinding.ActivityPostBinding
import com.spidy.instagramclone.models.Post
import com.spidy.instagramclone.models.User

class PostActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageUrl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER) { url ->
                if (url != null) {
                    binding.selectImage.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.btnPost.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    var user = it.toObject<User>()!!
                    val post: Post = Post(
                        postUrl = imageUrl!!,
                        caption = binding.caption.editText?.text.toString(),
                        uid = Firebase.auth.currentUser!!.uid,
                        time = System.currentTimeMillis().toString()
                    )

                    Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document()
                            .set(post)
                            .addOnSuccessListener {
                                startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                                finish()
                            }
                    }
                }
        }
    }
}