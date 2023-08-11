package com.test.mvvmbasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.test.mvvmbasic.databinding.ActivityDetailBinding
import com.test.mvvmbasic.vm.TestViewModel

class DetailActivity : AppCompatActivity() {

    lateinit var activityDetailBinding: ActivityDetailBinding
    lateinit var testViewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        initViewModel()
        observe()
    }

    private fun observe() {
        activityDetailBinding.run {
            testViewModel.run {
                testData1.observe(this@DetailActivity) {
                    textView3.text = it
                }
                testData2.observe(this@DetailActivity) {
                    textView4.text = it
                }
            }
        }
    }

    private fun initViewModel() {
        val idx = intent.getIntExtra("idx", 0)
        testViewModel = ViewModelProvider(this)[TestViewModel::class.java]
        testViewModel.apply {
            getDataList(this@DetailActivity)
            setData(idx)
        }
    }
}