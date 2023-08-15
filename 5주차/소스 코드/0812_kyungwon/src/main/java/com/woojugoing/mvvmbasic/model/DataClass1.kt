package com.woojugoing.mvvmbasic.model

// Model : App에서 사용되는 모든 데이터를 담을 클래스들을 정의한다.
// 예를 들어, 학생과 자동차 데이터를 관리한다고 생각하면 따로 만들어서 정의한다.
// idx는 값을 가져올 때만 사용하는 것이기 때문에 기본값을 0 으로 맞춰줌.

data class DataClass1(
    var idx: Int = 0,
    var data1: String,
    var data2: String
)
