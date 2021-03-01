package com.example.clientgqlapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientgqlapp.R
import com.example.clientgqlapp.UserQuery
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 *
 * view.findViewById<Button>(R.id.button_second).setOnClickListener {
 * findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
 * }
 */
const val ID_ARG = "userId"

class SecondFragment : Fragment() {

    val viewModel: SecondFragmentViewModel by lazy {
        ViewModelProvider(this).get(SecondFragmentViewModel::class.java)
    }

    private lateinit var hobbyAdapter: HobbyAdapter
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString(ID_ARG)
        id?.let {
            viewModel.userId = it
        }
        hobbyAdapter = HobbyAdapter()
        postAdapter = PostAdapter()
        recycler_hobby_view.adapter = hobbyAdapter
        recycler_hobby_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_posts_view.adapter = postAdapter
        recycler_posts_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_hobby_view.adapter
        addObserver()
    }

    private fun addObserver() {
        viewModel.userData.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(user : UserQuery.User?) {
        user?.let {
            user_name.text = it.name
            age_value.text = getString(R.string.age, it.age)
            profession_value.text = getString(R.string.profession, it.profession)
            salary_value.text = getString(R.string.salary, it.salary)
            it.hobbies?.let { hobbies ->
                hobbyAdapter.setHobbies(hobbies.filterNotNull())
            }
            it.posts?.let { posts ->
                postAdapter.setPosts(posts.filterNotNull())
            }
        }
    }
}