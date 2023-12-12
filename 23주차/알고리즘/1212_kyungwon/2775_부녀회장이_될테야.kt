import java.util.*

fun main() {
    val dp = Array(15) { Array(15) { 0 } }

    for(i in 0 until 15) {
        dp[0][i] = i
        dp[i][1] = 1
    }


    for(i in 1 until 15) {
        for(j in 2 until 15) {
            dp[i][j] = dp[i-1][j] + dp[i][j-1]
        }
    }

    val sc = Scanner(System.`in`)
    val t = sc.nextInt()
    for(i in 1 .. t) {
        val a = sc.nextInt()
        val b = sc.nextInt()
        println(dp[a][b])
    }
}