package ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInfoViewModel: ViewModel() {
    var userId=MutableLiveData<String>()
    var userPassword=MutableLiveData<String>()
}