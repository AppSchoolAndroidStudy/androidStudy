package com.test.testcode

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.test.testcode.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    lateinit var mainActivity: MainActivity

    // onCreateView : 뷰를 만드는 라이프사이클
    // 최대한 빨리 뷰를 반환하는 것을 구글에서 권장
    // inflate만 하는 게 좋음
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        return binding.root
    }

    // onViewCreated : 뷰를 제어하는 라이프 사이클 (뷰 초기화, 뷰 제어)
    // 생성된 뷰를 제어하기 위한 최적화된 라이프 사이클
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            buttonLoginJagilogin.setOnClickListener {
                val email = editinputLoginEmail.text.toString()
                val pw = editinputLoginPassword.text.toString()
                login(email, pw)
            }
        }
    }

    fun login(email: String, pw: String): Boolean {
        if (email == "hyeyeon@naver.com" && pw == "1234") {
            Snackbar.make(binding.root, "로그인 성공", Snackbar.LENGTH_SHORT).show()
            return true
        } else {
            val builder = MaterialAlertDialogBuilder(mainActivity)
            builder.run {
                setTitle("로그인 실패")
                setMessage("이메일, 비밀번호가 틀렸습니다.")
                setPositiveButton("확인") { _: DialogInterface, _: Int ->
                    binding.run {
                        editinputLoginEmail.setText("")
                        editinputLoginPassword.setText("")
                    }
                }
                show()
            }
            return false
        }
    }
}