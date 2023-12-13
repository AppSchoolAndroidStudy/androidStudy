import java.io.BufferedReader
import java.io.InputStreamReader

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val inputCount = (br.readLine().toInt())
    val problemFloor = IntArray(inputCount){0}
    val problemLine = IntArray(inputCount){0}
        for (i in 0 until inputCount){
        problemFloor[i] = br.readLine().toInt()
        problemLine[i] = br.readLine().toInt()
    }

    //메모리제이션
    val resultArr = Array(15){IntArray(15){0}}
    for (i in 0..14){
        resultArr[0][i] = i
    }

    //저장
    for (k in 1..14){
        var countPeopleEachFloor = resultArr[k-1][0]
        for (n in 1..14){
            resultArr[k][n] = resultArr[k-1][n] + countPeopleEachFloor
            countPeopleEachFloor += resultArr[k-1][n]
        }
    }
    for(i in problemFloor.indices){
        println(resultArr[problemFloor[i]][problemLine[i]])
    }
}