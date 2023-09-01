package com.test.jetpackcompose.data

import com.test.jetpackcompose.R
import com.test.jetpackcompose.model.AnimalDataClass

class AnimalDateProvider {

    companion object DataProvider{
        val animalList = listOf(
            AnimalDataClass("하늘 & 구름", R.drawable.pic_1, "하늘이랑 구름이"),
            AnimalDataClass("레이", R.drawable.pic_2, "집사의 코딩을 방해하는 레이"),
            AnimalDataClass("레이", R.drawable.pic_3, "공부하는 집사 옆에서 꾸벅꾸벅 조는 레이"),
            AnimalDataClass("삐용이", R.drawable.pic_4, "삐용 : 댕-하"),
            AnimalDataClass("몽이", R.drawable.pic_5, "몽이는 무슨 생각을 하고 있을까요?"),
            AnimalDataClass("레이", R.drawable.pic_6, "캣타워에서 집사를 내려다보는 레이"),
            AnimalDataClass("체리", R.drawable.pic_7, "견생 3개월차 체리입니다"),
            AnimalDataClass("레이", R.drawable.pic_8, "레이의 솜방망이 자랑"),
            AnimalDataClass("구름", R.drawable.pic_9, "카페 루프탑에서 기분 좋아보이는 구름이"),
            AnimalDataClass("냥이", R.drawable.pic_10, "냥이는 일광욕 중.\n근데 눈이 부셔서 뜨질 못하는 중"),
            AnimalDataClass("하늘", R.drawable.pic_11, "하늘이는 구름이랑 싸웠어요 ㅜ"),
            AnimalDataClass("길냥이", R.drawable.pic_12, "정처기 실기 보고 역 가는 길에 만난 오드아이 고양이"),
            AnimalDataClass("레이", R.drawable.pic_13, "여름 맞이 짚모자 장만한 레이\n하도 싫어해서 5분만에 벗겨줬습니다.."),
            AnimalDataClass("레이", R.drawable.pic_14, "레이는 그루밍중" )
        )
    }

}