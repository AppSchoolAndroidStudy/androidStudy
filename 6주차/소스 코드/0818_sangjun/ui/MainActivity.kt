package likelion.project.giphy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import likelion.project.giphy.databinding.ActivityMainBinding
import likelion.project.giphy.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var activityMainBinding: ActivityMainBinding
    private val mainAdapter by lazy {
        MainAdapter {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("giphy", Json.encodeToString(it))
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initViewModel()
        initView()
        observe()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getGif()
    }

    private fun initView() {
        activityMainBinding.run {
            rvGifs.run {
                adapter = mainAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    private fun observe() {
        activityMainBinding.pgb.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.uiState.collect {
                if (it.isGiphyInitialized) {
                    activityMainBinding.pgb.visibility = View.GONE
                    mainAdapter.submitList(it.giphyList)
                    delay(1000L)
                }
            }
        }
    }
}