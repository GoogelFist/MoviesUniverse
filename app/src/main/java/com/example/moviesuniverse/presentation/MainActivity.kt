package com.example.moviesuniverse.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moviesuniverse.R
import com.example.moviesuniverse.di.GLOBAL_QUALIFIER_NAME
import com.example.moviesuniverse.presentation.screens.Screens
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private val router: Router by inject(qualifier = named(GLOBAL_QUALIFIER_NAME))
    private val navigatorHolder: NavigatorHolder by inject(qualifier = named(GLOBAL_QUALIFIER_NAME))
    private val navigator: AppNavigator by inject(qualifier = named(GLOBAL_QUALIFIER_NAME)) {
        parametersOf(
            this,
            R.id.fragment_container
        )
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            viewModel.mainState.collect { state ->
                when (state) {
                    MainState.Loading -> router.replaceScreen(Screens.splash())
                    MainState.Loaded -> router.replaceScreen(Screens.tabs())
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}