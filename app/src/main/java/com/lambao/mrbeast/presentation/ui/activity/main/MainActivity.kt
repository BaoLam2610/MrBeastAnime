package com.lambao.mrbeast.presentation.ui.activity.main

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.lambao.base.extension.findNavController
import com.lambao.base.extension.setupWithNavController2
import com.lambao.base.presentation.ui.activity.BaseVMActivity
import com.lambao.base.utils.log
import com.lambao.mrbeast.presentation.service.CombineService
import com.lambao.mrbeast.presentation.service.StartedService
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseVMActivity<ActivityMainBinding, MainViewModel>() {

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as CombineService.LocalBinder
            val combineService = binder.getService()
            log("LAMNB", "Service connected")
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            log("LAMNB", "Service disconnected")
        }
    }

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
//        startService(Intent(this.applicationContext, StartedService::class.java))
        permissionHandler.requestPermission(android.Manifest.permission.POST_NOTIFICATIONS)

//        val intent = Intent(this.applicationContext, CombineService::class.java)
//        startService(intent)
//        bindService(intent, serviceConnection, BIND_AUTO_CREATE)


    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onStop() {
        super.onStop()
//        val intent = Intent(this.applicationContext, CombineService::class.java)
//        stopService(intent)
//        unbindService(serviceConnection)
    }

    override fun initObserve() {
        binding.viewModel = viewModel
        navController.addOnDestinationChangedListener { _, destination, _ ->
            log("Current destination label: ${destination.label}")
            log("Current destination id: ${destination.id}")
            viewModel.updateShowBottomNavByFragmentId(destination.id)
        }
    }

    private fun setupNavigation() {
        binding.bottomMenu.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = 0)
            insets
        }
        binding.bottomMenu.setupWithNavController2(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}