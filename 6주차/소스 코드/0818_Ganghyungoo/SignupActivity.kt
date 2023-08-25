package com.test.coroutineproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.test.coroutineproject.databinding.ActivitySignupBinding
import model.User
import repository.SignupRepository

class SignupActivity : AppCompatActivity() {
    lateinit var activitySignupBinding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySignupBinding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(activitySignupBinding.root)

        activitySignupBinding.run {
            signup.setOnClickListener {
                //데이터베이스에서 유저 인덱스를 성공적으로 가져왔다면...
                SignupRepository.getUserIdx {
                    var userIdx= it.result.value as Long
                    val signId=inputSignupId.text.toString()
                    val signPw=inputSignupPw.text.toString()

                    val userObject= User(userIdx,signId,signPw)
                    //데이터베이스 내부 사용자 인덱스가 소비되었다면 +1
                    userIdx++

                    //성공적으로 회원가입 정보를 데이터베이스에 저장했다면..
                    SignupRepository.addUserData(userObject){
                        //데이터베이스 내부에 저장한 유저 인덱스+1을 성공적으로 했다면....
                        SignupRepository.setPlusedUserIndex(userIdx){
                            Snackbar.make(activitySignupBinding.root, "가입이 완료되었습니다", Snackbar.LENGTH_SHORT).show()
                            finish()
                        }
                    }



                }
            }



        }
    }
}