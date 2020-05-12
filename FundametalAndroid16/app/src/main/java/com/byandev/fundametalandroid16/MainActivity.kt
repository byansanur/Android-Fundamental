package com.byandev.fundametalandroid16

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    companion object {
        // USE kotlin coroutine
        private const val INPUT_STRING = "Hallo ini demo AsyncTask"
        private const val LOG_ASYNC = "DemoAsync"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_status.setText(R.string.status_pre)
        tv_desc.text = INPUT_STRING

        GlobalScope.launch(Dispatchers.IO) {
            // background thread
            val input = INPUT_STRING
            val output: String?
            Log.d(LOG_ASYNC, "status : doInBackground")
            try {
                output = "$input Selamat Belajar!!"
                delay(2000)

                //pindah ke Main thread untuk update UI
                withContext(Dispatchers.Main) {
                    tv_status.setText(R.string.status_post)
                    tv_desc.text = output
                }
            } catch (e: Exception) {
                Log.d(LOG_ASYNC, e.message.toString())
            }
        }

        // pemanggilan class DemoAsync
//        val demoAsync = DemoAsync(this)
//        demoAsync.execute(INPUT_STRING)
    }

    /*
    override fun onPreExecute() {
        tv_status.setText(R.string.status_pre)
        tv_desc.text = INPUT_STRING
    }

    override fun onPostExecute(text: String) {
        tv_status.setText(R.string.status_post)
        tv_desc.text = text
    }
     */



    /*
    kelas DemoAsync yang mana kelas tersebut berfungsi untuk mengelola data secara asynchronous.
    Setelah membuat kelas DemoAsync di dalam MainActivity, kita akan membuat kelas tersebut mewarisi kelas AsyncTask

    private class DemoAsync(val myListener: MyAsyncCallback) : AsyncTask<String, Void, String>() {
        companion object {
            private val LOG_ASYNC = "DemoAsync"
        }
        private val myListeners: WeakReference<MyAsyncCallback>
        init {
            this.myListeners = WeakReference(myListener)
        }


        /*
        Setelah itu, Anda bisa menambahkan metode-metode di dalam kelas DemoAsync
        untuk persiapan dan ketika proses sudah berhasil.
         */
        override fun onPreExecute() {
            super.onPreExecute()
            Log.d(LOG_ASYNC, "status : onPreExecute")
            val myListener = myListeners.get()
            myListener?.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String {
            Log.d(LOG_ASYNC, "status : doInBackground")

            var output: String? = null

            try {
                val input = params[0]
                output = "$input Selamat belajar"
                Thread.sleep(2000)
            } catch (e: Exception) {
                Log.d(LOG_ASYNC, e.message)
            }
            return output.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d(LOG_ASYNC, "status : onPostExecute")
            val myListener = this.myListeners.get()
            myListener?.onPostExecute(result!!)
        }


    }
    */

}

/*
internal interface MyAsyncCallback {
    fun onPreExecute()
    fun onPostExecute(text: String)
}
 */
