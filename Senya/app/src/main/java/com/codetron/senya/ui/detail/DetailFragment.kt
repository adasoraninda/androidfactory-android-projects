package com.codetron.senya.ui.detail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.navArgs
import com.codetron.senya.R
import com.codetron.senya.data.Attraction
import com.codetron.senya.databinding.FragmentDetailBinding
import com.codetron.senya.ui.BaseFragment
import com.squareup.picasso.Picasso

class DetailFragment : BaseFragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    private val args: DetailFragmentArgs by navArgs()

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

        viewModel.getAttractionDetail(args.id)

        viewModel.attraction.observe(viewLifecycleOwner, {
            initDetailData(it)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_location -> {
                viewModel.intentEventLocation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initDetailData(attraction: Attraction?) {
        with(binding) {
            this?.txtTitle?.text = attraction?.title
            this?.txtDescription?.text = attraction?.description
            this?.txtNumberFacts?.text = "${attraction?.facts?.size} facts"
            this?.txtMonthsVisited?.text = attraction?.monthsToVisit

            Picasso.get()
                .load(attraction?.imageUrls?.get(0))
                .placeholder(R.color.teal_200)
                .error(R.color.black)
                .into(this?.imgPhoto)

            this?.txtNumberFacts?.setOnClickListener {
                val stringBuilder = StringBuilder()

                attraction?.facts?.forEachIndexed { index, s ->
                    stringBuilder.append("\u2022 $s")
                    if (index != attraction?.facts?.size?.minus(1)) {
                        stringBuilder.append("\n\n")
                    }
                }
                val message = stringBuilder.toString()

                AlertDialog.Builder(requireContext(), R.style.MyDialog)
                    .setTitle("${attraction?.title} Facts")
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}