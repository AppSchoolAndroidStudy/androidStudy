package com.test.android74_memoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android74_memoapp.databinding.FragmentMemoAddBinding
import com.test.android74_memoapp.databinding.FragmentMemoReadBinding


class MemoReadFragment : Fragment() {

    lateinit var fragmentMemoReadBinding: FragmentMemoReadBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentMemoReadBinding = FragmentMemoReadBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        // 현재 메모 데이터를 가져온다.
        val memoIdx = arguments?.getInt("memo_idx")
        val memoClass = MemoDAO.selectOne(mainActivity, memoIdx!!)

        fragmentMemoReadBinding.run{

            // 메모 내용을 채워준다.
            textViewMemoSubject.text = memoClass?.memoSubject
            textViewMemoDate.text = memoClass?.memoDate
            textViewMemoText.text = memoClass?.memoText

            toolbarMemoRead.run{
                title = "메모 읽기"
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MEMO_READ_FRAGMENT)
                }
                inflateMenu(R.menu.memo_read_menu)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.memo_read_menu_item1 -> {
                            // 메모 인덱스 번호를 전달한다.
                            val newBundle = Bundle()
                            newBundle.putInt("memo_idx", memoIdx)
                            mainActivity.replaceFragment(MainActivity.MEMO_MODIFY_FRAGMENT, true, true, newBundle)
                        }
                        R.id.memo_read_menu_item2 -> {
                            // 현재 메모를 삭제한다
                            MemoDAO.delete(mainActivity, memoIdx)
                            mainActivity.removeFragment(MainActivity.MEMO_READ_FRAGMENT)
                        }
                    }
                    false
                }
            }
        }

        return fragmentMemoReadBinding.root
    }

}