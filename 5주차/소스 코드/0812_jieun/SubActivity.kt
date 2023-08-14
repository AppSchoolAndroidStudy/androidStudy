package com.test.mvvmbasic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.test.mvvmbasic.databinding.ActivitySubBinding
import com.test.mvvmbasic.vm.TestViewModel

class SubActivity : AppCompatActivity() {

    lateinit var activitySubBinding: ActivitySubBinding

    lateinit var testViewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySubBinding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(activitySubBinding.root)

        testViewModel = ViewModelProvider(this)[TestViewModel::class.java]
        testViewModel.run {
            testDataResult1.observe(this@SubActivity){
                activitySubBinding.textView1.text = it
            }
            testDataResult2.observe(this@SubActivity){
                activitySubBinding.textView2.text = it
            }
        }

        testViewModel.testDataResult1.value = intent.getStringExtra("dataResult1")
        testViewModel.testDataResult2.value = intent.getStringExtra("dataResult2")

    }
}