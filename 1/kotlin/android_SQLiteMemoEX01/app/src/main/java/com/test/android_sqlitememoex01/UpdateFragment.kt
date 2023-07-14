package com.test.android_sqlitememoex01

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.test.android_sqlitememoex01.databinding.FragmentUpdateBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.concurrent.thread

class UpdateFragment : Fragment() {

    lateinit var fragmentUpdateBinding: FragmentUpdateBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentUpdateBinding = FragmentUpdateBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentUpdateBinding.run {
            //선택된 메모 객체 가져옴
            val memo = DAO.selectData(mainActivity, mainActivity.currentIdx)

            //뷰의 기본값으로 기존 메모 내용 설정
            editTextUpdateTitle.setText(memo.title)
            editTextUpdateContent.setText(memo.content)

            //뷰 포커스 및 키보드 생성
            mainActivity.focusOnView(editTextUpdateTitle)

            toolbarUpdate.run {
                title = "메모 수정"
                setTitleTextColor(Color.WHITE)

                //툴바 메뉴 지정
                inflateMenu(R.menu.create_menu)
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.UPDATE_FRAGMENT)
                }

                setOnMenuItemClickListener {
                    //내용 미입력 - 다이얼로그 생성
                    if(editTextUpdateTitle.text.isEmpty()){
                        mainActivity.showDialog("메모 제목을 입력해주세요.", editTextUpdateTitle)
                        return@setOnMenuItemClickListener true
                    }

                    if(editTextUpdateContent.text.isEmpty()){
                        mainActivity.showDialog("메모 내용을 입력해주세요.", editTextUpdateContent)
                        return@setOnMenuItemClickListener true
                    }

                    memo.title = editTextUpdateTitle.text.toString()
                    memo.content = editTextUpdateContent.text.toString()
                    //데이터 업데이터
                    DAO.updateData(mainActivity, memo)

                    mainActivity.removeFragment(MainActivity.UPDATE_FRAGMENT)

                    false
                }
            }
        }
        return fragmentUpdateBinding.root
    }
}