// 1003 피보나치 함수
// 2 x n    방법의 수
// -----------------
// 2 x 1      1
// 2 x 2      2
// 2 x 3      3
// 2 x 4      5
// 2 x 5      8
// 2 x 6      13
// 2 x 7      21
// 2 x 8      34
// 2 x 9      55
// f(n-1) + f(n-2)
fun main() {

    val dp = Array(1001) { 0 }
    val n = readln().toInt()

    dp[1] = 1
    dp[2] = 2

    for (i in 3..n) {
        dp[i] = (dp[i - 1] + dp[i - 2]) % 10007
    }

    println(dp[n])

}