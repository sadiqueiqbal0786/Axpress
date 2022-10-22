package com.example.axepress.Models

class MessageModel(var uId:String? =null, var message:String?=null ,var timeStamp:Long?=null) {

   constructor(uId: String?,message: String?) : this() {
       this.uId=uId
       this.message=message
    }
    class MessageModel constructor(){

    }
    @JvmName("getUId1")
    public fun getUId():String{
        return uId.toString()
    }
    @JvmName("setUId1")
    public fun setUId(uId: String?){
        this.uId=uId
    }
    @JvmName("getMessage1")
    public fun getMessage():String{
        return message.toString()
    }
    @JvmName("setMessage1")
    public fun setMessage(message: String?){
        this.message=message
    }
    @JvmName("getTimeStamp1")
    public fun getTimeStamp():Long{
        return timeStamp!!.toLong()
    }
    @JvmName("setTimeStamp1")
    public fun setTimeStamp(timeStamp: Long?){
        this.timeStamp=timeStamp
    }


}
