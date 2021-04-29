package com.example.fantest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.fantest.databinding.ActivityLoginBinding
import com.example.fantest.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        binding.btnForgetpass.setOnClickListener {
            val intent = Intent(this, ForgetPass::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPass.text.toString()

            val firebaseAuth: FirebaseAuth by lazy {
                FirebaseAuth.getInstance()
            }

            binding.progressBar.visibility = View.VISIBLE

            if(email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(this, Home::class.java)
                        startActivity(intent)
                        finish()

                        binding.progressBar.visibility = View.GONE
                    }
                    else{
                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_LONG).show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            } else {
                Toast.makeText(this, "Email dan Pass tidak boleh kosong", Toast.LENGTH_LONG).show()
                binding.progressBar.visibility = View.GONE

            }

        }


    }


}