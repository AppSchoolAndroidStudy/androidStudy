package com.woojugoing.study_databinding

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.woojugoing.study_databinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val liveUser = MutableLiveData<Question>()
    private var a = 0
    private var b = 0
    private var c = 0
    private var d = 0
    private var e = 0
    private var f = 0

    private var totalAttempts = 0
    private var correctAttempts = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this@MainActivity
        binding.apply {
            lifecycleOwner = this@MainActivity
            etName.text = "x^3 = 0"
        }

        binding.btnOk.setOnClickListener {

            totalAttempts++

            val x1 = binding.x1.text.toString().toInt()
            val x2 = binding.x2.text.toString().toInt()
            val x3 = binding.x3.text.toString().toInt()

            if (x1 == a && x2 == b && x3 == c) {
                correctAttempts++
            }

            Toast.makeText(this, "정답 : $a $b $c", Toast.LENGTH_LONG).show()
            a = (-5..5).random()
            b = (-5..5).random()
            c = (-5..5).random()
            d = (a + b + c) * -1
            e = (a * b + a * c + b * c)
            f = (a * b * c) * -1

            swapValuesIfNeeded()

            showNum(d)
            showNum(e)
            showNum(f)

            Log.d("정답", "$a $b $c $d $e $f")
            binding.etName.text = "x^3 ${showNum(d)}x^2 ${showNum(e)}x ${showNum(f)} = 0"

            var practice = "${binding.x1.text}, ${binding.x2.text}, ${binding.x3.text}"
            liveUser.value = Question(binding.etName.text.toString(), practice)

            binding.textScore.text = "${correctAttempts}/${totalAttempts}"

            binding.x1.text.clear()
            binding.x2.text.clear()
            binding.x3.text.clear()

        }
    }
    
    private fun swapValuesIfNeeded() {
        if (a > b) {
            swapValues('a', 'b')
        }

        if (b > c) {
            swapValues('b', 'c')
        }
    }

    private fun swapValues(firstChar: Char, secondChar: Char) {
        val temp = when (firstChar) {
            'a' -> a
            'b' -> b
            'c' -> c
            else -> 0
        }

        when (secondChar) {
            'a' -> a = temp
            'b' -> b = temp
            'c' -> c = temp
        }
    }

    private fun showNum(x: Int): String {
        return if (x >= 0) {
            "+$x"
        } else {
            "$x"
        }
    }

}



