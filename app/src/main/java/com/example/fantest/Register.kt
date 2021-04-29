package com.example.fantest

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fantest.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest


class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val firebaseAuth: FirebaseAuth by lazy {
            FirebaseAuth.getInstance()
        }


        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPass.text.toString()
            val passConfirm = binding.etConfirmPass.text.toString()
            val name = binding.etNama.text.toString()

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()

            binding.progressBar.visibility = View.VISIBLE

//            val actionCodeSettings =
//                ActionCodeSettings.newBuilder() // URL you want to redirect back to. The domain (www.example.com) for this
//                    // URL must be whitelisted in the Firebase Console.
//                    .setUrl("fantest-7e966.firebaseapp.com ") // This must be true
//                    .setHandleCodeInApp(true)
//                    .setIOSBundleId("com.example.ios")
//                    .setAndroidPackageName(
//                        "com.example.android",
//                        true,  /* installIfNotAvailable */
//                        "12" /* minimumVersion */
//                    )
//                    .build()

            if (name.length < 3) {
                Toast.makeText(this, "tidak boleh kosong dan minimal 3 karakter", Toast.LENGTH_LONG)
                    .show()
                binding.progressBar.visibility = View.GONE
            } else if (email.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(email)
                    .matches() == false
            ) {
                Toast.makeText(this, "Alamat email kosong atau format salah", Toast.LENGTH_LONG)
                    .show()
                binding.progressBar.visibility = View.GONE
            } else if (pass.isEmpty() || passConfirm.isEmpty() || pass != passConfirm) {
                Toast.makeText(
                    this,
                    "Password tidak boleh kosong atau Confirm Password salah",
                    Toast.LENGTH_LONG
                ).show()
                binding.progressBar.visibility = View.GONE
            } else if (pass.contains("[0-9]".toRegex()) == false || pass.contains("[a-z]".toRegex()) == false || pass.contains(
                    "[A-Z]".toRegex()
                ) == false
            ) {
                Toast.makeText(
                    this,
                    "Password harus terdiri dari huruf besar, huruf kecil dan angka",
                    Toast.LENGTH_LONG
                ).show()
                binding.progressBar.visibility = View.GONE
            } else if (pass.length < 8) {
                Toast.makeText(
                    this,
                    "Password harus lebih dari 8 karakter",
                    Toast.LENGTH_LONG
                ).show()
                binding.progressBar.visibility = View.GONE
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        firebaseAuth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    firebaseAuth.currentUser?.updateProfile(profileUpdates)!!
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(
                                                    this,
                                                    "silahkan cek email anda untuk verif",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                val intent = Intent(this, Home::class.java)
                                                startActivity(intent)
                                                finish()
                                                binding.progressBar.visibility = View.GONE
                                            } else {
                                                android.widget.Toast.makeText(
                                                    this,
                                                    "${it.exception?.message}",
                                                    android.widget.Toast.LENGTH_LONG
                                                ).show()
                                                binding.progressBar.visibility =
                                                    android.view.View.GONE
                                            }

                                        }
                                } else {
                                    Toast.makeText(
                                        this,
                                        "${it.exception?.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    binding.progressBar.visibility = View.GONE
                                }
                            }
                    } else {
                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_LONG).show()
                        binding.progressBar.visibility = View.GONE
                    }

                }
            }


        }


    }
}