package com.lambao.mrbeast.presentation.ui.activity.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lambao.base.extension.findNavController
import com.lambao.base.extension.setupWithNavController2
import com.lambao.base.presentation.ui.activity.BaseVMActivity
import com.lambao.base.utils.log
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseVMActivity<ActivityMainBinding, MainViewModel>() {

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun getLayoutResId() = R.layout.activity_main

    override fun getViewModelClass() = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupNavigation()
    }

    override fun initObserve() {

    }

    private fun setupNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            log("Current destination: ${destination.label}")
        }
        binding.bottomMenu.setupWithNavController2(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}