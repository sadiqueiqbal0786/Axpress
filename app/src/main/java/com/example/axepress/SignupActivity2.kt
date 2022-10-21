package com.example.axepress

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.axepress.Models.Users
import com.example.axepress.databinding.ActivitySignup2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User

class SignupActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivitySignup2Binding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2)
        supportActionBar?.hide()
        binding = ActivitySignup2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()

        database=FirebaseDatabase.getInstance()

        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Account is creating..")
        progressDialog.setMessage("Please wait..")


        binding.signup.setOnClickListener { // Do some work here
            try {
                progressDialog.show()
                auth.createUserWithEmailAndPassword(
                    binding.email.text.toString(),
                    binding.password.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val username = binding.username.text.toString()
                            val email = binding.email.text.toString()
                            val password = binding.password.text.toString()
                            var user = Users(username, email, password)
                            val id = task.result.user?.uid
                            if (id != null) {
                                database.reference.child("Users").child(id).setValue(user)
                                    .addOnSuccessListener {
                                        binding.username.text.clear()
                                        binding.email.text.clear()
                                        binding.password.text.clear()

                                        startActivity(Intent(this,MainActivity2::class.java))

                                    }
                            }
                            progressDialog.dismiss()
                            Toast.makeText(
                                this@SignupActivity2,
                                "Account created successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            progressDialog.dismiss()
                            Toast.makeText(
                                this@SignupActivity2,
                                task.exception?.message,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }

            }
            catch (e:Exception){
                progressDialog.dismiss()
                Toast.makeText(this,e.message.toString(),Toast.LENGTH_SHORT).show()
            }


        }

        binding.accountView.setOnClickListener{
            startActivity(Intent(this,LogIn::class.java))
        }


    }
}