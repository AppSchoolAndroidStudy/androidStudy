package com.test.androidmini_memoapp2

import android.content.DialogInterface
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.test.androidmini_memoapp2.databinding.ActivityShowMemoBinding

class ShowMemoActivity : AppCompatActivity() {
    lateinit var activityShowMemoBinding: ActivityShowMemoBinding
    var idx: Int? = null
    var position: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityShowMemoBinding = ActivityShowMemoBinding.inflate(layoutInflater)
        setContentView(activityShowMemoBinding.root)
        idx = intent.getIntExtra("id", -1)
        position = intent.getIntExtra("position", -1)
        activityShowMemoBinding.run {
            toolbar6.run {
                inflateMenu(R.menu.show_memo_memo)
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
                    when (it.itemId) {
                        R.id.updateMemo -> {
                            val intent =
                                Intent(this@ShowMemoActivity, UpdateMemoActivity::class.java)
                            intent.putExtra(
                                "memoName",
                                MemoDAO.selectAllData(
                                    this@ShowMemoActivity,
                                    idx!!
                                )[position!!].memoName
                            )
                            intent.putExtra(
                                "idx",
                                MemoDAO.selectAllData(
                                    this@ShowMemoActivity,
                                    idx!!
                                )[position!!].categoryId
                            )
                            startActivity(intent)
                            false
                        }

                        R.id.deleteMemo -> {
                            //다이얼로그 생성을 위한 객체를 생성한다
                            val builder = AlertDialog.Builder(this@ShowMemoActivity)
                            //타이틀
                            builder.setTitle("메모 삭제")
                            builder.setMessage("해당 메모를 삭제하시겠습니까?")
                            builder.setPositiveButton("삭제") { dialogInterface: DialogInterface, i: Int ->
                                MemoDAO.deleteData(this@ShowMemoActivity, idx!!, MemoDAO.selectAllData(this@ShowMemoActivity, idx!!)[position!!].memoName)
                                finish()
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            false
                        }

                        else -> {
                            false
                        }
                    }
                }
            }

            showMemoName.text =
                MemoDAO.selectAllData(this@ShowMemoActivity, idx!!)[position!!].memoName
            showMemoDate.text = MemoDAO.selectAllData(this@ShowMemoActivity, idx!!)[position!!].date
            showMemoDetail.text =
                MemoDAO.selectAllData(this@ShowMemoActivity, idx!!)[position!!].memoDetail
        }


    }

    override fun onRestart() {
        super.onRestart()
        activityShowMemoBinding.showMemoName.text =
            MemoDAO.selectAllData(this@ShowMemoActivity, idx!!)[position!!].memoName
        activityShowMemoBinding.showMemoDate.text =
            MemoDAO.selectAllData(this@ShowMemoActivity, idx!!)[position!!].date
        activityShowMemoBinding.showMemoDetail.text =
            MemoDAO.selectAllData(this@ShowMemoActivity, idx!!)[position!!].memoDetail
    }
}