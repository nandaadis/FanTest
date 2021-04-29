package com.example.fantest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.fantest.databinding.ActivityForgetPassBinding
import com.example.fantest.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class ForgetPass : AppCompatActivity() {

    private lateinit var binding: ActivityForgetPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPassBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnBack.setOnClickListener { finish() }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val firebaseAuth: FirebaseAuth by lazy {
                FirebaseAuth.getInstance()
            }
            binding.progressBar.visibility = View.VISIBLE

            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "Cek email anda", Toast.LENGTH_LONG).show()
                    finish()
                    binding.progressBar.visibility = View.GONE
                }
                else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}