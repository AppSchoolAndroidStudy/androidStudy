package com.example.android72_memo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.android72_memo.R
import com.example.android72_memo.databinding.ActivityMainBinding
import com.example.android72_memo.model.Memo

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    var selectedMemo = Memo()
    var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        replaceFragment(MAIN_FRAGMENT, false, false)
    }


    fun replaceFragment(name:String, addToBackStack:Boolean, animate:Boolean){

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var newFragment = when(name){
            MAIN_FRAGMENT -> {
                MainFragment()
            }
            ADD_AND_EDIT_FRAGMENT -> {
                AddAndEditFragment()
            }
            DETAIL_FRAGMENT -> {
                DetailFragment()
            }
            else -> {
                Fragment()
            }
        }

        if(newFragment != null) {
            fragmentTransaction.replace(R.id.mainContainer, newFragment)

            if (animate == true) {
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            }

            if (addToBackStack == true) {
                fragmentTransaction.addToBackStack(name)
            }

            fragmentTransaction.commit()
        }
    }

    fun removeFragment(name:String){
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {
        val MAIN_FRAGMENT = "MainFragment"
        val ADD_AND_EDIT_FRAGMENT = "AddAndEditFragment"
        val DETAIL_FRAGMENT = "DetailFragment"
    }
}