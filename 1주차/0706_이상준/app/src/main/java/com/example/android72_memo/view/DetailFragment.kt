package com.example.android72_memo.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.android72_memo.R
import com.example.android72_memo.databinding.FragmentDetailBinding
import com.example.android72_memo.viewmodel.MemoViewModel

class DetailFragment : Fragment() {

    lateinit var fragmentDetailBinding: FragmentDetailBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: MemoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentDetailBinding = FragmentDetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        viewModel = MemoViewModel(mainActivity)
        mainActivity.isEdit = false
        viewModel.getMemo(mainActivity.selectedMemo.uuid)


        fragmentDetailBinding.run {

            viewModel.memo.run {
                textViewDetailTitle.text = title
                textViewDetailContent.text = content
                textViewDetailDate.text = date
            }

            initToolbar()
        }
        return fragmentDetailBinding.root
    }

    private fun FragmentDetailBinding.initToolbar() {
        toolbarDetail.run {
            title = "메모 읽기"
            inflateMenu(R.menu.detail_menu)
            setTitleTextColor(Color.WHITE)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                mainActivity.removeFragment(MainActivity.DETAIL_FRAGMENT)
            }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_edit -> {
                        clickEditMemo()
                    }

                    R.id.menu_delete -> {
                        clickDeleteMemo()
                    }
                }
                false
            }
        }
    }

    private fun clickDeleteMemo() {
        val alertDialog = AlertDialog.Builder(mainActivity)
        alertDialog.run {
            setTitle("메모 삭제")
            setMessage("메모를 삭제 하겠습니까?")
            setPositiveButton("삭제") { dialog, _ ->
                viewModel.deleteMemo(viewModel.memo.uuid)
                mainActivity.removeFragment(MainActivity.DETAIL_FRAGMENT)
            }
            setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    private fun clickEditMemo() {
        viewModel.updateMemo(viewModel.memo)
        mainActivity.replaceFragment(
            MainActivity.ADD_AND_EDIT_FRAGMENT,
            true,
            true
        )
        mainActivity.isEdit = true
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMemo(mainActivity.selectedMemo.uuid)
        fragmentDetailBinding.run {
            viewModel.memo.run {
                textViewDetailTitle.text = title
                textViewDetailContent.text = content
                textViewDetailDate.text = date
            }
        }
    }
}