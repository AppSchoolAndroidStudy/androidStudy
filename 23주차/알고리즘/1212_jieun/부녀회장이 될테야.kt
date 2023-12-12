fun main(){
    val br = System.`in`.bufferedReader()
    var t = br.readLine().toInt()
    
    val personCountArr = Array(15) {IntArray(15){0}}
    for(i in 1..14){
        personCountArr[0][i] = i
    }
    
    while(t>0){
        val k = br.readLine().toInt()
        val n = br.readLine().toInt()
        
        for(floor in 1..k){
            for(room in 1..n){
                personCountArr[floor][room] = personCountArr[floor-1][room] + personCountArr[floor][room-1]
            }
        }
        println(personCountArr[k][n])
        t--
    }
}