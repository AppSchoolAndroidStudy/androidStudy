package com.test.androidmini_memoapp2

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.androidmini_memoapp2.databinding.ActivityAddMemoBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddMemoActivity : AppCompatActivity() {
    lateinit var activityAddMemoBinding: ActivityAddMemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddMemoBinding= ActivityAddMemoBinding.inflate(layoutInflater)
        setContentView(activityAddMemoBinding.root)
        val categoryId=intent.getIntExtra("categoryId",-1)


        activityAddMemoBinding.run {
            toolbar4.run {
                setTitle("메모 추가")
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.add_memo_menu)
                //백버튼 띄우기
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                // 백 버튼의 아이콘 색상을 변경한다.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                }
                // 백 버튼을 누르면 동작하는 리스너
                setNavigationOnClickListener {
                    finish()
                }


                //툴바의 추가 버튼 클릭시
                setOnMenuItemClickListener {
                    //추가 하는 DAO넣어야함.
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val now = sdf.format(Date())

                    val memoName=activityAddMemoBinding.inputMemoName.text.toString()
                    val memoDetail=activityAddMemoBinding.inputMemoDetail.text.toString()

                    val memoObj=Memo(0,categoryId,memoName,memoDetail,now)

                    MemoDAO.insertData(this@AddMemoActivity,memoObj)
                    finish()
                    false
                }
            }
        }
    }
}