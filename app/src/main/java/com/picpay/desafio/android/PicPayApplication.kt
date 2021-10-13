package com.picpay.desafio.android

import android.app.Application
import android.content.Context

class PicPayApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: PicPayApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

}