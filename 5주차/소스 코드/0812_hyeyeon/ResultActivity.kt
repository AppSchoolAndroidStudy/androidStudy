package com.test.mvvmbasic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.test.mvvmbasic.databinding.ActivityResultBinding
import com.test.mvvmbasic.vm.TestViewModel

class ResultActivity : AppCompatActivity() {

    lateinit var activityResultBinding: ActivityResultBinding
    lateinit var testViewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityResultBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(activityResultBinding.root)

        // ViewModel 객체를 가져온다.
        testViewModel = ViewModelProvider(this)[TestViewModel::class.java]

        activityResultBinding.run {
            // ViewModel 객체가 가지고 있는 프로퍼티에 대한 감시자를 설정한다
            testViewModel.run {
                testData1.observe(this@ResultActivity) {
                    textViewData1.text = it
                }

                testData2.observe(this@ResultActivity) {
                    textViewData2.text = it
                }
            }

            // 데이터를 가져온다.
            testViewModel.getDataOne(this@ResultActivity, intent.getIntExtra("testIdx", 0))

        }
    }
}