package com.test.retrofitex.ui

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.test.retrofitex.R
import com.test.retrofitex.databinding.ActivityResultBinding
import com.test.retrofitex.repository.NasaRepository
import com.test.retrofitex.server.RetrofitClient
import com.test.retrofitex.viewmodel.NasaViewModel
import com.test.retrofitex.viewmodel.NasaViewModelFactory

class ResultActivity : AppCompatActivity() {

    lateinit var activityResultBinding: ActivityResultBinding
    lateinit var nasaViewModel: NasaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityResultBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(activityResultBinding.root)

        val resultDate = intent.getStringExtra("date")
        val resultExplanation = intent.getStringExtra("explanation")
        val resultHdurl = intent.getStringExtra("hdurl")
        var resultTitle = intent.getStringExtra("title")

        val retrofitClient = RetrofitClient()
        val nasaRepository = NasaRepository(retrofitClient)

        nasaViewModel = ViewModelProvider(this@ResultActivity, NasaViewModelFactory(nasaRepository))[NasaViewModel::class.java]

        nasaViewModel.run {
            apodDate.observe(this@ResultActivity){
                activityResultBinding.textViewResultDate.text = it
            }
            apodTitle.observe(this@ResultActivity){
                activityResultBinding.textViewResultTitle.text = it
            }
            apodHdurl.observe(this@ResultActivity){
                Glide.with(this@ResultActivity).load(it)
                    .listener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            activityResultBinding.progressBarResult.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            activityResultBinding.progressBarResult.visibility = View.GONE
                            return false
                        }
                    })
                    .into(activityResultBinding.imageViewResult)
            }
            apodExplanation.observe(this@ResultActivity){
                activityResultBinding.textViewResultExplanation.text = it
            }
        }

        nasaViewModel.apodDate.value = resultDate
        nasaViewModel.apodTitle.value = resultTitle
        nasaViewModel.apodExplanation.value = resultExplanation
        nasaViewModel.apodHdurl.value = resultHdurl

        activityResultBinding.run {
            progressBarResult.visibility = View.VISIBLE
            materialToolbarResult.run{
                title = "NASA - APOD"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener { finish() }
            }
        }
    }
}