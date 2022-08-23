package com.example.moviesuniverse.presentation.screens.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesuniverse.MainApplication
import com.example.moviesuniverse.R
import com.example.moviesuniverse.databinding.TabsFragmentBinding
import com.example.moviesuniverse.presentation.screens.Screens
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator

class TabsFragment : Fragment(R.layout.tabs_fragment) {

    private val navigator: Navigator by lazy {
        AppNavigator(requireActivity(), R.id.tabs_container, childFragmentManager)
    }

    private var _binding: TabsFragmentBinding? = null
    private val binding: TabsFragmentBinding
        get() = _binding!!


    override fun onResume() {
        super.onResume()
        MainApplication.INSTANCE.tabsNavigatorHolder.setNavigator(navigator)
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

        MainApplication.INSTANCE.tabsRouter.replaceScreen(Screens.main())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_tab -> {
                    MainApplication.INSTANCE.tabsRouter.replaceScreen(Screens.main())
                }
                R.id.movies_tab -> {
                    MainApplication.INSTANCE.tabsRouter.replaceScreen(Screens.movies())
                }
                R.id.staff_tab -> {
                    MainApplication.INSTANCE.tabsRouter.replaceScreen(Screens.staff())
                }
            }
            true
        }
    }

    override fun onPause() {
        super.onPause()
        MainApplication.INSTANCE.tabsNavigatorHolder.removeNavigator()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}