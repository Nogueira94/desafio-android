package com.picpay.desafio.android.service

import android.content.Context
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.PicPayApplication
import com.picpay.desafio.android.utils.NetworkState
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.android.inject
import org.koin.core.context.GlobalContext
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val cacheSize = (5 * 1024 * 1024).toLong()


private val myCache = Cache(PicPayApplication.applicationContext().cacheDir, cacheSize)

private val okHttpClient = OkHttpClient.Builder()
                            .cache(myCache)
                            .addInterceptor{ chain ->
                                var request = chain.request()
                                request = if (NetworkState.checkForInternet(PicPayApplication.applicationContext()))
                                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                                else
                                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                                chain.proceed(request)
                            }
                            .connectTimeout(7 ,TimeUnit.SECONDS)
                            .readTimeout(7,TimeUnit.SECONDS)
                            .writeTimeout(7,TimeUnit.SECONDS)
                            .build()

private val picpayRepository = Retrofit.Builder()
                                .baseUrl(BuildConfig.URL_BASE)
                                .client(okHttpClient)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()


val picpayService : PicPayService = picpayRepository.create(PicPayService::class.java)