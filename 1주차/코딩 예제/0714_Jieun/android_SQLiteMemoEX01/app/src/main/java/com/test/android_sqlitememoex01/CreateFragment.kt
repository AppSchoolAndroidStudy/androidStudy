package com.test.android_sqlitememoex01

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.test.android_sqlitememoex01.databinding.FragmentCreateBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.concurrent.thread

class CreateFragment : Fragment() {

    lateinit var fragmentCreateBinding: FragmentCreateBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCreateBinding = FragmentCreateBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentCreateBinding.run {

            //해당 뷰에 포커스 주고 키보드 생성
            mainActivity.focusOnView(editTextCreateTitle)

            toolbarCreate.run {
                title = "메모 추가"
                setTitleTextColor(Color.WHITE)

                //툴바 메뉴 지정
                inflateMenu(R.menu.create_menu)

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.CREATE_FRAGMENT)
                }

                setOnMenuItemClickListener {
                    //값 미입력 - 다이얼로그 생성하고 이벤트 리스너 종료
                    if(editTextCreateTitle.text.isEmpty()){
                        mainActivity.showDialog("메모 제목을 입력해주세요.", editTextCreateTitle)
                        return@setOnMenuItemClickListener true
                    }
                    if(editTextCreateContent.text.isEmpty()){
                        mainActivity.showDialog("메모 내용을 입력해주세요.", editTextCreateContent)
                        return@setOnMenuItemClickListener true
                    }

                    //입력된 값을 가지고 객체 생성
                    val title = editTextCreateTitle.text.toString()
                    val content = editTextCreateContent.text.toString()
                    val now = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                    val memo = MemoClass(0, title, content, now)
                    //데이터 저장
                    DAO.insertData(mainActivity, memo)
                    mainActivity.removeFragment(MainActivity.CREATE_FRAGMENT)

                    false
                }
            }
        }
        return fragmentCreateBinding.root
    }

//    //다이얼로그 화면에 출력
//    fun showDialog(message:String, editText: EditText){
//        val builder = AlertDialog.Builder(mainActivity)
//        builder.setTitle("항목 미입력")
//            .setMessage(message)
//            .setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
//                mainActivity.focusOnView(editText)
//            }
//        builder.show()
//    }
}