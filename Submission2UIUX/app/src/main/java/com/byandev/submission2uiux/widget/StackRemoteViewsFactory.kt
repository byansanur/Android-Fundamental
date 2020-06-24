package com.byandev.submission2uiux.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.byandev.submission2uiux.R
import com.byandev.submission2uiux.data.dao.DatabaseContract
import com.byandev.submission2uiux.data.model.Item
import com.squareup.picasso.Picasso
import java.io.IOException


@Suppress("EqualsBetweenInconvertibleTypes")
class StackRemoteViewsFactory(private var mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private val favoriteEntryList: ArrayList<Item> = ArrayList()

//    private var mContext: Context? = null
    private var cursor: Cursor? = null

    override fun onCreate() {
    }



    override fun getLoadingView(): RemoteViews {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {
        getFavourite(mContext)
    }

    private fun getFavourite(mContext: Context) {
        val token: Long = Binder.clearCallingIdentity()
        getResolver(mContext)
        Binder.restoreCallingIdentity(token)
    }

    private fun getResolver(context: Context) {
        cursor = context.contentResolver
            .query(DatabaseContract.UserColumns.CONTENT_URI, null, null, null, null)
        if (cursor != null) {
            while (cursor!!.moveToNext()) {
                favoriteEntryList.add(
                    Item(
                        DatabaseContract.UserColumns.ID.toInt(),
                        DatabaseContract.UserColumns.LOGIN,
                        DatabaseContract.UserColumns.AVATAR_URL,
                        DatabaseContract.UserColumns.TYPE,
                        DatabaseContract.UserColumns.IS_FAVORITE.equals(true)
                    )
                )
            }
        }
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)


        val b: Bitmap
        try {
            b = Picasso.get().load(favoriteEntryList.get(position).avatar_url).get()
            rv.setImageViewBitmap(R.id.imageView, b)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val extras = Bundle()
        extras.putString(FavoriteWidget.EXTRA_ITEM, favoriteEntryList[position].login)
        val fillIntent = Intent()
        fillIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillIntent)

        return rv
    }

    override fun getCount(): Int = favoriteEntryList.size

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {
        cursor?.close()
    }

}