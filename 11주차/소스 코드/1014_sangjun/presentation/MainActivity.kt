package com.qure.calculator_tdd.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.qure.calculator_tdd.databinding.ActivityMainBinding
import com.qure.calculator_tdd.presentation.viewmodel.CalculatorViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CalculatorViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViewModel()
        initRecyclerView()
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
    }

    private fun initViewModel() {
        binding.viewmodel = viewModel
        viewModel.memory.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun initRecyclerView() {
        binding.mainActivityRecyclerViewHistory.adapter = adapter
    }
}