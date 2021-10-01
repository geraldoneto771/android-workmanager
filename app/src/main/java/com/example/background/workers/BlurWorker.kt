package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.R
import timber.log.Timber

class BlurWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        val appContext = applicationContext

        makeStatusNotification("Blurring image", appContext)

        return try {
            val picture = BitmapFactory.decodeResource(
                appContext.resources,
                R.drawable.test)

            val output = blurBitmap(picture, appContext)

            // Gravar bitmap em um arquivo temporário
            val outpupUri = writeBitmapToFile(appContext, output)

            makeStatusNotification("Output is $outpupUri", appContext)

            Result.success()
        } catch (thorwable: Throwable){
            Timber.e(thorwable, "Error applying blur")
            Result.failure()
        }
    }
}