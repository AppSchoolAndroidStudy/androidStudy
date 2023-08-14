package com.woojugoing.mvvmbasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.woojugoing.mvvmbasic.databinding.ActivityAddBinding
import com.woojugoing.mvvmbasic.model.DataClass1
import com.woojugoing.mvvmbasic.repo.DataRepository
import com.woojugoing.mvvmbasic.vm.ViewModel1

class AddActivity : AppCompatActivity() {

    lateinit var activityAddBinding: ActivityAddBinding
    lateinit var viewModel1: ViewModel1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(activityAddBinding.root)

        // MVVM Setting - 소유자를 생성해줘야 함.
        // 액티비티나 프래그먼트, 서비스, 브로트캐스트 리시버등 생명주기가 존재하면 모두 소유자가 될 수 있음
        // ViewModel의 소유자를 하나로 지정하면, 그 소유자가 소멸이 되지 않는 이상 계속 살아있을 수 있음
        // Data 공유 목적이면 소유자를 맞추고, 해당 화면만 관리하려면, this를 넣어주면 됨.
        viewModel1 = ViewModelProvider(this)[ViewModel1::class.java]
        viewModel1.run {
            // 감시자 설정 : 감시자를 달아주었을 경우, 넣어놓은 변수가 변화하였을 때, 감시자가 동작함. it에 새로운 객체가 들어옴
            data1.observe(this@AddActivity){ activityAddBinding.editTextAdd1.setText(it) }
            data2.observe(this@AddActivity){ activityAddBinding.editTextAdd2.setText(it) }
        }

        activityAddBinding.run {
            buttonAdd.run {
                setOnClickListener {
                    val a1 = editTextAdd1.text.toString()
                    val a2 = editTextAdd2.text.toString()
                    val t1 = DataClass1(data1 = a1, data2 = a2)
                    DataRepository.addData(this@AddActivity, t1)
                    finish()
                }
            }
        }
    }
}