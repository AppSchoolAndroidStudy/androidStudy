package com.androidstudy.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.androidstudy.databinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding type을 알 수 없을 땐 DataBindingUtil 클래스를 사용
        // Fragment, ListView or RecyclerView 일 때 data binding 사용 방법
        // 방법 1) val listItemBinding = ListItemBinding.inflate(layoutInflater, viewGroup, false)
        // 방법 2) val listItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, viewGroup, false)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val changeNumViewModel = ViewModelProvider(this)[ChangeNumViewModel::class.java]

        binding.lifecycleOwner = this
        binding.changeNumViewModel = changeNumViewModel

        changeNumViewModel.currentNum.observe(this) { newNum ->
            binding.textViewCurrentNumber.text = newNum.toString()
        }
    }
}