package com.picpay.desafio.android.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.utils.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.lang.Exception

class PicPayRepoImpl(private val api: PicPayService) {

    fun loadData() : Flow<ApiResult<List<User>>> {
        return  flow {
            emit(ApiResult.Loading(null,true))
            try {
                val response = api.getUsers()
                if (response.isSuccessful) {
                    emit(ApiResult.Success(response.body()))
                } else {
                    val errorMsg = response.errorBody().toString()
                    response.errorBody()?.close()
                    emit(ApiResult.Error(errorMsg))
                }
            } catch (e: Exception) {
                emit(ApiResult.Error(e.toString()))
            }
        }
    }

}