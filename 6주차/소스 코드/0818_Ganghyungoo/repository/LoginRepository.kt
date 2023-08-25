package repository

import androidx.activity.OnBackPressedCallback
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class LoginRepository {
    companion object{
        fun checkInfoToLogin(userId:String,callback:(Task<DataSnapshot>)->Unit){
            val database=FirebaseDatabase.getInstance()
            val loginRef=database.getReference("User")

            loginRef.orderByChild("id").equalTo(userId).get().addOnCompleteListener(callback)


        }
    }
}