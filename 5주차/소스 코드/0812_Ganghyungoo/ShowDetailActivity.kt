package com.test.mvvmbasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.test.mvvmbasic.databinding.ActivityShowDetailBinding
import com.test.mvvmbasic.vm.ShowItemModel
import com.test.mvvmbasic.vm.TestViewModel

class ShowDetailActivity : AppCompatActivity() {
    lateinit var activityShowDetailBinding: ActivityShowDetailBinding
    lateinit var showItemModel: ShowItemModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityShowDetailBinding= ActivityShowDetailBinding.inflate(layoutInflater)
        setContentView(activityShowDetailBinding.root)

        showItemModel= ViewModelProvider(this)[ShowItemModel::class.java]
        showItemModel.run {
            showObject.observe(this@ShowDetailActivity){
                activityShowDetailBinding.itemIdx.text=showObject.value?.testIdx.toString()
                activityShowDetailBinding.itemName.text=showObject.value?.testData1.toString()
                activityShowDetailBinding.itemText.text=showObject.value?.testData2.toString()
            }
        }

        val idx=intent.getIntExtra("idx",-1)

        showItemModel.showIdx.value=idx
        val idx2=showItemModel.showIdx.value

        if(idx2!=null){
            showItemModel.getShowObject(this,idx2)
        }else{
            Log.d("testt","idx가 설정되지 않았습니다")
        }
    }
}