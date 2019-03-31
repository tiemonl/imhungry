package io.imhungry.firebase.repository

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

abstract class FirebaseBaseCollectionRepository<T : Any>(
    protected val collection: CollectionReference,
    private val klass: KClass<out T>
) {

    protected suspend fun <R> performTaskAwait(task: Task<R>): R = Tasks.await(task)

    protected suspend fun performSingleQuery(query: Query): T? =
        withContext(Dispatchers.IO) {
            val singleResult = Tasks.await(query.limit(1).get()).firstOrNull()
            singleResult?.toObject(klass.java)
        }

    protected suspend fun performListQuery(query: Query): List<T> =
        withContext(Dispatchers.IO) {
            val results = Tasks.await(query.get())
            results?.toObjects(klass.java) ?: emptyList()
        }

    protected suspend fun performInsertion(value: T, reference: String? = null): DocumentReference =
        withContext(Dispatchers.IO) {
            if (reference != null) {
                collection.document(reference).also {
                    Tasks.await(it.set(value))
                }
            } else {
                Tasks.await(collection.add(value))
            }
        }

    protected suspend fun performFieldUpdate(documentId: String, field: String, value: Any) {
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