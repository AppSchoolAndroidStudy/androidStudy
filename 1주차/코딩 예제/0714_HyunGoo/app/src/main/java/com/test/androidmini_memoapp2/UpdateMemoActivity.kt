package com.test.androidmini_memoapp2

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.androidmini_memoapp2.databinding.ActivityUpdateMemoBinding

class UpdateMemoActivity : AppCompatActivity() {
    lateinit var activityUpdateMemoBinding: ActivityUpdateMemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityUpdateMemoBinding = ActivityUpdateMemoBinding.inflate(layoutInflater)
        setContentView(activityUpdateMemoBinding.root)
        val name = intent.getStringExtra("memoName")
        val id = intent.getIntExtra("idx", -1)
        val memoObj = MemoDAO.selectData(this, id, name!!)

        activityUpdateMemoBinding.run {
            toolbar6.run {
                inflateMenu(R.menu.update_memo_menu)
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
                setOnMenuItemClickListener {
                    val changedName = changedMemoName.text.toString()
                    val changedDetail = changedMemoDetail.text.toString()
                    memoObj.memoName = changedName
                    memoObj.memoDetail = changedDetail
                    MemoDAO.updateData(this@UpdateMemoActivity, memoObj)
                    finish()
                    false
                }
            }
            changedMemoName.setText(memoObj.memoName)
            changedMemoDetail.setText(memoObj.memoDetail)
        }


    }
}