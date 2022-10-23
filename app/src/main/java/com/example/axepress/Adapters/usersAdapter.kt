package com.example.axepress.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.axepress.Adapters.usersAdapter.ViewHolder
import com.example.axepress.ChatDetailedActivity
import com.example.axepress.Models.Users
import com.example.axepress.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class usersAdapter(var list:ArrayList<Users>,var context: Context?):
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view:View?=LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false)
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var user:Users= list.get(position)
        Picasso.get().load(user.getprofilepic()).placeholder(R.mipmap.user).into(holder.image)
        holder.userName?.text = user.getUsername()

        FirebaseDatabase.getInstance().reference.child("Chats")
            .child(FirebaseAuth.getInstance().uid+user.getUserId())
            .orderByChild("timeStamp")
            .limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                  if (snapshot.hasChildren()){
                      for (snapshot1:DataSnapshot in snapshot.children){
                          holder.lastMessage?.text=snapshot1.child("message").value.toString()
                      }
                  }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        holder.itemView.setOnClickListener{
            val intent= Intent(context,ChatDetailedActivity::class.java)
            intent.putExtra("userId",user.getUserId())
            intent.putExtra("profilePic",user.getprofilepic())
            intent.putExtra("userName",user.getUsername())
            context?.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }



    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image:ImageView?=itemView.findViewById(R.id.profile_image)
        var userName:TextView?=itemView.findViewById(R.id.usrName)
        var lastMessage:TextView?=itemView.findViewById(R.id.lastmsg)

    }

}