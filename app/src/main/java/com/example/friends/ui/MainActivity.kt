package com.example.friends.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.friends.R
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI


class MainActivity : AppCompatActivity() {

    private var appBarConfiguration: AppBarConfiguration? = null
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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