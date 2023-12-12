package com.androidstudy.hilt

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

interface MemoRepository {
    fun getMemo(callback: (Task<DocumentSnapshot>) -> Unit)
}

