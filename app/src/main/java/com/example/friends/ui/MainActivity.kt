package com.example.friends.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.friends.R
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.friends.repository.UserRepository
import com.example.githubrepo.ui.UserViewModelProviderFactory


class MainActivity : AppCompatActivity() {

    private var appBarConfiguration: AppBarConfiguration? = null
    private var navController: NavController? = null

    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userRepository = UserRepository()
        val viewModelProviderFactory = UserViewModelProviderFactory(application, userRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(UserViewModel::class.java)

        // Adding back button in appBar
        appBarConfiguration = AppBarConfiguration.Builder(R.id.userFragment).build()
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController!!, appBarConfiguration!!)
    }

    // Adding back button in appBar
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController!!, appBarConfiguration!!) || super.onSupportNavigateUp()
    }

}