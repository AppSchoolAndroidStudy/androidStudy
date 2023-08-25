package com.test.retrofitex.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.retrofitex.R
import com.test.retrofitex.data.model.APOD
import com.test.retrofitex.databinding.ActivityMainBinding
import com.test.retrofitex.repository.NasaRepository
import com.test.retrofitex.server.RetrofitClient
import com.test.retrofitex.viewmodel.NasaViewModel
import com.test.retrofitex.viewmodel.NasaViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var nasaViewModel : NasaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val retrofitClient = RetrofitClient()
        val nasaRepository = NasaRepository(retrofitClient)

        nasaViewModel = ViewModelProvider(this, NasaViewModelFactory(nasaRepository))[NasaViewModel::class.java]
        nasaViewModel.run {
            apodList.observe(this@MainActivity){
                activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
            }
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


