package com.example.clientgqlapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clientgqlapp.R
import com.example.clientgqlapp.UserQuery

/**
 * @author vkaja
 */
class HobbyAdapter : RecyclerView.Adapter<HobbyAdapter.HobbyViewHolder>() {

    private var hobbies : List<UserQuery.Hobby?>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbyViewHolder {
        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.hobby_row, parent, false)
        return HobbyViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: HobbyViewHolder, position: Int) {
        hobbies?.let {
            holder.bindHobby(it[position])
        }
    }

    override fun getItemCount(): Int {
        return hobbies?.size ?: 0
    }

    fun setHobbies(userHobbies : List<UserQuery.Hobby?>) {
        hobbies = userHobbies
        notifyDataSetChanged()
    }

    class HobbyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleView: TextView = itemView.findViewById(R.id.title_view)
        private val descriptionView: TextView = itemView.findViewById(R.id.description_view)

        fun bindHobby(hobby: UserQuery.Hobby?) {
            hobby?.let {
                titleView.text = it.title
                descriptionView.text = it.description
            }
        }
    }
}