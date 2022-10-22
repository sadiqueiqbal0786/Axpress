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

class chatAdapter(var messageModel: ArrayList<MessageModel>? , var context: Context?,
                  var SENDER_VIEW_TYPE:Int=1,var RECEIVER_VIEW_TYPE:Int=2):
    RecyclerView.Adapter <RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType==SENDER_VIEW_TYPE){
            val view:View=LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false)
            SenderViewHolder(view)
        } else{
            val view:View=LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false)
            ReceiverViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messageModel?.get(position)?.getUId().equals(FirebaseAuth.getInstance().uid)){
            SENDER_VIEW_TYPE
        } else{
            RECEIVER_VIEW_TYPE
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val messageModel: MessageModel = messageModel!![position]
        when (holder) {
            is SenderViewHolder -> holder.senderMsg.text=messageModel.getMessage()
            is ReceiverViewHolder -> holder.receiverMsg.text=messageModel.getMessage()
            else -> throw IllegalArgumentException()
        }
          /*  if (holder.javaClass.kotlin == SenderViewHolder::class.java) {
                //((SenderViewHolder)holder).senderMsg.setText
                holder as SenderViewHolder
                holder.senderMsg.text = messageModel.getMessage()
            } else {
                holder as ReceiverViewHolder
                holder.receiverMsg.text = messageModel.getMessage()
            }*/
    }

    override fun getItemCount(): Int {
        return messageModel!!.size
    }

   class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var receiverMsg=itemView.findViewById<TextView>(R.id.recever_text)
        var receiverTime=itemView.findViewById<TextView>(R.id.recever_time)


    }
class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var senderMsg=itemView.findViewById<TextView>(R.id.senderText)
        var senderTime=itemView.findViewById<TextView>(R.id.senderTime)

    }


}