package com.example.lesantarosa.ui.fragment.component

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.lesantarosa.R
import com.example.lesantarosa.databinding.FragmentListBinding
import com.example.lesantarosa.repository.Resource
import com.example.lesantarosa.ui.adapter.ListAdapter

abstract class ListFragment<T>: Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    protected lateinit var adapter: ListAdapter<T>

    protected var filterFragment: Fragment? = null

    private val mediator = MediatorLiveData<Resource<List<T>>>()
    private var actualSource: LiveData<List<T>>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeFilterFragment()
        initializeRecyclerView()

        setupFilterFragment()
        setupRecyclerView()

        observeMediator()
        observeListFilters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupFilterFragment() {
        val filter = filterFragment ?: return

        childFragmentManager.beginTransaction()
            .replace(R.id.filter_fragment_container, filter)
            .commit()
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.listRecyclerview
        recyclerView.adapter = adapter
    }

    private fun observeMediator() {
        mediator.observe(viewLifecycleOwner) { resource ->
            resource.data?.let { adapter.refresh(it) }
            resource.error?.let { Log.i("", "Error loading items: $it") }
        }
    }

    protected fun updateSource(newSource: LiveData<List<T>>) {
        actualSource?.let { mediator.removeSource(it) }
        actualSource = newSource

        mediator.addSource(newSource) { values ->
            mediator.value = Resource(values)
        }
    }

    protected open fun initializeFilterFragment() {}

    protected abstract fun initializeRecyclerView()

    protected abstract fun observeListFilters()

}