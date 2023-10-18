package com.qure.calculator_tdd.domain

class Calculator {

    private var operators = mutableListOf<String>()
    private var operands = mutableListOf<Int>()

    fun calculate(rawExpression: String): Int {
        val values = rawExpression.split(" ")
        operators = sortOperator(values)
        operands = sortOperand(values)
        return getValueStack().sum()
    }

    private fun getValueStack(): List<Int> {
        val valueStack = mutableListOf(Operator.PLUS.operation(0, operands.removeFirst()))
        for (i in 0..operators.lastIndex) {
            val operator = operators.removeFirst()
            val operand = operands.removeFirst()
            val num = findMulOrDiv(operator, valueStack)
            valueStack.add(Operator.of(operator).operation(num, operand))
        }
        return valueStack
    }

    private fun findMulOrDiv(operator: String, valueStack: MutableList<Int>): Int {
        if (operator == "*" || operator == "/") {
            return valueStack.removeLast()
        }
        return 0
    }

    private fun sortOperator(values: List<String>): MutableList<String> {
        return values.filter { isOperator(it) }.toMutableList()
    }

    private fun sortOperand(values: List<String>): MutableList<Int> {
        return values.filter { !isOperator(it) }.map { it.toInt() }.toMutableList()
    }

    private fun isOperator(value: String): Boolean {
        val operator = listOf("+", "-", "*", "/")
        return operator.contains(value)
    }
}
