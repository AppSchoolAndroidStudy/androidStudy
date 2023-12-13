import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val inputCount = br.readLine().toInt()
    val inputArr = IntArray(inputCount) { i ->
        br.readLine().toInt()
    }

    val resultArr = Array(41){i->LongArray(2){i->0} }

    resultArr[0][0] = 1
    resultArr[0][1] = 0
    resultArr[1][0] = 0
    resultArr[1][1] = 1
    resultArr[2][0] = 1
    resultArr[2][1] = 1

    for (index in 3..40){
        resultArr.get(index)[0] = resultArr.get(index-1)[0] + resultArr.get(index-2)[0]
        resultArr.get(index)[1] = resultArr.get(index-1)[1] + resultArr.get(index-2)[1]
    }

    for (index in inputArr){
        println("${resultArr.get(index)[0]} ${resultArr.get(index)[1]}")
    }
}