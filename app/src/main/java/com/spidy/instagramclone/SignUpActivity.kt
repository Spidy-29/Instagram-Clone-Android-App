package com.spidy.instagramclone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.spidy.instagramclone.Utils.USER_NODE
import com.spidy.instagramclone.Utils.USER_PROFILE_FOLDER
import com.spidy.instagramclone.Utils.uploadImage
import com.spidy.instagramclone.databinding.ActivitySignUpBinding
import com.spidy.instagramclone.models.User

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private lateinit var user: User
    private val launcher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            // this uri(Universe Resource Identifier) which is for android
            // like where is our file in android
            uploadImage(uri, USER_PROFILE_FOLDER) {
                if (it == null) {
                } else {
                    user.image = it
                    binding.addImage.visibility = View.INVISIBLE
                    binding.ivProfile.setImageURI(uri)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        user = User()
        binding.signUpBtn.setOnClickListener {
            if ((binding.name.editText?.text.toString() == "") or
                (binding.email.editText?.text.toString() == "") or
                (binding.password.editText?.text.toString() == "")
            ) {
                Toast.makeText(
                    this@SignUpActivity,
                    "Please Fill the Required Fields",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString(),
                ).addOnCompleteListener { result ->
                    if (result.isComplete) {
                        user.name = binding.name.editText?.text.toString()
                        user.email = binding.email.editText?.text.toString()
                        user.password = binding.password.editText?.text.toString()
                        Firebase.firestore.collection(USER_NODE)
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                                startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                                finish()
                            }
                    } else {
                        Toast.makeText(
                            this@SignUpActivity,
                            result.exception?.localizedMessage, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        binding.ivProfile.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.login.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }
    }
}