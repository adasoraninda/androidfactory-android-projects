package com.codetron.senyaepoxy.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.codetron.senyaepoxy.R
import com.codetron.senyaepoxy.data.Attraction
import com.codetron.senyaepoxy.databinding.FragmentDetailBinding
import com.codetron.senyaepoxy.ui.BaseFragment

class DetailFragment : BaseFragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    private val args: DetailFragmentArgs by navArgs()

    private val headerEpoxyController: HeaderEpoxyController by lazy {
        HeaderEpoxyController()
    }

    private val contentEpoxyController: ContentEpoxyController by lazy {
        ContentEpoxyController()
    }

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityViewModel.getAttractionDetail(args.id)

        activityViewModel.attraction.observe(viewLifecycleOwner, {
            initDetailData(it)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_location -> {
                activityViewModel.intentEventLocation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initDetailData(attraction: Attraction?) {
        with(binding) {
            this?.txtTitle?.text = attraction?.title
            this?.lstImages?.setController(headerEpoxyController.apply {
                attraction?.imageUrls?.let { imageUrls = it }
            })
            this?.lstImages?.let {
                LinearSnapHelper().attachToRecyclerView(it)
                this.sclImageIndicator.attachToRecyclerView(it)
            }

            this?.lstContent?.setController(contentEpoxyController.apply {

                setOnToggleCallback {
                    detailViewModel.toggleStateGrid()
                    contentEpoxyController.setGridState(detailViewModel.stateGrid)
                    requestModelBuild()

                    if (detailViewModel.stateGrid) {
                        lstContent.layoutManager = GridLayoutManager(requireContext(), 2)
                    } else {
                        lstContent.layoutManager = LinearLayoutManager(requireContext())
                    }

                }

                this.attraction = attraction
            })

//            this?.txtNumberFacts?.setOnClickListener {
//                val stringBuilder = StringBuilder()
//
//                attraction?.facts?.forEachIndexed { index, s ->
//                    stringBuilder.append("\u2022 $s")
//                    if (index != attraction.facts.size.minus(1)) {
//                        stringBuilder.append("\n\n")
//                    }
//                }
//                val message = stringBuilder.toString()
//
//                AlertDialog.Builder(requireContext(), R.style.MyDialog)
//                    .setTitle("${attraction?.title} Facts")
//                    .setMessage(message)
//                    .setCancelable(false)
//                    .setPositiveButton("OK") { dialog, _ ->
//                        dialog.dismiss()
//                    }
//                    .show()
//            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}