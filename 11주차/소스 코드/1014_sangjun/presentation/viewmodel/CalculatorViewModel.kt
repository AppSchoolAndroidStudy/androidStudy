package com.qure.calculator_tdd.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qure.calculator_tdd.domain.Calculator
import com.qure.calculator_tdd.domain.Expression
import com.qure.calculator_tdd.domain.HistoryRepository
import com.qure.calculator_tdd.domain.Operator
import com.qure.calculator_tdd.domain.model.Memory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val repository: HistoryRepository
) : ViewModel() {

    private val _expression = MutableLiveData(Expression.EMPTY)
    val expression: LiveData<Expression> = _expression

    private val _memeoryVisible = MutableLiveData(false)
    val memeoryVisible: LiveData<Boolean> = _memeoryVisible

    private val _memory = MutableLiveData<List<Memory>>(emptyList())
    val memory: LiveData<List<Memory>> = _memory

    fun addToExpression(operand: Int) {
        _expression.value = _expression.value?.plus(operand)
    }

    fun addToExpression(operator: Operator) {
        _expression.value = _expression.value?.plus(operator)
    }

    fun calculate() {
        val calculator = Calculator()
        val expression = _expression.value.toString()
        val result = calculator.calculate(expression)
        _expression.value = Expression(emptyList()) + result
        insert(Memory(expression,"= " + result.toString()))
    }

    fun getAll() {
        viewModelScope.launch {
           _memory.value = repository.getAll()
        }
    }

    fun insert(memory: Memory) {
        viewModelScope.launch {
            repository.insert(memory)
            getAll()
        }
    }

    fun removeExpression() {
        _expression.value = _expression.value?.removeExpression()
    }

    fun removeAllExpression() {
        _expression.value = _expression.value?.removeAllExpresstion()
    }

    fun showCalculatorMemory() {
        _memeoryVisible.value = isVisible()
        getAll()
    }

    fun removeAllMemory() {
        viewModelScope.launch {
            repository.removeAll()
            _memory.value = emptyList()
        }
    }

    private fun isVisible(): Boolean {
        if (_memeoryVisible.value == true) {
            return false
        }
        return true
    }
}
