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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class GroupChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityGroupChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recieverId:String= intent.getStringExtra("userId").toString()

        supportActionBar?.hide()

        binding.backbutton.setOnClickListener{
            startActivity(Intent(this,MainActivity2::class.java))
        }

        val database=FirebaseDatabase.getInstance()
        val senderId:String = FirebaseAuth.getInstance().uid.toString()
        binding.userName.text="Group Chat"

        val messageModel= ArrayList<MessageModel>()
        val adapter =chatAdapter(messageModel,this,recieverId)
        binding.chatRecyclerView.adapter=adapter

        val layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.layoutManager=layoutManager

        database.reference.child("Group Chat").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageModel.clear()
                for (dataSnapshot : DataSnapshot in snapshot.children){
                    val model=dataSnapshot.getValue(MessageModel::class.java)
                    model?.setMessageId(dataSnapshot.key)
                    if (model != null) {
                        messageModel.add(model)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



        binding.send.setOnClickListener{
            if (binding.messagebox.text.toString().isEmpty()){
                binding.messagebox.error="Please enter message"
            }
            else{
            val message:String = binding.messagebox.text.toString()
            val model = MessageModel(senderId,message)
            model.setTimeStamp(Date().time)
            binding.messagebox.text.clear()

            database.reference.child("Group Chat")
                .push()
                .setValue(model).addOnSuccessListener(OnSuccessListener {
                })

        }}

        }
    }