import java.util.*

fun main() {
    val dp0 = Array(41) { 0 }
    val dp1 = Array(41) { 0 }

    dp0[0] = 1
    dp1[0] = 0

    dp0[1] = 0
    dp1[1] = 1


    for(i in 2 .. 40) {
        dp0[i] = dp0[i-1] + dp0[i-2]
        dp1[i] = dp1[i-1] + dp1[i-2]
    }

    val sc = Scanner(System.`in`)
    val x = sc.nextInt()
    for(i in 1 .. x) {
        val y = sc.nextInt()
        println("${dp0[y]} ${dp1[y]}")
    }
}