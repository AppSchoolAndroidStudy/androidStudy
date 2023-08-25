package com.test.coroutineproject

import ViewModel.MarkerInfoModel
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.test.coroutineproject.databinding.ActivityMainBinding
import com.test.coroutineproject.databinding.DialogBinding
import model.APIObject
import model.Campsite

class MainActivity : AppCompatActivity(),OnMapReadyCallback,Overlay.OnClickListener {
    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var naverMap:NaverMap
    lateinit var markerInfoModel: MarkerInfoModel
    lateinit var campList:MutableList<Campsite>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        markerInfoModel=ViewModelProvider(this@MainActivity)[MarkerInfoModel::class.java]
        markerInfoModel.run {
            campSites.observe(this@MainActivity){
                campList=it.data
            }
            campsiteLoadError.observe(this@MainActivity){
                Log.d("CoroutineException",it+"\n")

            }
        }

        activityMainBinding.run {
            materialToolbar2.run {
                inflateMenu(R.menu.map_toolbar_menu)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.markCamp->{
                            addMaker()
                        }
                    }
                    true
                }
            }
            markerInfoModel.refresh()
        }

        val fm=supportFragmentManager
        val mapFragment=fm.findFragmentById(R.id.fragmentContainerView) as MapFragment?
            ?:MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.fragmentContainerView,it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: NaverMap) {
        naverMap=p0
    }

    fun addMaker(){
        for (info in campList){
            val marker=Marker()
            marker.position= LatLng(info.위도.toDouble(),info.경도.toDouble())
            marker.tag=info
            marker.map=naverMap
            marker.setOnClickListener(this)
        }
    }

    override fun onClick(p0: Overlay): Boolean {
        val dialogBinding=DialogBinding.inflate(layoutInflater)
        if(p0 is Marker){
            val marker=p0.tag as Campsite
            val builder = AlertDialog.Builder(this@MainActivity)
            dialogBinding.markText.text=marker.시설소개
            dialogBinding.markCall.text=marker.전화번호
            dialogBinding.markLink.text=marker.홈페이지
            dialogBinding.markLink.setTextColor(Color.BLUE)
            dialogBinding.markLink.setOnClickListener {
                val intent= Intent(Intent.ACTION_VIEW, Uri.parse(marker.홈페이지))
                startActivity(intent)
            }
            builder.setTitle("이름: ${marker.시설명}")
            builder.setView(dialogBinding.root)
            builder.setPositiveButton("닫기",null)
            builder.show()

            return true
        }
            return false
    }
}