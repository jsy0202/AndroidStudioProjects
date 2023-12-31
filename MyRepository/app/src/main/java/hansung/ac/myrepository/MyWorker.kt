package hansung.ac.myrepository

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class MyWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val inputData = inputData
        val username = inputData.getString("username") ?: ""

        val repository = MyRepository(applicationContext)
        try {
            repository.refreshData(username)
        } catch (e: Exception) {
            return Result.retry()
        }
        return Result.success()
    }


    companion object {
        const val name = "hansung.ac.repository_pattern.MyWorker"
    }
}