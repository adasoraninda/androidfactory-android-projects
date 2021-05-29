package com.codetron.senyaepoxy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.codetron.senyaepoxy.R
import com.codetron.senyaepoxy.databinding.FragmentHomeBinding
import com.codetron.senyaepoxy.ui.BaseFragment

class HomeFragment : BaseFragment(), (String) -> Unit {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val epoxyController by lazy { HomeFragmentController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityViewModel.getAttractions(R.raw.croatia)

        initListItem()

        subscribeToViewModel()
    }

    private fun initListItem() {
        binding?.lstItem?.setController(
            epoxyController.apply { setOnClickCallback(this@HomeFragment) }
        )

        binding?.lstItem?.setHasFixedSize(true)
        binding?.lstItem?.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                RecyclerView.VERTICAL
            )
        )

    }

    private fun subscribeToViewModel() {
        epoxyController.isLoading = true
        activityViewModel.attractions.observe(viewLifecycleOwner, { attractions ->
            epoxyController.attractions = attractions
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun invoke(id: String) {
        val navDirections = HomeFragmentDirections.navHomeToDetail(id)
        navController.navigate(navDirections)
    }

}