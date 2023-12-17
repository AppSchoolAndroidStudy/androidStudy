data class Fibonacci(val zero: Int, val one: Int)

fun main() {
    (0 until readln().toInt()).forEach {
        val number = readln().toInt()
        val dp = MutableList(41) { Fibonacci(0, 0) }
        dp[0] = dp[0].copy(1, 0)
        dp[1] = dp[1].copy(0, 1)
        dp[2] = dp[2].copy(1, 1)

        (3..number).forEach { index ->
            dp[index] = dp[index].copy(
                dp[index - 1].zero + dp[index - 2].zero,
                dp[index - 1].one + dp[index - 2].one,
            )
        }
        println("${dp[number].zero} ${dp[number].one}")
    }
}