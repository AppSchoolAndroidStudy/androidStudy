package com.test.study13_databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.test.study13_databinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding

    var text = "Hello!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // activity_main.xml 레이아웃 파일에서 선언한 data 변수에 해당 activity 할당
        mainBinding.main = this

        mainBinding.run {
            changeBtn.setOnClickListener {
                text = "Hello Binding!"
                // data 변동 시 binding된 view들에게 변화 알림
                Log.d("텍스트",text)

                // 변화가 있으면 반영
                invalidateAll()

            }
        }
    }
}