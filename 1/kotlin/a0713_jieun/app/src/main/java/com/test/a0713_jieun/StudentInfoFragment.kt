package com.test.a0713_jieun

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.a0713_jieun.databinding.FragmentStudentInfoBinding

class StudentInfoFragment : Fragment() {

    lateinit var fragmentStudentInfoBinding: FragmentStudentInfoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentStudentInfoBinding = FragmentStudentInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        val selectedIdx = arguments?.getInt("selectedIdx")

        val student : StudentData = StudentDAO.selectOne(mainActivity, selectedIdx!!)

        fragmentStudentInfoBinding.run {
            textViewStdName.text = student.studentName
            textViewStdGrade.text = "${student.studentGrade}학년"
            textViewStdSchoolName.text = student.studentSchoolName

            toolbarStdInfo.run {
                title = "학생 정보"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.STUDENT_INFO_FRAGMENT)
                }
            }
        }

        return fragmentStudentInfoBinding.root
    }

}