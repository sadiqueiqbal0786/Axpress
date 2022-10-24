package com.example.axepress.Models

class Users(var username:String?=null,
            var mail:String?=null,
            var password:String?=null,
            var profilepic:String?=null,
            var userId:String?=null,
            var lastMessage:String?=null,
            var status:String?=null
){
    class Users constructor(username: String,mail: String,password:String) {

   }
    constructor(userId : String) : this() {
        this.userId = userId
    }
    constructor():this(null){}
    @JvmName("getUsername1")
    public fun getUsername(): String {
        return username.toString()
    }
    @JvmName("setUsername1")
    public fun setUsername(username: String){
        this.username=username

    }
    @JvmName("getMail1")
    public fun getMail():String{
        return mail.toString()
    }
    @JvmName("setMail1")
    public fun setMail(mail: String){
        this.mail=mail
    }
    @JvmName("getpassword1")
    public fun getpassword():String{
        return password.toString()
    }
    @JvmName("setpassword1")
    public fun setpassword(password: String){
        this.password=password
    }
    @JvmName("getprofilepic1")
    public fun getprofilepic():String{
        return profilepic.toString()

    }
    @JvmName("setprofilepic1")
    public fun setprofilepic(profilepic: String){
        this.profilepic=profilepic
    }
    @JvmName("getUserId1")
    public fun getUserId(key: String?):String{
        return userId.toString()
    }
    @JvmName("setUserId1")
    public fun setUserId(userId: String){
        this.userId=userId
    }
    @JvmName("getlastMessage1")
    public fun getlastMessage():String{
        return lastMessage.toString()
    }
    @JvmName("setlastMessage1")
    public fun setlastMessage(lastMessage: String){
        this.lastMessage=lastMessage

    }

    @JvmName("getUserId2")
    public fun getUserId(): String {
        return userId.toString()
    }

    @JvmName("setUserId2")
    fun setUserId(userId: String?) {
        this.userId=userId
    }
    @JvmName("getStatus1")
    public fun getStatus():String{
        return status.toString()
    }
    @JvmName("setStatus1")
    public fun setStatus(status:String?){
        this.status=status
    }

}

