// 2775 부녀회장이 될테야
// k : 층, n : 호
//     1호  2호  3호
// 0층 | 1 | 2 | 3 |
// 1층 | 1 | 3 | 6 |
// 2층 | 1 | 4 | 10 |
// 3층 | 1 | 5 | 15 |
// k층 n호의 사람 수 = (k-1층 n호의 사람 수) + (k층 n-1호의 사람 수)
fun main() {

    val dp = Array(15) { Array(15) { 0 } }

    for (i in 0..14) {
        for (j in 0..14) {
            if (i == 0 || j == 0) dp[i][j] = j + 1
            else dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
        }
    }

    val input = readln().toInt()

    repeat(input) {
        val k = readln().toInt()
        val n = readln().toInt()
        println(dp[k][n - 1])
    }

}