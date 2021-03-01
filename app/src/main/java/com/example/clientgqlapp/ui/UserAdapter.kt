package com.example.clientgqlapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.clientgqlapp.R
import com.example.clientgqlapp.UsersQuery

/**
 * @author vkaja
 */
class UserAdapter(private val context: Context, private val listener: (user: UsersQuery.User) -> Unit): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    var users: List<UsersQuery.User>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false)
        return UserViewHolder(itemView, context, listener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        users?.let {
            holder.bindView(it[position])
        }
    }

    override fun getItemCount(): Int {
        return users?.size ?: 0
    }


    class UserViewHolder(itemView: View, private val context: Context, private val listener: (user: UsersQuery.User) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private val userNameView: TextView = itemView.findViewById(R.id.title_view)
        private val ageView: TextView = itemView.findViewById(R.id.description_view)
        private val professionView: TextView = itemView.findViewById(R.id.profession_header)
        private val salaryView: TextView = itemView.findViewById(R.id.salary_header)
        private val cardView: CardView = itemView.findViewById(R.id.card_view)
        var TAG: String = UserViewHolder::class.java.simpleName

        fun bindView(user : UsersQuery.User) {
            userNameView.text = context.getString(R.string.username, user.name)
            ageView.text = context.getString(R.string.age, user.age)
            professionView.text = context.getString(R.string.profession, user.profession)
            salaryView.text = context.getString(R.string.salary, user.salary)

            cardView.setOnClickListener {
                listener.invoke(user)
            }
        }
    }
}