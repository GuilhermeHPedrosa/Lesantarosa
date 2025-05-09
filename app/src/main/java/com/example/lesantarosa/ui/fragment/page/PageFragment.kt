package com.example.lesantarosa.ui.fragment.page

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.lesantarosa.R
import com.example.lesantarosa.databinding.AppTabLayoutBinding
import com.google.android.material.tabs.TabLayout

abstract class PageFragment: Fragment() {

    private lateinit var toolbar: Toolbar

    private lateinit var tabLayoutContainer: FrameLayout
    protected var tabLayout: TabLayout? = null

    var title: String = ""
        set(it) { field = it ; toolbar.title = it }

    //fragment especifico para pager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity = requireActivity()

        toolbar = activity.findViewById(R.id.toolbar)
        tabLayoutContainer = activity.findViewById(R.id.tab_layout_container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabLayout?.let { tabLayoutContainer.removeView(it) }
    }

    protected fun initializeTabLayout() { tabLayout = AppTabLayoutBinding.inflate(layoutInflater).tabLayout }

    private fun setupTabLayout() { tabLayout?.let { tabLayoutContainer.addView(it) } }

}