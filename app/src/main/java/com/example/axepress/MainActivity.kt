package com.example.axepress

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 2000
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        // This is the loading time of the splash screen
        supportActionBar?.hide()
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("App is loading..")
        progressDialog.setMessage("Please wait..")

        Handler().postDelayed({
            progressDialog.show()
                // This method will be executed once the timer is over
                // Start your app main activity

                startActivity(Intent(this,LogIn::class.java))

                // close this activity
                finish()

            }, SPLASH_TIME_OUT)
        progressDialog.dismiss()
    }
}