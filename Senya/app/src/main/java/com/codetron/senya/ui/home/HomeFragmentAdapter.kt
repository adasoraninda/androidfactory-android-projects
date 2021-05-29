package com.codetron.senya.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codetron.senya.R
import com.codetron.senya.data.Attraction
import com.codetron.senya.databinding.ItemAttractionBinding
import com.squareup.picasso.Picasso

class HomeFragmentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val attractions = arrayListOf<Attraction>()

    private var onClickedCallback: ((id: String) -> Unit)? = null

    fun setOnClickCallback(onClickedCallback: ((id: String) -> Unit)?) {
        this.onClickedCallback = onClickedCallback
    }

    fun setData(attractions: List<Attraction>) {
        this.attractions.clear()
        this.attractions.addAll(attractions)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AttractionViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AttractionViewHolder).onBind(attractions[position], onClickedCallback)
    }

    override fun getItemCount(): Int {
        return attractions.size
    }

    inner class AttractionViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_attraction, parent, false)
    ) {

        private val binding by lazy { ItemAttractionBinding.bind(itemView) }

        fun onBind(attraction: Attraction, onClicked: ((id: String) -> Unit)?) {
            binding.txtTitle.text = attraction.title
            binding.txtMonthsToVisit.text = attraction.monthsToVisit

            if (attraction.imageUrls.isNotEmpty()) {
                Picasso.get()
                    .load(attraction.imageUrls[0])
                    .placeholder(R.color.teal_200)
                    .error(R.color.black)
                    .into(binding.imgHeader)
            }

            binding.root.setOnClickListener {
                onClicked?.invoke(attraction.id)
            }

        }

    }

}