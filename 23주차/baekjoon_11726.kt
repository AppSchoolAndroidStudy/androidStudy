fun main() {
    val n = readln().toInt()
    val dp = MutableList(1001) { 0 }
    dp[1] = 1
    dp[2] = 2

    (3..n).forEach { index ->
        dp[index] = (dp[index - 1] + dp[index - 2]) % 10007
    }
    println(dp[n] % 10007)
}
