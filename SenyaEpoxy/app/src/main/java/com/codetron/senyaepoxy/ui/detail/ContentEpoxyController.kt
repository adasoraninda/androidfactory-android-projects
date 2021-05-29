package com.codetron.senyaepoxy.ui.detail

import com.airbnb.epoxy.EpoxyController
import com.codetron.senyaepoxy.R
import com.codetron.senyaepoxy.data.Attraction
import com.codetron.senyaepoxy.databinding.ItemDescriptionBinding
import com.codetron.senyaepoxy.databinding.ItemFactBinding
import com.codetron.senyaepoxy.databinding.ItemFactsHeaderBinding
import com.codetron.senyaepoxy.databinding.ItemMonthsToVisitBinding
import com.codetron.senyaepoxy.epoxy.ViewBindingKotlinModel

class ContentEpoxyController : EpoxyController() {

    private var toggleOnClicked: (() -> Unit)? = null

    private var gridState = true

    var attraction: Attraction? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    fun setGridState(gridState: Boolean) {
        this.gridState = gridState
    }

    fun setOnToggleCallback(toggleOnClicked: () -> Unit) {
        this.toggleOnClicked = toggleOnClicked
    }

    override fun buildModels() {
        attraction?.let {
            DescriptionEpoxyModel(it.description).id(DescriptionEpoxyModel.KEY_ID).addTo(this)

            FactsHeaderEpoxyModel(
                facts = "${it.facts.size} Facts",
                isGridState = gridState,
                onToggleClicked = toggleOnClicked,
            ).id(FactsHeaderEpoxyModel.KEY_ID)
                .addTo(this)

            it.facts.forEachIndexed { index, fact ->
                FactEpoxyModel(fact).id("${fact}_${index}").addTo(this)
            }

            MonthsToVisitEpoxyModel(it.monthsToVisit).id(MonthsToVisitEpoxyModel.KEY_ID).addTo(this)
        }
    }

    class MonthsToVisitEpoxyModel(
        private val monthsToVisit: String
    ) : ViewBindingKotlinModel<ItemMonthsToVisitBinding>(R.layout.item_months_to_visit) {

        override fun ItemMonthsToVisitBinding.bind() {
            txtMonthsToVisit.text = monthsToVisit
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }

        companion object {
            const val KEY_ID = "MONTHS_TO_VISIT"
        }
    }

    class DescriptionEpoxyModel(
        private val description: String
    ) : ViewBindingKotlinModel<ItemDescriptionBinding>(R.layout.item_description) {

        override fun ItemDescriptionBinding.bind() {
            txtDescription.text = description
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }

        companion object {
            const val KEY_ID = "DESCRIPTION"
        }
    }

    class FactsHeaderEpoxyModel(
        private val facts: String,
        private val isGridState: Boolean,
        private val onToggleClicked: (() -> Unit)?
    ) : ViewBindingKotlinModel<ItemFactsHeaderBinding>(R.layout.item_facts_header) {

        override fun ItemFactsHeaderBinding.bind() {
            txtFacts.text = facts

            val imageRes = if (isGridState) {
                R.drawable.ic_list_view_24
            } else {
                R.drawable.ic_grid_view_24
            }

            imgToggle.setImageResource(imageRes)

            imgToggle.setOnClickListener {
                onToggleClicked?.invoke()
            }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }

        companion object {
            const val KEY_ID = "FACTS_HEADER"
        }
    }

    class FactEpoxyModel(
        private val fact: String
    ) : ViewBindingKotlinModel<ItemFactBinding>(R.layout.item_fact) {

        override fun ItemFactBinding.bind() {
            txtFact.text = fact
        }

    }
}