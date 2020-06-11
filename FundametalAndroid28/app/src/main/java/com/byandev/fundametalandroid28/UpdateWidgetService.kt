package com.byandev.fundametalandroid28

import android.app.job.JobParameters
import android.app.job.JobService
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.widget.RemoteViews

class UpdateWidgetService : JobService() {
    override fun onStopJob(params: JobParameters?): Boolean {
        val manager = AppWidgetManager.getInstance(this)
        val view = RemoteViews(packageName, R.layout.random_number_widget)
        val theWidget = ComponentName(this, RandomNumberWidget::class.java)
        val lastUpdate = "Random : " + NumberGenerator.generate(100)
        view.setTextViewText(R.id.appwidget_text, lastUpdate)
        manager.updateAppWidget(theWidget, view)
        jobFinished(params, false)
        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        TODO("Not yet implemented")
    }

}