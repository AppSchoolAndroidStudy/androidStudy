package com.test.retrofitex.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.retrofitex.databinding.ActivityMainBinding
import com.test.retrofitex.viewmodel.NasaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    private val nasaViewModel : NasaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        nasaViewModel.apodList.observe(this@MainActivity){
            activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
        }

        activityMainBinding.run {
            toolbarMain.run {
                title = "Astronomy Picture of the Day"
            }
            recyclerViewMain.run {
                nasaViewModel.getAPOD()

                adapter = Adapter(nasaViewModel, this@MainActivity) {position->
                    val newIntent = Intent(this@MainActivity, ResultActivity::class.java)
                    newIntent.putExtra("date", nasaViewModel.apodList.value?.get(position)?.date)
                    newIntent.putExtra("explanation", nasaViewModel.apodList.value?.get(position)?.explanation)
                    newIntent.putExtra("hdurl", nasaViewModel.apodList.value?.get(position)?.hdurl)
                    newIntent.putExtra("title", nasaViewModel.apodList.value?.get(position)?.title)
                    startActivity(newIntent)
                }
                layoutManager = LinearLayoutManager(this@MainActivity)
                addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            }
        }
    }
}


