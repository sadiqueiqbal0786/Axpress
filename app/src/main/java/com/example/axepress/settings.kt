package com.example.axepress

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.axepress.Models.Users
import com.example.axepress.databinding.ActivitySettingsBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso

class settings : AppCompatActivity() {
    lateinit var binding: ActivitySettingsBinding
    lateinit var storage:FirebaseStorage
    lateinit var auth:FirebaseAuth
    lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        storage=FirebaseStorage.getInstance()
        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()

        binding.backButton.setOnClickListener{
            startActivity(Intent(this,MainActivity2::class.java))
        }
        database.reference.child("Users").child(FirebaseAuth.getInstance().uid!!)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                   val users:Users= snapshot.getValue(Users::class.java)!!
                    Picasso.get()
                        .load(users.getprofilepic())
                        .into(binding.profile)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        binding.addProfile.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,33)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data?.data!=null){
            val sFile:Uri = data.data!!
            binding.profile.setImageURI(sFile)

            val reference:StorageReference= storage.reference.child("Profile_Pictures")
                .child(FirebaseAuth.getInstance().uid!!)
            reference.putFile(sFile).addOnSuccessListener{ taskSnapshot->
                reference.downloadUrl.addOnSuccessListener {  uri->

                    database.reference.child("Users").child(FirebaseAuth.getInstance().uid!!)
                        .child("profilepic").setValue(uri.toString())
                }
                Toast.makeText(this,"Profile picture updated successfully",Toast.LENGTH_SHORT).show()
            }
        }
    }
}