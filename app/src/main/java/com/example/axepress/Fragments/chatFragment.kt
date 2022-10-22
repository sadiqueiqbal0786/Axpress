package com.example.axepress.Fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.axepress.Adapters.usersAdapter
import com.example.axepress.Models.Users
import com.example.axepress.R
import com.example.axepress.databinding.FragmentChatBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User

//param arg
lateinit var binding: FragmentChatBinding
var list:ArrayList<Users> = ArrayList()
lateinit var database: FirebaseDatabase
class chatFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentChatBinding.inflate(inflater, container, false)
        database=FirebaseDatabase.getInstance()
        val adapter= usersAdapter(list, context)
        binding.chatRecycllerView.adapter=adapter
        val layoutManager= LinearLayoutManager(context)
        binding.chatRecycllerView.layoutManager = layoutManager
        database.reference.child("Users").addValueEventListener(object:ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()

                for (datasnapshot:DataSnapshot in snapshot.children){
                    val user: Users? = datasnapshot.getValue( Users::class.java)
                    user?.setUserId(datasnapshot.key)//now get is working fine i will do set later
                    if (user != null) {
                        list.add(user)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO()
            }

        })

        return binding.root
    }

}