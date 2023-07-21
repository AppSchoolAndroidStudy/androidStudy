package com.woojugoing.project_skw_0710

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.woojugoing.project_skw_0710.databinding.FragmentMainBinding
import com.woojugoing.project_skw_0710.databinding.RowMainBinding

class MainFragment : Fragment() {

    private lateinit var fragmentMainBinding: FragmentMainBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainActivity = activity as MainActivity
        fragmentMainBinding = FragmentMainBinding.inflate(inflater)

        fragmentMainBinding.run {
            toolbarMain.run {
                title = "Memo App"
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.menu_main)
                setOnMenuItemClickListener {
                    mainActivity.replaceFragment(MainActivity.INPUT_FRAGMENT, addToBackStack = true, animate = true)
                    false
                }
            }
            recyclerViewMain.run {
                adapter = MainRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity,DividerItemDecoration.VERTICAL))
            }
        }
        return fragmentMainBinding.root
    }

    inner class MainRecyclerViewAdapter :
        RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolderClass>() {

        inner class MainViewHolderClass(rowMainBinding: RowMainBinding) :
            RecyclerView.ViewHolder(rowMainBinding.root) {
            var rowDate: TextView
            var rowTitle: TextView

            init {
                rowDate = rowMainBinding.textViewRowDate
                rowTitle = rowMainBinding.textViewRowTitle
                rowMainBinding.root.setOnClickListener {
                    mainActivity.rowPosition = adapterPosition
                    mainActivity.replaceFragment(MainActivity.RESULT_FRAGMENT, addToBackStack = true, animate = true)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolderClass {
            val rowBinding = RowMainBinding.inflate(layoutInflater)
            val mainViewHolderClass = MainViewHolderClass(rowBinding)

            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return mainViewHolderClass
        }

        override fun getItemCount(): Int {
            val idx = DAO.selectAllData(mainActivity)
            return idx.size
        }

        override fun onBindViewHolder(holder: MainViewHolderClass, position: Int) {
            val idx = DAO.selectAllData(mainActivity)
            val obj = DAO.selectData(mainActivity, idx.size - position)
            if (obj == null) {
                holder.itemView.visibility = View.GONE
            } else {
                holder.itemView.visibility = View.VISIBLE
                holder.rowDate.text = obj.date
                holder.rowTitle.text = obj.title
            }
        }

    }

    override fun onResume() {
        super.onResume()
        fragmentMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
    }
}
