package likelion.project.giphy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import likelion.project.giphy.data.Giphy
import likelion.project.giphy.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    lateinit var activityDetailBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        initView()
    }

    private fun initView() {
        val giphyJson = intent.getStringExtra("giphy")!!
        val giphy = Json.decodeFromString<Giphy>(giphyJson)
        activityDetailBinding.run {
            Glide.with(this@DetailActivity)
                .asGif()
                .load(giphy.images.original.url)
                .into(activityDetailBinding.ivDetailGif)
            tvDetailTitle.text = giphy.title
        }
    }
}