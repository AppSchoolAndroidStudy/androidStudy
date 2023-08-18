package ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.color.utilities.MaterialDynamicColors.onError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.APIObject
import repository.CampingService

class MarkerInfoModel: ViewModel() {
    val campingService= CampingService.getCampingService()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }
    val campSites=MutableLiveData<APIObject>()
    val campsiteLoadError=MutableLiveData<String>()
    val loading=MutableLiveData<Boolean>()

    fun refresh(){
        fetchCampSites()
    }
    fun fetchCampSites(){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = campingService.getCampSiteList(1,10,"JSON","hojdaAj28uKSvkkT5O01VLlmsMbVDxwWfk5norTQMAdtVK6+18evQogPO5ix63vdVPoPG6hGVUGv2iZ3nKzJvA==").execute()
            try {
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        campSites.value = response.body()
                        campsiteLoadError.value = null
                        loading.value = false
                    } else {
                        onError("Error: ${response.code()}")
                        Log.d("marker","레트로핏오류\n")
                    }
                }
            } catch (t: Throwable) {
                withContext(Dispatchers.Main) {
                    Log.d("CoroutineException", "오류 내용 : ${t.message}\n")
                    onError("Exception: ${t.localizedMessage}")
                }
            }
        }
    }

    fun onError(message: String){
        campsiteLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}