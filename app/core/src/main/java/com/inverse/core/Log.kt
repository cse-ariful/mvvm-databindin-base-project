package com.inverse.core

import android.util.Log

object Log {
    fun d(tag:String,message:String){
        if(BuildConfig.DEBUG){
            Log.d(tag, message)
        }
    }
    fun a(tag:String,message:String){
        Log.d(tag, message)

    }
}