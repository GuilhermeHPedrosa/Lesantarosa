package com.example.lesantarosa.ui.fragment.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.R
import com.example.lesantarosa.database.preferences.CartPreferences
import com.example.lesantarosa.databinding.FragmentPageCartBinding
import com.example.lesantarosa.models.data.VisualComponents
import com.example.lesantarosa.ui.fragment.component.CartFragment
import com.example.lesantarosa.ui.fragment.bottomsheet.CartOptionsBottomSheetDialog
import com.example.lesantarosa.ui.viewmodel.CartViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class PageCartFragment: PageFragment() {

    private lateinit var binding: FragmentPageCartBinding

    private lateinit var viewModel: CartViewModel

    private val cartOptionsDialog by lazy { CartOptionsBottomSheetDialog(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.defineVisualComponents(VisualComponents())

        viewModel = getViewModel<CartViewModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeCartFragment()

        handleMoreOptionsButton()
        handleToPaymentButton()

        handleCartNotes()
        handleCartDiscounts()
        handleClearCart()
    }

    private fun initializeCartFragment() {
        val cartFragment = CartFragment()

        childFragmentManager
            .beginTransaction()
            .replace(R.id.cart_container, cartFragment)
            .commit()
    }

    private fun handleCartNotes() {
        cartOptionsDialog.noteButtonListener = {
            lifecycleScope.launch {
                val note = CartPreferences.getCartNote(requireContext()).first()

                val direction = PageCartFragmentDirections.actionPageCartToNoteDialog(note)
                findNavController().navigate(direction)
            }
        }
    }

    private fun handleCartDiscounts() {
        cartOptionsDialog.discountButtonListener = {
            lifecycleScope.launch {
                val totalPrice = viewModel.totalPrice
                val actualDiscount = CartPreferences.getCartDiscount(requireContext()).first()

                val direction = PageCartFragmentDirections.actionPageCartToDiscountDialog(totalPrice.toFloat(), actualDiscount.toFloat())
                findNavController().navigate(direction)
            }
        }
    }

    private fun handleClearCart() {
        cartOptionsDialog.clearCartButtonListener = {
            lifecycleScope.launch {
                viewModel.clearCart()

                CartPreferences.updateNote(requireContext(), null)
                CartPreferences.updateDiscount(requireContext(), null)

                findNavController().navigateUp()
            }
        }
    }

    private fun handleMoreOptionsButton() {
        val moreOptionsButton = binding.moreOptionButton
        moreOptionsButton.setOnClickListener { cartOptionsDialog.show() }
    }

    private fun handleToPaymentButton() {
        val goToPaymentButton = binding.goToPaymentButton
        goToPaymentButton.setOnClickListener {
            val direction = PageCartFragmentDirections.actionPageCartToPagePayment(viewModel.finalPrice.toFloat())
            findNavController().navigate(direction)
        }
    }
}