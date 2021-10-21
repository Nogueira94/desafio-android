package com.picpay.desafio.android.view.view_model

import android.util.Log
import androidx.lifecycle.*
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.service.PicPayRepoImpl
import com.picpay.desafio.android.service.PicPayService
import com.picpay.desafio.android.service.picpayService
import com.picpay.desafio.android.utils.ApiResult
import com.picpay.desafio.android.utils.ApiStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel() : ViewModel() {

    private val repository = PicPayRepoImpl(picpayService)

    private val _users = MutableLiveData<ApiResult<List<User>>>()
    val users: LiveData<ApiResult<List<User>>> = _users

    fun loadData(){
        viewModelScope.launch {
            repository.loadData().collect {
                _users.value = it
            }
        }
    }

}
