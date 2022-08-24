package com.example.moviesuniverse.presentation.screens.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesuniverse.R
import com.example.moviesuniverse.databinding.TabsFragmentBinding
import com.example.moviesuniverse.di.NavigationModule
import com.example.moviesuniverse.presentation.screens.Screens
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class TabsFragment : Fragment(R.layout.tabs_fragment) {

    private val router: Router by inject(qualifier = named(NavigationModule.TABS_QUALIFIER_NAME))
    private val navigatorHolder: NavigatorHolder by inject(qualifier = named(NavigationModule.TABS_QUALIFIER_NAME))
    private val navigator: AppNavigator by inject(qualifier = named(NavigationModule.TABS_QUALIFIER_NAME)) {
        parametersOf(
            requireActivity(),
            R.id.tabs_container,
            childFragmentManager
        )
    }

    private var _binding: TabsFragmentBinding? = null
    private val binding: TabsFragmentBinding
        get() = _binding!!


    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TabsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        router.replaceScreen(Screens.main())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_tab -> {
                    router.replaceScreen(Screens.main())
                }
                R.id.movies_tab -> {
                    router.replaceScreen(Screens.movies())
                }
                R.id.staff_tab -> {
                    router.replaceScreen(Screens.staff())
                }
            }
            true
        }
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}