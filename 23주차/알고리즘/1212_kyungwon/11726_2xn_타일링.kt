import java.util.*

fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val dp = Array(1001){0}

    dp[1] = 1
    dp[2] = 2

    for(i in 3..n) {
        dp[i] = (dp[i-1] + dp[i-2]) % 10007
    }

    print(dp[n])
}