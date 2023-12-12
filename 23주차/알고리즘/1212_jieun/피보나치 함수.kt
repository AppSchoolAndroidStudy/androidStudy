fun main(){
    val br = System.`in`.bufferedReader()
    var t = br.readLine().toInt()
    
    val countArray = Array(41) {IntArray(2){0}}
    countArray[0][0] = 1
    countArray[0][1] = 0
    countArray[1][0] = 0
    countArray[1][1] = 1
    
    while(t>0){
        val n = br.readLine().toInt()
        for(i in 2..n){
            countArray[i][0] = countArray[i-1][0] + countArray[i-2][0]
            countArray[i][1] = countArray[i-1][1] + countArray[i-2][1]
        }
        println("${countArray[n][0]} ${countArray[n][1]}")
        t--
    }
}