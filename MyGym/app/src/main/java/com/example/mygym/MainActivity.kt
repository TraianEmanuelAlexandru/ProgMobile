package com.example.mygym

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mygym.databinding.ActivityMainBinding
import com.example.mygym.databinding.AdminActivityMainBinding
import com.example.mygym.ui.home.HomeAdminFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var _binding: AdminActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var email = intent.getStringExtra("email")!!
        var inSettings = false

        if (adminsList.any { it.email == email }) {
            _binding = AdminActivityMainBinding.inflate(layoutInflater)
            setContentView(_binding.root)
            val navView: BottomNavigationView = _binding.navViewAdmin
            val navController = findNavController(R.id.nav_host_fragment_activity_main_admin)
            navView.selectedItemId = R.id.fragment_home_admin
            navView.setupWithNavController(navController)
            _binding.toolbarMenu.setOnMenuItemClickListener{
                    item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.popupMenuAnagrafica -> {
                        if (!inSettings) {
                            navController.navigate(R.id.fragment_impostazioni)
                            inSettings = true
                        }else{
                            navController.popBackStack()
                            inSettings = false
                        }
                    }
                    R.id.popupMenuDisconnect->{
                        deleteSharedPreferences(getString(R.string.profile_key))
                        finish()
                    }
                }
                true
            }

        } else  {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val navView: BottomNavigationView = binding.navView
            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            navView.selectedItemId = R.id.fragment_home
            navView.setupWithNavController(navController)
            binding.toolbarMenu.setOnMenuItemClickListener{
                    item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.popupMenuAnagrafica -> {
                        if (!inSettings){
                            navController.navigate(R.id.fragment_impostazioni_utente)
                            inSettings = true
                        }else{
                            navController.popBackStack()
                            inSettings = false
                        }
                    }
                    R.id.popupMenuDisconnect->{
                        deleteSharedPreferences(getString(R.string.profile_key))
                        finish()
                    }
                }
                true
            }


        }
    }




}