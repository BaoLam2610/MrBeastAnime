package com.lambao.mrbeast.presentation.ui.activity.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lambao.base.presentation.ui.activity.BaseVMActivity
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ActivityMainBinding

class MainActivity : BaseVMActivity<ActivityMainBinding, MainViewModel>() {

    override fun getLayoutResId() = R.layout.activity_main

    override fun getViewModelClass() = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onViewReady(savedInstanceState: Bundle?) {

    }

    override fun initObserve() {

    }

}