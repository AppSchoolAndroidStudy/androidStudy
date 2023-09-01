package com.woojugoing.jetpack01_practice

class DogRepository {
    fun getAllData(): List<Dog> {
        return listOf(
            Dog(0, "おかか", "쌀집 강아지(가다랑어포)", R.drawable.dog1),
            Dog(1, "つな", "쌀집 강아지(참치)", R.drawable.dog2),
            Dog(2, "ごま", "쌀집 강아지(참깨)", R.drawable.dog3),
            Dog(3, "うめ", "쌀집 강아지(매실)", R.drawable.dog4),
            Dog(4, "あんこ", "경단집 강아지(팥)", R.drawable.dog5),
            Dog(5, "きなこ", "경단집 강아지(콩고물)", R.drawable.dog6),
            Dog(5, "さくら", "경단집 강아지(벚꽃)", R.drawable.dog7),
            Dog(5, "もなか", "경단집 강아지(모나카)", R.drawable.dog8),
            Dog(5, "すずめさん", "그냥 참새(참새)", R.drawable.dog9),
            Dog(5, "こむぎ", "빵집 강아지(밀)", R.drawable.dog10),
        )
    }
}