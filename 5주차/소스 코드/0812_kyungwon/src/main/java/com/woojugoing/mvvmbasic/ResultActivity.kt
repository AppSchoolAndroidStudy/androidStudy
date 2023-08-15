package com.woojugoing.mvvmbasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.woojugoing.mvvmbasic.databinding.ActivityResultBinding
import com.woojugoing.mvvmbasic.repo.DataRepository
import com.woojugoing.mvvmbasic.vm.ViewModel1

class ResultActivity : AppCompatActivity() {

    lateinit var activityResultBinding : ActivityResultBinding
    lateinit var viewModel1: ViewModel1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityResultBinding = ActivityResultBinding.inflate(layoutInflater)
        val idx = intent.getIntExtra("idx", 0)
        setContentView(activityResultBinding.root)
        viewModel1 = ViewModelProvider(this@ResultActivity)[ViewModel1::class.java]
        viewModel1.run {
            data1.observe(this@ResultActivity){activityResultBinding.textViewResult1.setText(it)}
            data2.observe(this@ResultActivity){activityResultBinding.textViewResult2.setText(it)}
            data1.value = DataRepository.getData(MainActivity.mainActivity, idx).data1
            data2.value = DataRepository.getData(MainActivity.mainActivity, idx).data2
        }
    }
}