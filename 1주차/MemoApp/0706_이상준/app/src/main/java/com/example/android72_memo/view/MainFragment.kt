package com.example.android72_memo.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android72_memo.R
import com.example.android72_memo.databinding.FragmentMainBinding
import com.example.android72_memo.model.Memo
import com.example.android72_memo.view.adapter.MainAdapter
import com.example.android72_memo.viewmodel.MemoViewModel

class MainFragment : Fragment() {

    lateinit var fragmentMainBinding: FragmentMainBinding
    lateinit var mainActivity: MainActivity
    lateinit var memoAdapter: MainAdapter
    lateinit var viewModel: MemoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMainBinding = FragmentMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        viewModel = MemoViewModel(mainActivity)
        viewModel.getAllMemo()

        initAdapter()
        fragmentMainBinding.run {
            initToolbar()
            initRecyclerView()
        }
        return fragmentMainBinding.root
    }

    private fun initAdapter() {
        memoAdapter = MainAdapter(viewModel.memos, this@MainFragment, {
            mainActivity.replaceFragment(MainActivity.DETAIL_FRAGMENT, true, true)
            mainActivity.selectedMemo = it
        })
    }

    private fun FragmentMainBinding.initToolbar() {
        toolbarMain.run {
            title = "메모앱"
            setTitleTextColor(Color.WHITE)
            inflateMenu(R.menu.main_menu)
            setOnMenuItemClickListener {
                mainActivity.replaceFragment(MainActivity.ADD_AND_EDIT_FRAGMENT, true, true)
                mainActivity.selectedMemo = Memo()
                false
            }
        }
    }

    private fun FragmentMainBinding.initRecyclerView() {
        recyclerViewMain.run {
            adapter = memoAdapter
            layoutManager = LinearLayoutManager(mainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    mainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        memoAdapter.notifyDataSetChanged()
    }
}