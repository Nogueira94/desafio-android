package com.picpay.desafio.android.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val picpayRepository = Retrofit.Builder()
    .baseUrl("https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val picpayService : PicPayService = picpayRepository.create(PicPayService::class.java)