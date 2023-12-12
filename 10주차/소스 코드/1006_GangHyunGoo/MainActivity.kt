package com.test.databindingproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.databindingproject.databinding.ActivityMainBinding
import com.test.databindingproject.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    val items = arrayOf("CM", "M", "KM")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        activityMainBinding.mainViewmodel = viewModel
        activityMainBinding.lifecycleOwner = this

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)


        //스피너 설정
        activityMainBinding.spinner.adapter = myAdapter
        activityMainBinding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long,
                ) {

                    //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                    when (position) {
                        0 -> {
                            MainViewModel.settingSelectedUnit("CM")
                        }

                        1 -> {
                            MainViewModel.settingSelectedUnit("M")
                        }

                        2 -> {
                            MainViewModel.settingSelectedUnit("KM")
                        }

                        else -> {
                            MainViewModel.settingSelectedUnit("CM")
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    MainViewModel.settingSelectedUnit("CM")
                }

            }

        activityMainBinding.editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.updateConversion(s.toString())
            }

        })
    }
}