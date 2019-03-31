package io.imhungry.firebase.repository

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class FirebaseBaseCollectionRepository(
    protected val collection: CollectionReference
) {
    protected suspend inline fun <reified T : Any> performSingleQuery(task: Task<QuerySnapshot>): T? =
        withContext(Dispatchers.IO) {
            val singleResult = Tasks.await(task).firstOrNull()
            singleResult?.toObject(T::class.java)
        }

    protected suspend inline fun <reified T : Any> performListQuery(task: Task<QuerySnapshot>): List<T> =
        withContext(Dispatchers.IO) {
            val results = Tasks.await(task)
            results?.toObjects(T::class.java) ?: emptyList()
        }
}