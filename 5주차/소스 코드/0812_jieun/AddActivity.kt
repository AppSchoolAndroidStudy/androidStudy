package com.test.mvvmbasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.mvvmbasic.databinding.ActivityAddBinding
import com.test.mvvmbasic.model.TestDataClass
import com.test.mvvmbasic.repository.TestDataRepository
import com.test.mvvmbasic.vm.TestViewModel

class AddActivity : AppCompatActivity() {

    lateinit var activityAddBinding: ActivityAddBinding

    lateinit var testViewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAddBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(activityAddBinding.root)

        //MVVM 설정
        //소유자는 생명주기를 가지는 모든 요소라면 다 들어갈 수 있음(안드로이드 4대 구성요소와 프래그먼트 가능)
        testViewModel = ViewModelProvider(this)[TestViewModel::class.java]
        testViewModel.run {
            //감시자 설정
            testData1.observe(this@AddActivity){
                //it에는 새롭게 셋팅한 값이 들어옴
                activityAddBinding.editTextAdd1.setText(it)
            }
            testData2.observe(this@AddActivity){
                activityAddBinding.editTextAdd2.setText(it)
            }
        }

        //초기값을 설정한다
        //라이브 데이터에 값이 변경될때 위에 설정한 감시자가 자동 작동돼서 화면으 갱신함
        testViewModel.testData1.value = "하하하"
        testViewModel.testData2.value = "호호호"

        activityAddBinding.run {
            buttonAdd.run {
                setOnClickListener {
                    val a1 = editTextAdd1.text.toString()
                    val a2 = editTextAdd2.text.toString()

                    val t1 = TestDataClass(testData1 = a1, testData2 = a2)
                    TestDataRepository.addData(this@AddActivity, t1)
                    finish()
                }
            }
        }

    }
}