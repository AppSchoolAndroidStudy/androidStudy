package com.test.android_sqlitememoex01

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    //메모 객체 담을 리스트
    var memoList = mutableListOf<MemoClass>()
    //선택 항목 객체의 멤버 idx값
    var currentIdx = 0

    companion object{
        //Activity가 관리할 프래그먼트들의 이름
        val MAIN_FRAGMENT = "MainFragment"
        val CREATE_FRAGMENT = "CreateFragment"
        val RESULT_FRAGMENT = "ResultFragment"
        val UPDATE_FRAGMENT = "UpdateFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //메인 프래그먼트로 전환
        replaceFragment(MAIN_FRAGMENT, false, false)
    }

    fun replaceFragment(name:String, addToBackStack:Boolean, animate:Boolean){
        //Fragment 교체 상태로 설정
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        //새로운 fragment담을 변수
        var newFragment = when(name){
            MAIN_FRAGMENT->{
                MainFragment()
            }
            CREATE_FRAGMENT->{
                CreateFragment()
            }
            RESULT_FRAGMENT->{
                ResultFragment()
            }
            UPDATE_FRAGMENT->{
                UpdateFragment()
            }
            else->{
                Fragment()
            }
        }

        if(newFragment !=null){
            //Fragment를 교체한다
            fragmentTransaction.replace(R.id.mainContainer, newFragment)

            if(animate){
                //애니메이션 설정
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            }

            if(addToBackStack){
                //fragment를 backstack에 넣어 이전으로 돌아가는 기능이 동작할 수 있도록 한다.
                fragmentTransaction.addToBackStack(name)
            }

            //교체 명령이 동작하도록 한다
            fragmentTransaction.commit()
        }
    }

    //Fragment를 backstack에서 제거한다
    fun removeFragment(name : String){
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    //editText뷰에 포커스주고 키보드 생성
    fun focusOnView(editText: EditText){
        editText.requestFocus()
        thread {
            SystemClock.sleep(500)
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, 0)
        }
    }

    //다이얼로그 화면에 출력
    fun showDialog(message:String, editText: EditText){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("항목 미입력")
            .setMessage(message)
            .setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                focusOnView(editText)
            }
        builder.show()
    }
}

data class MemoClass(var idx:Int, var title:String, var content:String, var date:String)