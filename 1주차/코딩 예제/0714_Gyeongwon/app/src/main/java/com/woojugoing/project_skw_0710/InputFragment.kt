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
import com.woojugoing.project_skw_0710.databinding.FragmentInputBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InputFragment : Fragment() {

    private lateinit var fragmentAddBinding: FragmentInputBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        fragmentAddBinding = FragmentInputBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        fragmentAddBinding.run {
            toolbarInput.run {
                title = "메모추가"
                setTitleTextColor(Color.WHITE)

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter = BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?. setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                }
                // Back Button을 누르면 동작하는 Listener
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.INPUT_FRAGMENT)
                }

                inflateMenu(R.menu.menu_add)
                setOnMenuItemClickListener {

                    val title = editTextAddTitle.text.toString()
                    val content = editTextAddContent.text.toString()
                    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                    val obj = MemoClass(0, title, content, date)
                    DAO.insertData(mainActivity, obj)
                    mainActivity.replaceFragment(MainActivity.MAIN_FRAGMENT, addToBackStack = true, animate = true)
                    mainActivity.removeFragment(MainActivity.INPUT_FRAGMENT)
                    false
                }
            }
        }
        return fragmentAddBinding.root
    }

}