package repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import model.User

class SignupRepository {
    companion object {
        //회원가입 인덱스 가져올 때
        fun getUserIdx(callBack: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val userIdxRef = database.getReference("UserIdx")
            userIdxRef.get().addOnCompleteListener(callBack)
        }
        fun setPlusedUserIndex(userIdx: Long, callBack: (Task<Void>) -> Unit){
            val database=FirebaseDatabase.getInstance()
            val userIdxRef=database.getReference("UserIdx")
            userIdxRef.get().addOnCompleteListener {
                it.result.ref.setValue(userIdx).addOnCompleteListener(callBack)
            }
        }

        //데이터베이스에 회원가입 정보 입력
        fun addUserData(user: User, callBack: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val userDataRef = database.getReference("User")
            userDataRef.push().setValue(user).addOnCompleteListener(callBack)
        }

    }
}