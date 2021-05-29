package com.codetron.senyaepoxy.ui.detail

import com.airbnb.epoxy.EpoxyController
import com.codetron.senyaepoxy.R
import com.codetron.senyaepoxy.databinding.ItemHeaderImageBinding
import com.codetron.senyaepoxy.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class HeaderEpoxyController : EpoxyController() {

    var imageUrls: List<String?> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        imageUrls.forEachIndexed { index, url ->
            HeaderImageEpoxyModel(url).id("${url}_${index}").addTo(this)
        }
    }

    class HeaderImageEpoxyModel(
        private val imageUrl: String?
    ) : ViewBindingKotlinModel<ItemHeaderImageBinding>(R.layout.item_header_image) {
        override fun ItemHeaderImageBinding.bind() {
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.color.teal_200)
                .error(R.color.black)
                .into(imgPhotoHeader)
        }
    }
}