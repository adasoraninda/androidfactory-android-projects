package com.codetron.senyaepoxy.arch

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AttractionsViewModel::class.java) -> {
                AttractionsViewModel(context) as T
            }
            else -> throw IllegalArgumentException("Unknown class ViewModel: ${modelClass.simpleName}")
        }
    }
}