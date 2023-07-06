package com.example.android72_memo.view

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.android72_memo.R
import com.example.android72_memo.databinding.FragmentAddAndEditBinding
import com.example.android72_memo.model.Memo
import com.example.android72_memo.viewmodel.MemoViewModel
import kotlin.concurrent.thread

class AddAndEditFragment : Fragment() {

    lateinit var fragmentAddAndEditBinding: FragmentAddAndEditBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: MemoViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAddAndEditBinding = FragmentAddAndEditBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        viewModel = MemoViewModel(mainActivity)
        viewModel.getMemo(mainActivity.selectedMemo.uuid)

        fragmentAddAndEditBinding.run {
            val (memoTitle, memoContent, date, uuid) = viewModel.memo
            showKeyboard()
            setMemo(memoTitle, memoContent)
            initToolbar(date, uuid)
        }
        return fragmentAddAndEditBinding.root
    }

    private fun FragmentAddAndEditBinding.showKeyboard() {
        editTextAddAndEditTitle.requestFocus()
        thread {
            SystemClock.sleep(500)
            val imm = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(mainActivity.currentFocus, 0)
        }
    }

    private fun FragmentAddAndEditBinding.initToolbar(
        date: String,
        uuid: String
    ) {
        toolbarAddAndEdit.run {
            title = if (mainActivity.isEdit) "메모 수정" else "메모 추가"
            setTitleTextColor(Color.WHITE)
            setNavigationIcon(R.drawable.ic_back)
            inflateMenu(R.menu.add_edit_menu)
            setNavigationOnClickListener {
                mainActivity.removeFragment(MainActivity.ADD_AND_EDIT_FRAGMENT)
            }

            setOnMenuItemClickListener {
                val writedTitle = editTextAddAndEditTitle.text.toString()
                val writedContent = editTextAddAndEditContent.text.toString()
                if (mainActivity.isEdit) {
                    updateMemo(writedTitle, writedContent, date, uuid)
                } else {
                    insertMemo(writedTitle, writedContent)
                }
                mainActivity.removeFragment(MainActivity.ADD_AND_EDIT_FRAGMENT)
                false
            }
        }
    }

    private fun FragmentAddAndEditBinding.setMemo(
        memoTitle: String,
        memoContent: String
    ) {
        editTextAddAndEditTitle.setText(memoTitle)
        editTextAddAndEditContent.setText(memoContent)
    }

    private fun insertMemo(writedTitle: String, writedContent: String) {
        viewModel.insertMemo(
            Memo(writedTitle, writedContent)
        )
    }

    private fun updateMemo(
        writedTitle: String,
        writedContent: String,
        date: String,
        uuid: String
    ) {
        viewModel.updateMemo(Memo(writedTitle, writedContent, date, uuid))
        mainActivity.removeFragment(MainActivity.ADD_AND_EDIT_FRAGMENT)
    }
}