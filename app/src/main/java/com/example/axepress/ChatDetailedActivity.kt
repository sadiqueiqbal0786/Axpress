package com.example.axepress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.axepress.Adapters.chatAdapter
import com.example.axepress.Models.MessageModel
import com.example.axepress.databinding.ActivityChatDetailedBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
        var sh=findViewById<TextView>(R.id.senderText)
        val senderId:String= auth.uid.toString()
        database=FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()
        val recieverId:String= intent.getStringExtra("userId").toString()
        val userName:String=intent.getStringExtra("userName").toString()
        val profilePic:String=intent.getStringExtra("profilePic").toString()
        binding.userName.text = userName
        Picasso.get().load(profilePic).placeholder(R.mipmap.user).into(binding.profileImage)

        binding.backbutton.setOnClickListener{
            startActivity(Intent(this,MainActivity2::class.java))
        }
        val messageModel:ArrayList<MessageModel> = ArrayList()
        val chatAdapters=chatAdapter(messageModel,this,recieverId)
        binding.chatRecyclerView.adapter = chatAdapters

        val layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.layoutManager=layoutManager

        val senderRoom:String=senderId+recieverId
        val receiverRoom:String=recieverId+senderId

        database.reference.child("Chats").child(senderRoom)
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageModel.clear()
                   for (snapshot1:DataSnapshot in snapshot.children){
                     val model = snapshot1.getValue(MessageModel::class.java)
                       model?.setMessageId(snapshot1.key)
                       if (model != null) {
                           messageModel.add(model)
                       }
                       // i will use notify data changed later
                   }
                    chatAdapters.notifyDataSetChanged()
                    binding.chatRecyclerView.scrollToPosition(messageModel.size-1)
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
            val message:String=binding.messagebox.text.toString()
            val model= MessageModel(senderId,message)
            model.setTimeStamp(Date().time)
            binding.messagebox.text.clear()

            database.reference.child("Chats").child(senderRoom).push()
                .setValue(model).addOnSuccessListener {
                    database.reference.child("Chats").child(receiverRoom).push()
                        .setValue(model).addOnSuccessListener {

                        }
                }

        }}
        binding.chatRecyclerView.scrollToPosition(messageModel.size-1)


    }
}