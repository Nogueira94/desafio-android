package com.picpay.desafio.android.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.model.User
import com.squareup.picasso.Picasso
import com.squareup.picasso.Callback


class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListItemViewHolder>() {

    var users = emptyList<User>()
        set(value) {
            val result = DiffUtil.calculateDiff(
                UserListDiffCallback(
                    field,
                    value
                )
            )
            result.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val bind = ListItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserListItemViewHolder(bind)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    inner class UserListItemViewHolder (val bind: ListItemUserBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(user: User){
            bind.apply {
                name.text = user.name
                username.text = user.username
                progressBar.visibility = View.VISIBLE
                Picasso.get()
                    .load(user.img)
                    .error(R.drawable.ic_round_account_circle)
                    .into(bind.picture, object : Callback {
                        override fun onSuccess() {
                            bind.progressBar.visibility = View.GONE
                        }

                        override fun onError(e: Exception?) {
                            bind.progressBar.visibility = View.GONE
                        }
                    })
            }
        }
    }

}

class UserListDiffCallback(
    private val oldList: List<User>,
    private val newList: List<User>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].username.equals(newList[newItemPosition].username)
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }
}

