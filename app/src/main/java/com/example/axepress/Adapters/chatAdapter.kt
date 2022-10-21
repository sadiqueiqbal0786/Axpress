package com.example.axepress.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.axepress.Models.MessageModel
import com.example.axepress.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class chatAdapter(var messageModel: ArrayList<MessageModel>? , var context: Context?,
                  var SENDER_VIEW_TYPE:Int=1,var RECEIVER_VIEW_TYPE:Int=2):
    RecyclerView.Adapter <RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType==SENDER_VIEW_TYPE){
            var view:View=LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false)
            return SenderViewHolder(view)
        }
        else{
            var view:View=LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false)
            return ReceiverViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (messageModel?.get(position)?.getUId().equals(FirebaseAuth.getInstance().uid)){
            return SENDER_VIEW_TYPE
        }
        else{
            return RECEIVER_VIEW_TYPE
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var messageModel: MessageModel = messageModel!![position]
            if (holder.javaClass.kotlin == SenderViewHolder::class.java) {
                //((SenderViewHolder)holder).senderMsg.setText
                holder as SenderViewHolder
                holder.senderMsg.setText(messageModel.getMessage())
            } else {
                holder as ReceiverViewHolder
                holder.receiverMsg.setText(messageModel.getMessage())
            }
    }

    override fun getItemCount(): Int {
        return messageModel!!.size
    }

    public class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var receiverMsg=itemView.findViewById<TextView>(R.id.recever_text)
        var receiverTime=itemView.findViewById<TextView>(R.id.recever_time)


    }
    public class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var senderMsg=itemView.findViewById<TextView>(R.id.senderText)
        var senderTime=itemView.findViewById<TextView>(R.id.senderTime)
    }


}