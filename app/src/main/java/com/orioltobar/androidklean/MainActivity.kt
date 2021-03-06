package com.orioltobar.androidklean

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.orioltobar.androidklean.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override val layoutId: Int get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = NavHostFragment.findNavController(navFragment)
        navHostFragment.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.movieFragment, R.id.movieListFragment -> {
                    navBottom.visibility = View.GONE
                }
                else -> {
                    navBottom.visibility = View.VISIBLE
                }
            }
        }
        NavigationUI.setupWithNavController(navBottom, navHostFragment)
    }
}
