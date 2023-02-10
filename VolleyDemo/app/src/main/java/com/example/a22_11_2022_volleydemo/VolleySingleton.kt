package com.example.a22_11_2022_volleydemo

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object VolleySingleton {
    var volleyRequestQueue : RequestQueue? = null
    fun initializeRequestQueue(context: Context){
        volleyRequestQueue = Volley.newRequestQueue(context)
    }
}