package com.example.axepress.Adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.axepress.Fragments.database
import com.example.axepress.Models.MessageModel
import com.example.axepress.R
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue.TIMESTAMP
import java.text.Format
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class chatAdapter(var messageModel: ArrayList<MessageModel>? , var context: Context?,var recId:String?,
                  var SENDER_VIEW_TYPE:Int=1,var RECEIVER_VIEW_TYPE:Int=2,):
    RecyclerView.Adapter <RecyclerView.ViewHolder>(){
    class chatAdapter constructor(messageModel: ArrayList<MessageModel>?, context: Context?, recId: String?){

    }

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val messageModel: MessageModel = messageModel!![position]
        val ts= messageModel.getTimeStamp()
        val simpleDateFormat:SimpleDateFormat= SimpleDateFormat("dd-MM-yyyy hh:mm a")
        val time:String=simpleDateFormat.format(ts)


        holder.itemView.setOnLongClickListener{
            AlertDialog.Builder(context).setTitle("Delete")
                .setMessage("Are you sure to delete this message?")
                .setPositiveButton("yes",DialogInterface.OnClickListener{
                        dialog, which ->
                    database= FirebaseDatabase.getInstance()
                    val sender=FirebaseAuth.getInstance().uid+recId
                    database.reference.child("Chats").child(sender)
                        .child(messageModel.getMessageId()).setValue(null)




                }).setNegativeButton("No",DialogInterface.OnClickListener{
                        dialog, which ->dialog.dismiss()

                }).show()


            return@setOnLongClickListener true
        }
        when (holder) {
            is SenderViewHolder -> {holder.senderMsg.text=messageModel.getMessage()
                holder.senderTime.text=time
            }

            is ReceiverViewHolder ->{ holder.receiverMsg.text=messageModel.getMessage()

               holder.receiverTime.text=time
            }
            else -> throw IllegalArgumentException()
        }

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