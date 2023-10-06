package com.example.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customview.databinding.ActivityMainBinding
import com.example.customview.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    lateinit var itemTouchHelper: ItemTouchHelper

    val nameList = mutableListOf<String>(
        "김소윤", "강현구", "공혜연", "서경원", "이상준", "이지은"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {
            recyclerView.run {
                adapter = RecyclerAdapterClass()
                layoutManager = LinearLayoutManager(this@MainActivity)

                val itemTouchHelperCallback = ItemTouchHelperCallback(object : ItemTouchHelperListener {
                    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
                        val name = nameList[fromPosition]

                        nameList.removeAt(fromPosition)
                        nameList.add(toPosition, name)

                        recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)

                        return true
                    }

                    override fun onItemSwipe(position: Int) {
                        nameList.removeAt(position)
                        recyclerView.adapter?.notifyItemRemoved(position)
                    }
                })
                itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
                itemTouchHelper.attachToRecyclerView(recyclerView)
            }

        }
    }

    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>(){

        inner class ViewHolderClass (rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){

            val textView : TextView

            init {
                textView = TextView(this@MainActivity)
                textView.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                rowBinding.root.addView(textView)

                val layoutParams = textView.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowBinding = RowBinding.inflate(layoutInflater)

            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return ViewHolderClass(rowBinding)
        }

        override fun getItemCount(): Int {
            return nameList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textView.text = nameList[position]
        }
    }
}