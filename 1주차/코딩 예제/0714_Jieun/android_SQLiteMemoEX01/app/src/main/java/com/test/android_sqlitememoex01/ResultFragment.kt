package com.test.android_sqlitememoex01

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.test.android_sqlitememoex01.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    lateinit var fragmentResultBinding: FragmentResultBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivity = activity as MainActivity
        fragmentResultBinding = FragmentResultBinding.inflate(inflater)

        fragmentResultBinding.run {

            //메모리에 저장된 모든 메모 객체 갱신
            mainActivity.memoList = DAO.selectAllData(mainActivity)
            //선택한 메모 객체를 가져옴
            val memo = DAO.selectData(mainActivity, mainActivity.currentIdx)

            //메모 출력
            fragmentResultBinding.textViewResultTitle.text = memo.title
            fragmentResultBinding.textViewResultContent.text = memo.content
            fragmentResultBinding.textViewResultDate.text = memo.date

            toolbarResult.run {
                title = "메모 읽기"
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.result_menu)
                //백버튼 설정
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.RESULT_FRAGMENT)
                }

                setOnMenuItemClickListener {
                    when(it.itemId){
                        //수정 프래그먼트로 전환
                        R.id.menuItemUpdate->{
                            mainActivity.replaceFragment(MainActivity.UPDATE_FRAGMENT, true, true)
                        }
                        //삭제 다이얼로그 출력
                        R.id.menuItemDelete->{
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("메모 삭제")
                                .setMessage("${DAO.selectData(mainActivity, mainActivity.currentIdx).title}를 삭제하시겠습니까?")
                                .setPositiveButton("삭제"){ dialogInterface: DialogInterface, i: Int ->
                                    //데이터 삭제
                                    DAO.deleteData(mainActivity, mainActivity.currentIdx)
                                    //다이얼로그 소멸
                                    dialogInterface.dismiss()
                                    mainActivity.removeFragment(MainActivity.RESULT_FRAGMENT)
                                }
                                .setNegativeButton("취소", null)
                            builder.show()
                        }
                    }
                    false
                }
            }
        }
        return fragmentResultBinding.root
    }

}