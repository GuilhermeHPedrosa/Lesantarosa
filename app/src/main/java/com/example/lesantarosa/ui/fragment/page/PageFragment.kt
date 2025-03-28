package com.example.lesantarosa.ui.fragment.page

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lesantarosa.R
import com.example.lesantarosa.ui.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout

abstract class PageFragment: Fragment() {

    protected val mainViewModel by activityViewModels<MainViewModel>()

    protected val tabLayout: TabLayout by lazy { requireActivity().findViewById(R.id.tab_layout) }

}