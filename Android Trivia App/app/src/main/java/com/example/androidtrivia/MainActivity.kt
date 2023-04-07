package com.example.androidtrivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.androidtrivia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        drawerLayout = binding.drawerLayout
        navController = this.findNavController(R.id.myNavHostFragment)


        navController.addOnDestinationChangedListener{ controller , destination , arguments ->
            if (destination.id == controller.graph.startDestinationId) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)

        appBarConfiguration = AppBarConfiguration(navGraph = navController.graph, drawerLayout = drawerLayout)
        NavigationUI.setupWithNavController(binding.navView,navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController,drawerLayout) || super.onSupportNavigateUp()
    }
}