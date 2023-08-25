package com.example.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.coroutine.databinding.ActivityResultBinding
import com.example.coroutine.vm.MainViewModel

class ResultActivity : AppCompatActivity() {

    lateinit var activityResultBinding: ActivityResultBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityResultBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(activityResultBinding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        activityResultBinding.run {
            mainViewModel.run {
                dataList.observe(this@ResultActivity) {
                    if (dataList.value?.size != 0) {
                        textViewResultTitle.text = dataList.value?.get(intent.getIntExtra("rowIdx", 0))?.title.toString()
                    }
                }
            }

            mainViewModel.getDataList(this@ResultActivity)
        }
    }
}