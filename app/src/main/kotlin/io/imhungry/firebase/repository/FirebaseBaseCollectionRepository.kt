package io.imhungry.firebase.repository

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

abstract class FirebaseBaseCollectionRepository<T : Any>(
    protected val collection: CollectionReference,
    private val klass: KClass<out T>
) {
    protected suspend fun performSingleQuery(task: Task<QuerySnapshot>): T? =
        withContext(Dispatchers.IO) {
            val singleResult = Tasks.await(task).firstOrNull()
            singleResult?.toObject(klass.java)
        }

    protected suspend fun performListQuery(task: Task<QuerySnapshot>): List<T> =
        withContext(Dispatchers.IO) {
            val results = Tasks.await(task)
            results?.toObjects(klass.java) ?: emptyList()
        }

    protected suspend fun performInsertion(value: T): DocumentReference = withContext(Dispatchers.IO) {
        Tasks.await(collection.add(value))
    }

    protected suspend fun performUpdate(documentId: String, field: String, value: Any) {
        withContext(Dispatchers.IO) {
            Tasks.await(collection.document(documentId).update(field, value))
        }
    }

    protected suspend fun performDeletion(documentId: String) {
        withContext(Dispatchers.IO) {
            Tasks.await(collection.document(documentId).delete())
        }
    }
}