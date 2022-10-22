package com.example.axepress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.axepress.Adapters.chatAdapter
import com.example.axepress.Models.MessageModel
import com.example.axepress.databinding.ActivityGroupChatBinding
import com.example.axepress.databinding.ActivitySignup2Binding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList

class GroupChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityGroupChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.backbutton.setOnClickListener{
            startActivity(Intent(this,MainActivity2::class.java))
        }

        val database=FirebaseDatabase.getInstance()
        val senderId:String = FirebaseAuth.getInstance().uid.toString()
        binding.userName.text="Group Chat"

        val messageModel= ArrayList<MessageModel>()
        val adapter =chatAdapter(messageModel,this)
        binding.chatRecyclerView.adapter=adapter

        val layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.layoutManager=layoutManager

        binding.send.setOnClickListener{
            val message:String = binding.messagebox.text.toString()
            val model = MessageModel(senderId,message)
            model.setTimeStamp(Date().time)
            binding.messagebox.text.clear()

            database.reference.child("Group Chat")
                .push()
                .setValue(model).addOnSuccessListener(OnSuccessListener {
                })

        }

        }
    }