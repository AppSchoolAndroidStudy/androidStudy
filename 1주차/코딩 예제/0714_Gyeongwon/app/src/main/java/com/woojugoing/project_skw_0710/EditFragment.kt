package com.woojugoing.project_skw_0710


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
import com.woojugoing.project_skw_0710.databinding.FragmentEditBinding
import com.woojugoing.project_skw_0710.databinding.FragmentInputBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditFragment : Fragment() {

    private lateinit var fragmentEditBinding: FragmentEditBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var memo: MemoClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        fragmentEditBinding = FragmentEditBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        fragmentEditBinding.run {
            toolbarEdit.run {
                title = "메모 수정"
                setTitleTextColor(Color.WHITE)

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter = BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?. setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                }
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.EDIT_FRAGMENT)
                }

                memo = DAO.selectData(mainActivity, DAO.selectAllData(mainActivity).size - mainActivity.rowPosition)
                editTextEditTitle.setText(memo.title)
                editTextEditContent.setText(memo.content)

                inflateMenu(R.menu.menu_edit)
                setOnMenuItemClickListener {
                    var editTitle = editTextEditTitle.text.toString()
                    var editContent = editTextEditContent.text.toString()

                    memo.title = editTitle
                    memo.content = editContent

                    DAO.updateData(mainActivity, memo)
                    mainActivity.replaceFragment(MainActivity.RESULT_FRAGMENT, true, true)
                    mainActivity.removeFragment(MainActivity.EDIT_FRAGMENT)
                    true
                }
            }

        }
        return fragmentEditBinding.root

    }

}