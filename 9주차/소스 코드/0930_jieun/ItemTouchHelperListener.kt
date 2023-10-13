package com.example.customview

interface ItemTouchHelperListener {
    fun onItemMove(fromPosition : Int, toPosition : Int) : Boolean
    fun onItemSwipe(position : Int)
}