package com.picpay.desafio.android.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.utils.ApiStatus
import com.picpay.desafio.android.view.adapter.UserListAdapter
import com.picpay.desafio.android.view.view_model.MainViewModel
import kotlinx.coroutines.Job

class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModels()
    lateinit var bind: ActivityMainBinding
    private val adapterListUser = UserListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        startView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestUsers()
    }

    private fun startView() {
        bind.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterListUser
            addItemDecoration(DividerItemDecoration(applicationContext,DividerItemDecoration.VERTICAL))
        }
        loadData()
    }

    private fun loadData(){
        viewModel.apiResponse.observe(this) { result ->
            when(result.status){
                ApiStatus.LOADING ->
                    showProgress()
                ApiStatus.ERROR ->
                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                ApiStatus.SUCCESS -> {
                    adapterListUser.users = result.data as List<User>
                    hideProgress()
                }
            }
        }
    }


    private fun showProgress(){
        bind.userListProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        bind.userListProgressBar.visibility = View.GONE
    }

}
