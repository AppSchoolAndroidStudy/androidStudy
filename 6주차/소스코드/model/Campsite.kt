package model

import com.google.gson.annotations.SerializedName

data class Campsite(
    @SerializedName("위도") var 위도: String,
    @SerializedName("경도") var 경도: String,
    @SerializedName("전화번호") var 전화번호: String,
    @SerializedName("홈페이지") var 홈페이지: String,
    @SerializedName("시설명") var 시설명: String,
    @SerializedName("시설 소개") var 시설소개: String
)