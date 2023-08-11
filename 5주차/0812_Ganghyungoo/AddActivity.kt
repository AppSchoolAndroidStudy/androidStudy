package com.test.mvvmbasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.test.mvvmbasic.databinding.ActivityAddBinding
import com.test.mvvmbasic.databinding.ActivityMainBinding
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

        // MVVM
        testViewModel = ViewModelProvider(this)[TestViewModel::class.java]
        testViewModel.run{
            // 감시자를 설정한다.
//            testData1.observe(this@AddActivity){
//                activityAddBinding.editTextAdd1.setText(it)
//            }
//            testData2.observe(this@AddActivity){
//                activityAddBinding.editTextAdd2.setText(it)
//            }
        }
//
//        // 초기값을 설정한다.
//        testViewModel.testData1.value = ""
//        testViewModel.testData2.value = ""
        //이거 왜 한거죠?????????!!!!!!!!!!!!

        activityAddBinding.run{
            buttonAdd.run{
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