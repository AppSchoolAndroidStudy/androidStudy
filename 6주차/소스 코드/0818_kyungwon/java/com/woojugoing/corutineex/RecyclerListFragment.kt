package com.woojugoing.corutineex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.woojugoing.corutineex.adapter.RecyclerViewAdapter
import com.woojugoing.corutineex.databinding.FragmentRecyclerListBinding
import com.woojugoing.corutineex.model.RecyclerList
import com.woojugoing.corutineex.viewmodel.MainViewModel

class RecyclerListFragment : Fragment() {

    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var fragmentRecyclerListBinding: FragmentRecyclerListBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentRecyclerListBinding = FragmentRecyclerListBinding.inflate(inflater)
        val view = fragmentRecyclerListBinding.root
        initViewModel(view)
        initViewModel()
        return view
    }

    private fun initViewModel(view: View){
        fragmentRecyclerListBinding.recyclerMain.run {
            recyclerViewAdapter = RecyclerViewAdapter()
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getRecyclerListObserver().observe(viewLifecycleOwner, Observer<RecyclerList> {
            if(it != null) {
                recyclerViewAdapter.setUpdatedData(it.items)
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall()
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecyclerListFragment
    }
}