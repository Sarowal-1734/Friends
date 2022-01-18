package com.example.friends.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.friends.adapters.UserAdapter
import com.example.friends.databinding.FragmentUserBinding
import com.example.friends.ui.MainActivity
import com.example.friends.ui.UserViewModel
import com.example.friends.util.Constants
import com.example.friends.util.Resource

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    private lateinit var viewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false)

        viewModel = (activity as MainActivity).viewModel

        // Setup recycler adapter
        setupRecyclerView()

        // Swipe to refresh if data changed
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            // Fetch data from api
            viewModel.usersPageNumber = 1
            viewModel.usersPageResponse = null
            viewModel.getUsers()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        // on recycler item click to open user_details UI
        userAdapter.setOnItemClickListener {
            val direction =
                UserFragmentDirections.actionUserFragmentToUserDetailsFragment(it)
            this.findNavController().navigate(direction)
        }

        viewModel.users.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { userResponse ->
                        userAdapter.differ.submitList(userResponse.results.toList())
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showMainProgressBar()
                }
                is Resource.Paginating -> {
                    showPaginationProgressBar()
                }
            }
        })

        return binding.root
    }

    // hide progressBar
    private fun hideProgressBar() {
        binding.initialProgressBar.visibility = View.GONE
        binding.paginationProgressBar.visibility = View.GONE

    }

    // show initial progressBar
    private fun showMainProgressBar() {
        binding.initialProgressBar.visibility = View.VISIBLE
    }

    // show pagination progressBar
    private fun showPaginationProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    // Pagination
    var isLoading = false
    var isLastPage = false
    var isScrolling = false
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isNotLeadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLeadingAndNotLastPage && isAtLastItem
                    && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getUsers()
                isScrolling = false
            }
        }
    }

    // Setup recyclerView
    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            userAdapter = UserAdapter()
            adapter = userAdapter
            layoutManager = GridLayoutManager(activity?.applicationContext, 2)
            addOnScrollListener(scrollListener)
        }
    }
}