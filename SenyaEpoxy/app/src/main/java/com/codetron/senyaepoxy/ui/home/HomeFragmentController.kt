package com.codetron.senyaepoxy.ui.home

import com.airbnb.epoxy.EpoxyController
import com.codetron.senyaepoxy.R
import com.codetron.senyaepoxy.data.Attraction
import com.codetron.senyaepoxy.databinding.ItemAttractionBinding
import com.codetron.senyaepoxy.databinding.ItemHeaderAttractionBinding
import com.codetron.senyaepoxy.databinding.ViewEmptyListBinding
import com.codetron.senyaepoxy.databinding.ViewLoadingListBinding
import com.codetron.senyaepoxy.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class HomeFragmentController : EpoxyController() {

    var isLoading: Boolean = false
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var attractions: List<Attraction> = emptyList()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    private var onClickedCallback: ((id: String) -> Unit)? = null

    fun setOnClickCallback(onClickedCallback: ((id: String) -> Unit)?) {
        this.onClickedCallback = onClickedCallback
    }

    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id(LoadingEpoxyModel.KEY_ID).addTo(this)
            return
        }

        if (attractions.isEmpty()) {
            EmptyDataEpoxyModel().id(EmptyDataEpoxyModel.KEY_ID).addTo(this)
            return
        }

        HeaderEpoxyModel("All Attractions").id(HeaderEpoxyModel.KEY_ID).addTo(this)

        attractions.forEach { attraction ->
            AttractionEpoxyModel(attraction, onClickedCallback)
                .id(attraction.id)
                .addTo(this)
        }
    }

    data class AttractionEpoxyModel(
        private val attraction: Attraction,
        private val onClicked: ((id: String) -> Unit)?
    ) : ViewBindingKotlinModel<ItemAttractionBinding>(R.layout.item_attraction) {

        override fun ItemAttractionBinding.bind() {
            txtTitle.text = attraction.title
            txtMonthsToVisit.text = attraction.monthsToVisit

            if (attraction.imageUrls.isNotEmpty()) {
                Picasso.get()
                    .load(attraction.imageUrls[0])
                    .placeholder(R.color.teal_200)
                    .error(R.color.black)
                    .into(imgHeader)
            }

            root.setOnClickListener {
                onClicked?.invoke(attraction.id)
            }
        }
    }

    data class HeaderEpoxyModel(
        private val headerText: String
    ) : ViewBindingKotlinModel<ItemHeaderAttractionBinding>(R.layout.item_header_attraction) {

        override fun ItemHeaderAttractionBinding.bind() {
            txtHeader.text = headerText
        }

        companion object{
            const val KEY_ID = "HEADER_KEY"
        }

    }

    class LoadingEpoxyModel :
        ViewBindingKotlinModel<ViewLoadingListBinding>(R.layout.view_loading_list) {

        override fun ViewLoadingListBinding.bind() {
            return
        }

        companion object {
            const val KEY_ID = "KEY_LOADING"
        }
    }

    class EmptyDataEpoxyModel :
        ViewBindingKotlinModel<ViewEmptyListBinding>(R.layout.view_empty_list) {

        override fun ViewEmptyListBinding.bind() {
            return
        }

        companion object {
            const val KEY_ID = "KEY_EMPTY_DATA"
        }
    }

}