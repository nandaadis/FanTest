package com.example.fantest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fantest.databinding.ActivityHomeBinding
import com.example.fantest.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val firebaseAuth: FirebaseAuth by lazy {
            FirebaseAuth.getInstance()
        }

        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        binding.tv2.text = """
            |halo. ${firebaseAuth.currentUser?.displayName}
            |status ${if (firebaseAuth.currentUser?.isEmailVerified == true) {"Verified"} else {"Not Verified"} }
        """.trimMargin()
    }
}