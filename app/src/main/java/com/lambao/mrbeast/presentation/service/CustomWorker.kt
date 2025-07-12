package com.lambao.mrbeast.presentation.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.lambao.base.utils.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class CustomWorker(appContext: Context, private val workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    companion object {
        const val ARG_NUMBER = "number"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
//        repeat(4) {
//            delay(1000)
//            log("LAMNB", "count: $it")
//        }
        val data = Data.Builder()
            .putInt(ARG_NUMBER, workerParams.inputData.getInt(ARG_NUMBER, 0) + 2)
            .build()
        Result.success(data)
    }
}