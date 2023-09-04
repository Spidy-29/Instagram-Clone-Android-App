package com.spidy.instagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.spidy.instagramclone.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        binding.signUpBtn.setOnClickListener { 
            if(binding.name.editText?.text.toString().equals("") or 
                    binding.email.editText?.text.toString().equals("") or 
                    binding.password.editText?.text.toString().equals("")){
                Toast.makeText(this@SignUpActivity, "Please Fill the Required Fields", Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString(),
                ).addOnCompleteListener {
                    result->
                    if(result.isComplete){
                        Toast.makeText(
                            this@SignUpActivity,
                            "Sign Up Successfull",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Toast.makeText(this@SignUpActivity, result.exception.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}