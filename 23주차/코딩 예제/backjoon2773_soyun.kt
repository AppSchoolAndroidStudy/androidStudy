fun main() {
    val input = readLine()!!.toInt()

    for (i in 0 until input) {
        val f = readLine()!!.toInt() // 층
        val r = readLine()!!.toInt() // 호

        val apartment = Array(f + 1) { IntArray(r + 1) { 1 } } // 모든 값을 1로 초기화

        // 0층 초기화
        for (j in 1..r) {
            apartment[0][j] = j
        }

        // 1층부터 k층까지 계산
        for (floor in 1..f) {
            for (room in 2..r) { // 1호는 이미 1로 초기화 되어 있으므로 2호부터 시작
                apartment[floor][room] = apartment[floor][room - 1] + apartment[floor - 1][room]
            }
        }

        println(apartment[f][r])
    }
}
