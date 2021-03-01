package com.example.clientgqlapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientgqlapp.R
import com.example.clientgqlapp.UsersQuery
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val viewModel : FirstFragmentViewModel by lazy {
        ViewModelProvider(this).get(FirstFragmentViewModel::class.java)
    }
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserAdapter(requireContext()) {
            openDetailsView(it)
        }
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        addObserver()
        setUpProgress()
    }

    private fun setUpProgress() {
        container.visibility = View.INVISIBLE
        progress.visibility = View.VISIBLE
    }

    private fun addObserver() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            users?.let {
                progress.visibility = View.GONE
                container.visibility = View.VISIBLE
                adapter.users = it
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun openDetailsView(user: UsersQuery.User) {
        val bundle = bundleOf(ID_ARG to user.id)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }
}