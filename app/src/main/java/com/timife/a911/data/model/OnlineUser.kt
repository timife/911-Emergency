package com.timife.a911.data.model

data class OnlineUser(
    var fullName: String="",
    var email : String="",
    var imageUrl: String="",
    var userId : String = ""
){
    constructor(): this(
        "","","",""
    )
}

