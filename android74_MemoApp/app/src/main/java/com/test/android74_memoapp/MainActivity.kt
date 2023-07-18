package com.test.android74_memoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.test.android74_memoapp.databinding.ActivityMainBinding
import kotlin.concurrent.thread

// AndroidManifest.xml 라벨 및 아이콘설정
// label : 아이콘 하단에 표시되는 앱의 이름
// icon, roundIcon : 애플리케이션 아이콘

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    companion object {
        val PASSWORD_FRAGMENT = "PasswordFragment"
        val LOGIN_FRAGMENT = "LoginFragment"
        val CATEGORY_MAIN_FRAGMENT = "CategoryMainFragment"
        val MEMO_MAIN_FRAGMENT = "MemoMainFragment"
        val MEMO_ADD_FRAGMENT = "MemoAddFragment"
        val MEMO_READ_FRAGMENT = "MemoReadFragment"
        val MEMO_MODIFY_FRAGMENT = "MemoModifyFragment"
    }

    // 키보드 관리자
    lateinit var inputMethodManager:InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // SplashScreen을 적용한다.
        // setContentView 전에 작성해야 한다.
        installSplashScreen()

        // SystemClock.sleep(10000)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        // 비빌번호 설정 여부에 따라 처음 보여줄 프레그먼트를 다르게 지정한다.
        //replaceFragment(PASSWORD_FRAGMENT, false, false)

        // 저장된 비빌번호를 가져온다.
        val passwordList = PasswordDAO.selectAll(this)

        // 설정된 비밀번호가 없다면
        if(passwordList.size == 0){
            replaceFragment(PASSWORD_FRAGMENT, false, false)
        }
        // 설정된 비밀번호가 있다면
        else {
            replaceFragment(LOGIN_FRAGMENT, false, false)
        }


        val dbHelper = DBHelper(this)
        dbHelper.writableDatabase
        dbHelper.close()
    }

    // 지정한 Fragment를 보여주는 메서드
    fun replaceFragment(name:String, addToBackStack:Boolean, animate:Boolean, arguments:Bundle?=null){
        SystemClock.sleep(200)
        // Fragment 교체 상태로 설정한다.
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        // 새로운 Fragment를 담을 변수
        var newFragment = when(name){
            PASSWORD_FRAGMENT -> PasswordFragment()
            LOGIN_FRAGMENT -> LoginFragment()
            CATEGORY_MAIN_FRAGMENT -> CategoryMainFragment()
            MEMO_MAIN_FRAGMENT -> MemoMainFragment()
            MEMO_ADD_FRAGMENT -> MemoAddFragment()
            MEMO_READ_FRAGMENT -> MemoReadFragment()
            MEMO_MODIFY_FRAGMENT -> MemoModifyFragment()
            else -> Fragment()
        }

        // 현재 프래그먼트에 번들을 설정해준다.
        newFragment.arguments = arguments

        if(newFragment != null) {

            if (animate == true) {
                // 애니메이션을 설정한다.
                // fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                fragmentTransaction.setCustomAnimations(
                    // A -> B로 이동을 가정한다.
                    // B가 나타날 때의 애니메이션
                    R.anim.slide_in,
                    // A가 사라질 때의 애니메이션
                    R.anim.fade_out,
                    // A가 나타날 때의 애니메이션
                    R.anim.fade_in,
                    // B가 사라질 때의 애니메이션
                    R.anim.slide_out
                )
            }

            // Fragment를 교채한다.
            fragmentTransaction.replace(R.id.main_container, newFragment)

            if (addToBackStack == true) {
                // Fragment를 Backstack에 넣어 이전으로 돌아가는 기능이 동작할 수 있도록 한다.
                fragmentTransaction.addToBackStack(name)
            }

            // 교체 명령이 동작하도록 한다.
            fragmentTransaction.commit()
        }
    }

    // Fragment를 BackStack에서 제거한다.
    fun removeFragment(name:String){
        SystemClock.sleep(200)
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    // 키보드를 올려주는 메서드
    fun showSoftInput(view:View, delay:Long){
        view.requestFocus()
        thread {
            SystemClock.sleep(delay)
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    // 키보드를 내려주는 메서드
    fun hideSoftInput(){
        if(currentFocus != null){

            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}

// 테이블의 데이터를 담을 클래스들
data class PasswordClass(var passwordIdx:Int, var passwordData:String)
data class CategoryClass(var categoryIdx:Int, var categoryName:String)
data class MemoClass(var memoIdx:Int, var memoSubject:String, var memoText:String, var memoDate:String, var memoCategoryIdx:Int)













