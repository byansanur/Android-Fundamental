package com.byandev.consumersubmission

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var adapterConsumer: AdapterConsumer

    companion object {
        private const val TAG = "MainActivity consumer"
        private const val AUTHORITY = "com.byandev.submission2uiux"
        private const val TABLE_NAME = "tb_favorite"
        const val LOADER = 1
        val URI_FAV: Uri = Uri.parse("content://${AUTHORITY}/${TABLE_NAME}")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapterConsumer = AdapterConsumer(this)

        rv_list_consumer_app.setHasFixedSize(true)
        rv_list_consumer_app.layoutManager = LinearLayoutManager(this)
        rv_list_consumer_app.adapter = adapterConsumer


        supportLoaderManager.initLoader(LOADER, null, mLoaderCallbacks)

        Log.d(TAG, "onCreate: $URI_FAV")
    }


    private val mLoaderCallbacks: LoaderManager.LoaderCallbacks<Cursor?> =
        object : LoaderManager.LoaderCallbacks<Cursor?> {
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor?> {
                when (id) {
                    LOADER -> {
                        return CursorLoader(
                            applicationContext,
                            URI_FAV,
                            null,
                            null,
                            null,
                            null
                        )
                    }
                    else -> throw IllegalArgumentException()
                }
            }

            override fun onLoadFinished(loader: Loader<Cursor?>, data: Cursor?) {
                if (loader.id == LOADER) {
                    adapterConsumer.setData(data)
//                    pbLoadRv.visibility = View.GONE
                    Log.d(TAG, "onLoadFinished: ${data.toString()}")
                }
            }

            override fun onLoaderReset(loader: Loader<Cursor?>) {
                if (loader.id == LOADER) {
                    adapterConsumer.setData(null)
                }
            }

        }
}