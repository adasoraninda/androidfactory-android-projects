package com.codetron.senyaepoxy.ui.detail

import androidx.lifecycle.ViewModel

class DetailViewModel:ViewModel() {

    private var _stateGrid: Boolean = true
    val stateGrid get() = _stateGrid

    fun toggleStateGrid() {
        _stateGrid = _stateGrid.not()
    }

}