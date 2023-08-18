package com.test.coroutineproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.test.coroutineproject.databinding.ActivityLoginBinding
import repository.LoginRepository

class LoginActivity : AppCompatActivity() {
    lateinit var activityLoginBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        activityLoginBinding.run {
            login.setOnClickListener {
                if (inputLoginId.text.isEmpty()||inputLoginPw.text.isEmpty()){
                    Snackbar.make(activityLoginBinding.root, "모든 정보를 입력해주세요", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val loginId=inputLoginId.text.toString()
                val loginPassword=inputLoginPw.text.toString()
                LoginRepository.checkInfoToLogin(loginId){
                    if(it.result.exists()==false){
                        Snackbar.make(activityLoginBinding.root, "존재하지 않는 정보입니다", Snackbar.LENGTH_SHORT).show()
                        activityLoginBinding.inputLoginId.setText("")
                        activityLoginBinding.inputLoginPw.setText("")
                    }else{
                        for (check in it.result.children){
                            if ((check.child("password").value) as String !=loginPassword){
                                Snackbar.make(activityLoginBinding.root, "비밓번호가 틀렸습니다", Snackbar.LENGTH_SHORT).show()
                                activityLoginBinding.inputLoginPw.setText("")
                            }else{
                                Snackbar.make(activityLoginBinding.root, "로그인 완료", Snackbar.LENGTH_SHORT).show()
                                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                            }
                        }
                    }
                }
            }



            goSignup.setOnClickListener {
                startActivity(Intent(this@LoginActivity,SignupActivity::class.java))
            }
        }
    }
}