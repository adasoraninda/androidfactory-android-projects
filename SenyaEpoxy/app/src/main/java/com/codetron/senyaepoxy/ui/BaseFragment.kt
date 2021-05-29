package com.codetron.senyaepoxy.ui

import androidx.fragment.app.Fragment
import com.codetron.senyaepoxy.arch.AttractionsViewModel

abstract class BaseFragment : Fragment() {

    protected val navController by lazy {
        (activity as MainActivity).navController
    }

    protected val activityViewModel: AttractionsViewModel
        get() = (activity as MainActivity).viewModel

}