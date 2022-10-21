package com.example.axepress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.axepress.Adapters.fragmentAdapters
import com.example.axepress.databinding.ActivityLogInBinding
import com.example.axepress.databinding.ActivityMain2Binding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    lateinit var authe: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        authe=FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)

        binding.viewPager.adapter = fragmentAdapters(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var inflator:MenuInflater=menuInflater
        inflator.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.setting->{
            }
            R.id.logout->{
              authe.signOut()
                mGoogleSignInClient.signOut()
                startActivity(Intent(this,LogIn::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}