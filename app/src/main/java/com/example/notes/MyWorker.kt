package com.example.notes

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.notes.model.NoteRepository
import com.example.notes.model.NoteRepositoryImpl

class MyWorker(
    context: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    private val repository: NoteRepository = NoteRepositoryImpl(applicationContext)

    override suspend fun doWork(): Result {
        Log.d(LOG_FILTER, "Сохранено заметок: ${repository.getNoteList().size}")
        return Result.success()
    }

    companion object {

        const val WORK_NAME = "Worker"

        private const val LOG_FILTER = "FakeBackup"
    }
}