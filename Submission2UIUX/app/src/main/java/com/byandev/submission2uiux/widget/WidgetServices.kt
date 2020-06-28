package com.byandev.submission2uiux.widget

import android.content.Intent
import android.widget.RemoteViewsService

class WidgetServices : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return RemoteViewFactory(this.applicationContext)
    }

}