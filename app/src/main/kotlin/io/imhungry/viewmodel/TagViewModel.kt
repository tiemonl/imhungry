package io.imhungry.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.imhungry.db.ImhungryDatabase
import io.imhungry.db.ImhungryRepository
import io.imhungry.model.TagEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TagViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ImhungryRepository
    val allTags: LiveData<List<TagEntity>>

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    init {
        val imhungryDao = ImhungryDatabase.createInstance(application).imhungryDao
        repository = ImhungryRepository(imhungryDao)
        allTags = repository.allTags
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun insert(tag: TagEntity) = scope.launch(Dispatchers.IO) {
        repository.insertTag(tag)
    }
}