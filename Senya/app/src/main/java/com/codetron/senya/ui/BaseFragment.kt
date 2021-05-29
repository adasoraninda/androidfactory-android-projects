package com.codetron.senya.ui

import androidx.fragment.app.Fragment
import com.codetron.senya.arch.AttractionsViewModel

abstract class BaseFragment : Fragment() {

    protected val navController by lazy {
        (activity as MainActivity).navController
    }

    protected val viewModel: AttractionsViewModel
        get() = (activity as MainActivity).viewModel

}