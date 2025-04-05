package com.example.lesantarosa.ui.fragment.page

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.R
import com.example.lesantarosa.databinding.CardItemBinding
import com.example.lesantarosa.databinding.FragmentPageCheckoutBinding
import com.example.lesantarosa.models.data.VisualComponents
import com.example.lesantarosa.models.entities.Order
import com.example.lesantarosa.ui.fragment.component.CustomInput
import com.example.lesantarosa.ui.viewmodel.CheckoutViewModel
import com.example.lesantarosa.ui.viewmodel.SaleViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class PageCheckoutFragment: PageFragment() {

    private lateinit var binding: FragmentPageCheckoutBinding

    private lateinit var viewModel: CheckoutViewModel

    private val checkoutInputs = listOf(
        CustomInput("Order Title", "Invalid Title", Regex("^.{3,100}\$"), InputType.TYPE_CLASS_TEXT),
        CustomInput("Customer Contact", "Invalid Contact", Regex("^\\(\\d{2}\\) \\d{5}-\\d{4}\$"), InputType.TYPE_CLASS_PHONE),
        CustomInput("Deadline", "Invalid Deadline", Regex("^(?:[1-9]|[1-9][0-9]|[12][0-9]{2}|3[01][0-9]|365)\$"), InputType.TYPE_CLASS_TEXT)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.defineVisualComponents(VisualComponents())

        viewModel = getViewModel<CheckoutViewModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCheckoutInputs()

        handleCheckoutButton()
    }

    private fun setupCheckoutInputs() {
        val checkoutInputLinearLayout = binding.checkoutInputLinearLayout
        checkoutInputs.forEach {
            val inputLayout = it.createInputLayout(requireContext())
            checkoutInputLinearLayout.addView(inputLayout)
        }
    }

    private fun handleCheckoutButton() {
        val checkoutButton = binding.checkoutButton
        checkoutButton.setOnClickListener {
            try {
                val title = checkoutInputs[0].getInputValue()
                //val customerContact = checkoutInputs[1].getInputValue()
                val deadline = checkoutInputs[2].getInputValue().toInt()

                viewModel.finishOrder(requireContext(), title, "82 99223510", deadline) {
                    findNavController().popBackStack(R.id.fragment_page_sell, false)
                }

            } catch (e: Exception) {
                Log.i("", e.message.toString())
            }
        }
    }
}