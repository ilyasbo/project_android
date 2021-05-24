package com.example.tracking_app.models

import java.io.Serializable

data class Employee( var uuid:String? = "" ,var email: String ?="",var firstName: String ?="", var lastName: String ?="" ,  var numPhone: String ?="" , var numCIN:String ?="" ):Serializable
