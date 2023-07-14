package com.test.a0713_jieun

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.a0713_jieun.databinding.FragmentStudentMainBinding
import com.test.a0713_jieun.databinding.RowBinding

class StudentMainFragment : Fragment() {

    lateinit var fragmentStudentMainBinding: FragmentStudentMainBinding
    lateinit var mainActivity: MainActivity

    var studentList = mutableListOf<StudentData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentStudentMainBinding = FragmentStudentMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        val schoolName = arguments?.getString("schoolName")

        studentList = StudentDAO.selectAll(mainActivity, schoolName!!)

        fragmentStudentMainBinding.run {
            toolbarStudent.run {
                title = schoolName
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.STUDENT_MAIN_FRAGMENT)
                }
            }
            recyclerViewStudent.run {
                adapter = StudentRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }
        }

        return fragmentStudentMainBinding.root
    }

    inner class StudentRecyclerViewAdapter: RecyclerView.Adapter<StudentRecyclerViewAdapter.StudentViewHolderClass>(){

        inner class StudentViewHolderClass(rowBinding: RowBinding): RecyclerView.ViewHolder(rowBinding.root){
            var textViewRowName : TextView
            init {
                textViewRowName = rowBinding.textViewRowName

                rowBinding.root.setOnClickListener {
                    val selectedIdx = studentList[adapterPosition].idx
                    val newBundle = Bundle()
                    newBundle.putInt("selectedIdx", selectedIdx)
                    mainActivity.replaceFragment(MainActivity.STUDENT_INFO_FRAGMENT, true, true, newBundle)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolderClass {
            val rowBinding = RowBinding.inflate(layoutInflater)
            val studentViewHolderClass = StudentViewHolderClass(rowBinding)

            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            return studentViewHolderClass
        }

        override fun getItemCount(): Int {
            return studentList.size
        }

        override fun onBindViewHolder(holder: StudentViewHolderClass, position: Int) {
            holder.textViewRowName.text = studentList[position].studentName
        }
    }
}