import java.math.BigInteger

fun main(){
    val br = System.`in`.bufferedReader()
    val n = br.readLine().toInt()
    
    val fibonacci = Array<BigInteger>(1001){BigInteger.ZERO}
    fibonacci[1] = BigInteger.ONE
    
    val countArr = Array<BigInteger>(1001){BigInteger.ZERO}
    countArr[1] = BigInteger.ONE
    
    for(i in 2..n){
        fibonacci[i] = fibonacci[i-1] + fibonacci[i-2]
        countArr[i] = countArr[i-1] + fibonacci[i-1]
    }

    print(countArr[n].mod(BigInteger("10007")))
}