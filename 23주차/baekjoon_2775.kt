fun main() {
    (0 until readln().toInt()).forEach {
        val dp = MutableList(15) { MutableList(15) { 0 } }
        dp[0] = MutableList(15) { i -> i }
        val a = readln().toInt()
        val b = readln().toInt()

        (1..a).forEach { x ->
            (1..14).forEach { y ->
                dp[x][y] = dp[x - 1].take(y + 1).sum()
            }
        }
        println(dp[a][b])
    }
}