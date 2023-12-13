import java.io.BufferedReader
import java.io.InputStreamReader

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))

    val input = br.readLine().toInt()

    val resultArr = LongArray(1000+1){i-> 0}
    resultArr[1]=1
    resultArr[2]=2

    for (i in 3..1000) {
        resultArr[i] = (resultArr[i - 1] + resultArr[i - 2]) % 10007
    }
    print((resultArr[input]))
}