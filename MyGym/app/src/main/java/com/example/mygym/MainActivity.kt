package com.example.mygym

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mygym.databinding.ActivityMainBinding
import com.example.mygym.databinding.AdminActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var _binding: AdminActivityMainBinding

    //lateinit var esercizioRoomDatabase: EsercizioRoomDatabase
    //lateinit var esercizioDao: EsercizioDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var email = intent.getStringExtra("email")

        if (adminsList.any { it.email == email }) {
            _binding = AdminActivityMainBinding.inflate(layoutInflater)
            setContentView(_binding.root)
            val navView: BottomNavigationView = _binding.navViewAdmin
            val navController = findNavController(R.id.nav_host_fragment_activity_main_admin)//R.id.nav_host_fragment_activity_main_admin)
            /*val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.fragment_home_admin, R.id.navigation_dashboard, R.id.navigation_notifications
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)*/
            navView.selectedItemId = R.id.fragment_home_admin
            navView.setupWithNavController(navController)

        } else  {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val navView: BottomNavigationView = binding.navView
            val navController = findNavController(R.id.nav_host_fragment_activity_main)

            /*val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.fragment_home, R.id.navigation_dashboard, R.id.navigation_notifications
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)*/
            navView.selectedItemId = R.id.fragment_home
            navView.setupWithNavController(navController)
        }
    }




}