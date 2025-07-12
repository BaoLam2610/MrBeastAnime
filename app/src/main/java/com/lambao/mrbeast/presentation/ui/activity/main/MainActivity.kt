package com.lambao.mrbeast.presentation.ui.activity.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.lambao.base.extension.findNavController
import com.lambao.base.extension.setupWithNavController2
import com.lambao.base.presentation.ui.activity.BaseVMActivity
import com.lambao.base.utils.log
import com.lambao.mrbeast.presentation.service.CustomWorker
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

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
//        val workRequest = OneTimeWorkRequestBuilder<CustomWorker>().apply {
//            setInputData(
//                workDataOf(
//                    CustomWorker.ARG_NUMBER to 1
//                )
//            )
//            setConstraints(
//                Constraints(NetworkType.CONNECTED)
//            )
//
//        }. build()

        val workRequest = PeriodicWorkRequestBuilder<CustomWorker>(
            repeatInterval = 16,
            repeatIntervalTimeUnit = TimeUnit.MINUTES,
            flexTimeInterval = 5,
            flexTimeIntervalUnit = TimeUnit.MINUTES
        ).apply {
            setInputData(
                workDataOf(
                    CustomWorker.ARG_NUMBER to 1
                )
            )
        }.build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)

        val workInfo = WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdLiveData(workRequest.id)
        workInfo.observe(this) {
            it?.outputData?.toString()?.let {
                log("LAMNB", it)
            }
        }
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