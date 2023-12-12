package com.androidstudy.hilt

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

class MemoRepositoryImp @Inject constructor(
    val fireStore: FirebaseFirestore
) : MemoRepository {

    override fun getMemo(callback: (Task<DocumentSnapshot>) -> Unit) {
        val docRef = fireStore.collection("Memo")
            .document("memo1")

        docRef.get()
            .addOnCompleteListener(callback)
    }

}