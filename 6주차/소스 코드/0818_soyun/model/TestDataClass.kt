package com.test.mvvmbasic.model

import android.icu.text.CaseMap.Title

// model : 애플리케이션에서 사용되는 모든 데이터를 담을 클래스들을 정의한다.

data class TestDataClass(var id:Int = 0, var title: String, var content : String, var createdAt : String, var updatedAt : String, var UserId : Int)