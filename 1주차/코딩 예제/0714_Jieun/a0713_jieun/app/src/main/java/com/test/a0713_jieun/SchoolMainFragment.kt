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
import com.test.a0713_jieun.databinding.FragmentSchoolMainBinding
import com.test.a0713_jieun.databinding.RowBinding

class SchoolMainFragment : Fragment() {

    lateinit var fragmentSchoolMainBinding: FragmentSchoolMainBinding
    lateinit var mainActivity: MainActivity

    var schoolList = mutableListOf<SchoolData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSchoolMainBinding = FragmentSchoolMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        schoolList = SchoolDAO.selectAll(mainActivity)

//        SchoolDAO.insert(mainActivity, SchoolData(0, "초등학교"))
//        SchoolDAO.insert(mainActivity, SchoolData(0, "중학교"))
//        SchoolDAO.insert(mainActivity, SchoolData(0, "고등학교"))
//
//        StudentDAO.insert(mainActivity, StudentData(0, "이지은", 1, "초등학교"))
//        StudentDAO.insert(mainActivity, StudentData(0, "김지은", 2, "초등학교"))
//        StudentDAO.insert(mainActivity, StudentData(0, "박지은", 3, "중학교"))
//        StudentDAO.insert(mainActivity, StudentData(0, "최지은", 2, "고등학교"))

        fragmentSchoolMainBinding.run {
            recyclerViewSchool.run {
                adapter = SchoolRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }
        }

        return fragmentSchoolMainBinding.root
    }

    inner class SchoolRecyclerViewAdapter : RecyclerView.Adapter<SchoolRecyclerViewAdapter.SchoolViewHolderClass>(){

        inner class SchoolViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            var textViewRowName : TextView
            init {
                textViewRowName = rowBinding.textViewRowName

                rowBinding.root.setOnClickListener {
                    val newBundle = Bundle()
                    newBundle.putString("schoolName", schoolList[adapterPosition].schoolName)
                    mainActivity.replaceFragment(MainActivity.STUDENT_MAIN_FRAGMENT, true, true, newBundle)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolderClass {
            val rowBinding = RowBinding.inflate(layoutInflater)
            val schoolViewHolderClass = SchoolViewHolderClass(rowBinding)

            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            return schoolViewHolderClass
        }

        override fun getItemCount(): Int {
            return schoolList.size
        }

        override fun onBindViewHolder(holder: SchoolViewHolderClass, position: Int) {
            holder.textViewRowName.text = schoolList[position].schoolName
        }
    }

}