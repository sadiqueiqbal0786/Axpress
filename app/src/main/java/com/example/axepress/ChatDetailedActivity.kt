package com.example.axepress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.axepress.Adapters.chatAdapter
import com.example.axepress.Models.MessageModel
import com.example.axepress.databinding.ActivityChatDetailedBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class ChatDetailedActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatDetailedBinding
    lateinit var database: FirebaseDatabase
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityChatDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        auth=FirebaseAuth.getInstance()
        var senderId:String= auth.uid.toString()
        database=FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()
        var recieverId:String= intent.getStringExtra("userId").toString()
        var userName:String=intent.getStringExtra("userName").toString()
        var profilePic:String=intent.getStringExtra("profilePic").toString()
        binding.userName.text = userName
        Picasso.get().load(profilePic).placeholder(R.mipmap.user).into(binding.profileImage)

        binding.backbutton.setOnClickListener{
            startActivity(Intent(this,MainActivity2::class.java))
        }
        var messageModel:ArrayList<MessageModel> = ArrayList()
        var chatAdapters:chatAdapter=chatAdapter(messageModel,this)
        binding.chatRecyclerView.adapter = chatAdapters

        var layoutManager:LinearLayoutManager= LinearLayoutManager(this)
        binding.chatRecyclerView.layoutManager=layoutManager
        var senderRoom:String=senderId+recieverId
        var receiverRoom:String=recieverId+senderId
        binding.send.setOnClickListener{
            var message:String=binding.messagebox.text.toString()
            var model:MessageModel= MessageModel(senderId,message)
            model.setTimeStamp(Date().time)
            binding.messagebox.text.clear()

            database.reference.child("Chats").child(senderRoom).push()
                .setValue(model).addOnSuccessListener {
                    database.reference.child("Chats").child(receiverRoom).push()
                        .setValue(model).addOnSuccessListener {

                        }
                }

        }


    }
}