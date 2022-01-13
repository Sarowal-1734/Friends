package com.example.friends.ui.fragments.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.friends.adapters.UserAdapter
import com.example.friends.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false)

        // Fetch data from api
        val userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Setup recycler adapter
        setupRecyclerView()

        // Fetch data from api
        fetchData(userViewModel)

        // Swipe to refresh if data changed
        binding.swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            // Fetch data from api
            binding.swipeRefreshLayout.isRefreshing = true
            fetchData(userViewModel)
            binding.swipeRefreshLayout.isRefreshing = false
        })

        // Show/Hide progressBar
        userViewModel.isLoading().observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = it
        })

        return binding.root
    }

    // Fetch data from api
    private fun fetchData(userViewModel: UserViewModel) {
        userViewModel.user.observe(viewLifecycleOwner, Observer {
            // passing data to the adapter
            userAdapter.results = it.results
        })
    }

    // Setup recyclerView
    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            userAdapter = UserAdapter()
            adapter = userAdapter
            layoutManager = GridLayoutManager(activity?.applicationContext, 2)
        }
    }
}