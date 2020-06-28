package com.byandev.submission2uiux.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.byandev.submission2uiux.R


/**
 * Implementation of App Widget functionality.
 */
class FavoriteWidget : AppWidgetProvider() {

    companion object {
        private const val TOAST_ACTION = "com.byandev.submission2uiux.TOAST_ACTION"
        const val EXTRA_ITEM = "com.byandev.submission2uiux.EXTRA_ITEM"

        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val intent = Intent(context, WidgetServices::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))


            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.favorite_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)

            val toastIntent = Intent(context, FavoriteWidget::class.java)
            toastIntent.action = TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val toastPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != null) {
            if (intent.action == TOAST_ACTION) {

                val appWidgetManager = AppWidgetManager.getInstance(context)
                val thisWidget = context?.let { ComponentName(it, FavoriteWidget::class.java) }
                val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)

            } else if (intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {

                val appWidgetManager = AppWidgetManager.getInstance(context)
                val thisWidget = ComponentName(context!!, FavoriteWidget::class.java)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)

            }
        }
        super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }


}

