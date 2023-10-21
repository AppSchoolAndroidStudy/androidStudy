package com.qure.calculator_tdd.domain

enum class Operator(val sign: String, val operation: (Int, Int) -> Int) {
    PLUS("+", {x, y -> y}),
    MINUS("-", {x, y -> -1 * y}),
    MUL("*", {x, y -> x * y}),
    DIV("/", {x, y -> x / y});

    companion object {
        fun of(sign: String): Operator =
            values().find { it.sign == sign }
                ?: throw IllegalArgumentException("찾을 수 없는 연산자입니다.")
    }
}
