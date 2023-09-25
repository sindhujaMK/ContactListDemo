package com.example.contactlistdemo

import android.graphics.Bitmap
import java.io.Serializable


class ItemsViewModel(
    //var image: Bitmap? = null,
    var name: String? = null,
    var phoneNumber: ArrayList<String>? = null,
    var email : ArrayList<String>? = null,
    var birthDate : String? = null

) : Serializable