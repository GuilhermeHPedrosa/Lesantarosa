package com.example.lesantarosa.ui.fragment.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.PopupWindow
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.lesantarosa.R
import com.example.lesantarosa.databinding.CardOrderFilterBinding
import com.example.lesantarosa.databinding.PopupOrderStatusBinding
import com.example.lesantarosa.databinding.TextviewOrderStatusBinding
import com.example.lesantarosa.models.enums.OrderStatus

class OrderFilterFragment: Fragment() {

    private lateinit var binding: CardOrderFilterBinding

    private val searchFragment = SearchFragment(R.string.input_search_order_hint)

    private val statusPopup by lazy { initializeStatusPopup() }
    private val orderStatus: MutableLiveData<OrderStatus?> = MutableLiveData()

    val filterMediator = MediatorLiveData<Pair<String?, OrderStatus?>>().apply {
        fun update() {
            val search = searchFragment.actualSearch.value
            val status = orderStatus.value
            this.value = Pair(search, status)
        }

        addSource(searchFragment.actualSearch) { update() }
        addSource(orderStatus) { binding.filterByStatusButton.text = it?.getStatusDisplay(requireContext())?.first ; update() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CardOrderFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchFragment()

        handleFilterByStatusButton()
    }

    private fun initializeStatusPopup(): PopupWindow {
        val popupLayout = PopupOrderStatusBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        val popupWindow = PopupWindow(popupLayout.root, WRAP_CONTENT, WRAP_CONTENT, true)
        popupWindow.elevation = 10f

        val statusLayouts = OrderStatus.entries.map { inflateStatusButton(it) }
        statusLayouts.forEach { popupLayout.orderStatusLinearLayout.addView(it) }

        return popupWindow
    }

    private fun inflateStatusButton(status: OrderStatus): View {
        val display = status.getStatusDisplay(requireContext())
        val name = display.first
        val icon = display.second

        val textview = TextviewOrderStatusBinding.inflate(LayoutInflater.from(requireContext()), null, false).root
        textview.text = name

        textview.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
        textview.setOnClickListener {
            orderStatus.value = status
            statusPopup.dismiss()
        }
        return textview
    }

    private fun setupSearchFragment() {
        childFragmentManager.beginTransaction()
            .replace(R.id.filterSearchFragmentContainer, searchFragment)
            .commit()
    }

    private fun handleFilterByStatusButton() {
        val filterByStatusButton = binding.filterByStatusButton
        filterByStatusButton.setOnClickListener {
            statusPopup.showAsDropDown(it)
        }
    }
}