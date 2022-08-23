package com.example.moviesuniverse.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moviesuniverse.MainApplication
import com.example.moviesuniverse.R
import com.example.moviesuniverse.presentation.screens.Screens
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val navigator by lazy { AppNavigator(this, R.id.fragment_container) }

    private val viewModel by viewModels<MainViewModel>()

    override fun onResume() {
        super.onResume()
        MainApplication.INSTANCE.globalNavigatorHolder.setNavigator(navigator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            viewModel.mainState.collect { state ->
                when (state) {
                    MainState.Loading -> MainApplication.INSTANCE.globalRouter.replaceScreen(Screens.splash())
                    MainState.Loaded -> MainApplication.INSTANCE.globalRouter.replaceScreen(Screens.tabs())
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        MainApplication.INSTANCE.globalNavigatorHolder.removeNavigator()
    }
}