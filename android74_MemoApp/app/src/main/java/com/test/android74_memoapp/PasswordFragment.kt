package com.test.android74_memoapp

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.test.android74_memoapp.databinding.FragmentPasswordBinding


class PasswordFragment : Fragment() {

    lateinit var fragmentPasswordBinding: FragmentPasswordBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentPasswordBinding = FragmentPasswordBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentPasswordBinding.run{

            // 키보드를 올린다.
            mainActivity.showSoftInput(userPw, 150)

            toolbarPassword.run{
                title = "비빌번호 설정"
            }

            submitBtn.run{
                setOnClickListener {
                    clickSubmitBtn()
                }
            }

            userPw2.run{
                setOnEditorActionListener { textView, i, keyEvent ->
                    clickSubmitBtn()
                    false
                }
            }
        }

        return fragmentPasswordBinding.root
    }

    // 비밀번호 검사
    fun clickSubmitBtn(){
        fragmentPasswordBinding.run{
            // 입력한 내용을 가져온다.
            val str1 = userPw.text.toString()
            val str2 = userPw2.text.toString()

            if(str1.length == 0){
                val builder = AlertDialog.Builder(mainActivity)
                builder.setTitle("비빌번호 입력 오류")
                builder.setMessage("비밀번호를 입력해주세요")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(userPw, 150)
                }
                builder.show()
                return
            }

            if(str2.length == 0){
                val builder = AlertDialog.Builder(mainActivity)
                builder.setTitle("비빌번호 입력 오류")
                builder.setMessage("비밀번호를 입력해주세요")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(userPw2, 150)
                }
                builder.show()
                return
            }

            if(str1 != str2){
                val builder = AlertDialog.Builder(mainActivity)
                builder.setTitle("비빌번호 입력 오류")
                builder.setMessage("비빌번호를 다르게 입력하였습니다")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    userPw.setText("")
                    userPw2.setText("")
                    mainActivity.showSoftInput(userPw, 150)
                }
                builder.show()
                return
            }

            val builder = AlertDialog.Builder(mainActivity)
            builder.setTitle("설정 완료")
            builder.setMessage("비빌번호 설정이 완료되었습니다")
            builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->

                // 사용자가 설정한 비빌번호를 저장한다.
                val passwordClass = PasswordClass(0, str1)
                PasswordDAO.insert(mainActivity, passwordClass)

                mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT, false, true)
            }
            builder.show()
        }
    }

}