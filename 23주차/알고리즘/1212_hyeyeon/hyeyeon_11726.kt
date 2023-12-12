// 11726 2*n 타일링
fun main() {
    val dp = Array(41) { Array(2) {0} }
    val t = readln().toInt()

    dp[0] = arrayOf(1, 0)
    dp[1] = arrayOf(0, 1)

    for (i in 2..40) {
        for (j in 0..1) {
            dp[i][j] = dp[i - 1][j] + dp[i - 2][j]
        }
    }

    repeat(t) {
        val n = readln().toInt()
        println("${dp[n][0]} ${dp[n][1]}")
    }
}
