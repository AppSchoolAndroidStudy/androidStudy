package com.test.androidmini_memoapp2

import java.io.Serializable

data class Category(var idx:Int,var name:String):Serializable

data class Memo(var idx:Int,val categoryId:Int,var memoName:String, var memoDetail:String,var date:String):Serializable