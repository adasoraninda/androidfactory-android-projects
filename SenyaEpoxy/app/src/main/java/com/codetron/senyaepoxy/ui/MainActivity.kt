package com.codetron.senyaepoxy.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.codetron.senyaepoxy.R
import com.codetron.senyaepoxy.arch.AttractionsViewModel
import com.codetron.senyaepoxy.arch.ViewModelFactory
import com.codetron.senyaepoxy.data.Attraction
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    val viewModel: AttractionsViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nhf_main) as NavHostFragment

        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        viewModel.eventsFlow.onEach { event ->
            when (event) {
                AttractionsViewModel.Event.IntentLocation -> {
                    showLocationAttraction(viewModel.attraction.value)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun showLocationAttraction(attraction: Attraction?) {
        val uri =
            Uri.parse("geo:${attraction?.location?.latitude},${attraction?.location?.longitude}?z=9&q=${attraction?.title}")
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}