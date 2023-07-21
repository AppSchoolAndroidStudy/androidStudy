package com.woojugoing.project_skw_0710

import android.content.DialogInterface
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.woojugoing.project_skw_0710.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var fragmentResultBinding: FragmentResultBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentResultBinding = FragmentResultBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        val result = DAO.selectData(mainActivity, DAO.selectAllData(mainActivity).size - mainActivity.rowPosition)

        fragmentResultBinding.run {
            toolbarResult.run {
                title = "메모 읽기"
                setTitleTextColor(Color.WHITE)

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter = BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?. setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                }
                // Back Button을 누르면 동작하는 Listener
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.RESULT_FRAGMENT)
                    mainActivity.replaceFragment(MainActivity.MAIN_FRAGMENT, true, true)
                }

                inflateMenu(R.menu.menu_result)
                setOnMenuItemClickListener {
                    when(it?.itemId){
                        R.id.menu_result_editMemo -> {
                            mainActivity.replaceFragment(MainActivity.EDIT_FRAGMENT, addToBackStack = true, animate = true)
                        }
                        R.id.menu_result_deleteMemo -> {
                            val builder = AlertDialog.Builder(mainActivity)

                            builder.run {
                                setTitle("메모 삭제")
                                setMessage("메모를 삭제하시겠습니까?")
                                setNegativeButton("취소",null)
                                setPositiveButton("삭제"){ dialogInterface: DialogInterface, i: Int ->
                                    DAO.deleteData(mainActivity, DAO.selectAllData(mainActivity).size - mainActivity.rowPosition)
                                    mainActivity.removeFragment(MainActivity.RESULT_FRAGMENT)
                                    mainActivity.replaceFragment(MainActivity.MAIN_FRAGMENT, true, true)
                                }
                                show()
                            }
                        }
                    }
                    false
                }

            }
            textViewResult1.text = result.title
            textViewResult2.text = result.date
            textViewResult3.text = result.content
        }

        return fragmentResultBinding.root
    }

}