package model

import com.google.gson.annotations.SerializedName
import java.util.Objects

data class APIObject (@SerializedName("data") var data:MutableList<Campsite>){
    override fun toString(): String {
        return "${data}"
    }
}