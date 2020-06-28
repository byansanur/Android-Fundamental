package com.byandev.submission2uiux.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.dao.UserDatabase
import com.byandev.submission2uiux.data.model.Item
import com.byandev.submission2uiux.data.repo.SearchListRepository


class RemoteViewFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val widgetItem = ArrayList<Item>()

    override fun onCreate() {
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        if (widgetItem.size != 0) {
            widgetItem.clear()
        }

        val favorites: List<Item>
        val userDb: UserDatabase = UserDatabase.invoke(context)
        val repository = SearchListRepository(userDb)
        favorites = repository.getAllWidgetFav()
        for (data in favorites) getFav(data.id)
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_item)
        try {
            val bitmap: Bitmap = Glide.with(context)
                .asBitmap()
                .load(widgetItem[position].avatar_url)
                .submit(512, 512)
                .get()
            rv.setImageViewBitmap(R.id.img_widget, bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        rv.setTextViewText(R.id.tv_widget, widgetItem[position].login)

        val extras = Bundle()
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.img_widget, fillInIntent)
        return rv
    }

    override fun getCount(): Int = widgetItem.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
    }

    private fun getFav(id: Int) {
        val userDb = UserDatabase.invoke(context)
        val repos = SearchListRepository(userDb)
        widgetItem.addAll(repos.getWidgetFav(id))
    }

}