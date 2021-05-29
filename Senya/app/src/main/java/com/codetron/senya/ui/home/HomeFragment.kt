package com.codetron.senya.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.codetron.senya.R
import com.codetron.senya.databinding.FragmentHomeBinding
import com.codetron.senya.ui.BaseFragment

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val homeAdapter by lazy { HomeFragmentAdapter() }

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

        viewModel.getAttractions(R.raw.croatia)

        initListItem()

        subscribeToViewModel()
    }

    private fun initListItem() {
        binding?.lstItem?.adapter = homeAdapter.apply {
            setOnClickCallback { attractionId ->
                val navDirections = HomeFragmentDirections.navHomeToDetail(attractionId)
                navController.navigate(navDirections)
            }
        }
        binding?.lstItem?.setHasFixedSize(true)
        binding?.lstItem?.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                RecyclerView.VERTICAL
            )
        )
    }

    private fun subscribeToViewModel() {
        viewModel.attractions.observe(viewLifecycleOwner, { attractions ->
            homeAdapter.setData(attractions)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}