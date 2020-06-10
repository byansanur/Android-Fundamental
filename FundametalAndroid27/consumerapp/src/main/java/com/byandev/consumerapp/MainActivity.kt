package com.byandev.consumerapp

import android.annotation.SuppressLint
import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.byandev.consumerapp.SQLite.DatabaseContract.NoteColumns.Companion.CONTENT_URI
import com.byandev.consumerapp.entity.Note
import com.byandev.consumerapp.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/*
    Catatan :
    Jika terjadi error dan kemudian Anda mengubah nama di bagian DatabaseContract
    atau query di DatabaseHelper pastikan Anda meng-uninstall dulu apk yang lama/meng-clear cache
    terlebih dahulu. Karena proses create table hanya dilakukan sekali saat di awal.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NoteAdapter
//    private lateinit var noteHelper: NoteHelper

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE" // orientasi layar agar konsisten
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Consumer App"

        rv_notes.layoutManager = LinearLayoutManager(this)
        rv_notes.setHasFixedSize(true)
        adapter = NoteAdapter(this)
        rv_notes.adapter = adapter

        fab_add.setOnClickListener { noteAdd() }

        /*
        Aturan utama dalam penggunaan dan akses database SQLite adalah membuat instance
        dan membuka koneksi pada metode onCreate():
         */
//        noteHelper = NoteHelper.getInstance(applicationContext)
//        noteHelper.open()

        // materi content resolver
        val handlerThread = HandlerThread("DaraObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadNotesAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)



        if (savedInstanceState == null) {
            // proses ambil data
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Note>(EXTRA_STATE)
            if (list != null) {
                adapter.listNotes = list
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listNotes)
    }

    private fun loadNotesAsync() {
        /*
            Fungsi ini digunakan untuk load data dari tabel dan dan kemudian menampilkannya
            ke dalam list secara asynchronous dengan menggunakan Background process seperti berikut.
         */
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
//                val cursor = noteHelper.queryAll()
                // materi content resolver
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            progressbar.visibility = View.INVISIBLE
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                adapter.listNotes = notes
            } else {
                adapter.listNotes = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }

    private fun noteAdd() {
        val intent = Intent(this, NoteAddUpdateActivity::class.java)
        startActivityForResult(intent,
            NoteAddUpdateActivity.REQUEST_ADD
        )
    }

    // Lakukan aksi setelah menerima nilai balik dari semua aksi yang dilakukan di NoteAddUpdateActivity.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            when(requestCode) {
                // Akan dipanggil jika request codenya ADD
                NoteAddUpdateActivity.REQUEST_ADD -> {
                    if (resultCode == NoteAddUpdateActivity.RESULT_ADD){
                        val note = data.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE)
                        adapter.addItem(note)
                        rv_notes.smoothScrollToPosition(adapter.itemCount - 1)
                        showSnackbarMessage("Satu item berhasil ditambahkan")
                    }
                }
                // Update dan Delete memiliki request code sama akan tetapi result codenya berbeda
                NoteAddUpdateActivity.REQUEST_UPDATE ->
                    when(resultCode) {
                        /*
                           Akan dipanggil jika result codenya  UPDATE
                           Semua data di load kembali dari awal
                         */
                        NoteAddUpdateActivity.RESULT_UPDATE -> {
                            val note = data.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE)
                            val position = data.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0)
                            adapter.updateItem(position, note)
                            rv_notes.smoothScrollToPosition(position)
                            showSnackbarMessage("Satu item berhasil diubah")
                        }
                        /*
                           Akan dipanggil jika result codenya DELETE
                           Delete akan menghapus data dari list berdasarkan dari position
                         */
                        NoteAddUpdateActivity.RESULT_DELETE -> {
                            val position = data.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0)
                            adapter.removeItem(position)
                            showSnackbarMessage("Satu item berhasil dihapus")
                        }
                    }
            }
        }
        /*
            Setiap aksi yang dilakukan pada NoteAddUpdateActivity akan berdampak pada MainActivity
            baik itu untuk penambahan, pembaharuan atau penghapusan.
            Metode onActivityResult() akan melakukan penerimaan data dari intent yang dikirimkan
            dan diseleksi berdasarkan jenis requestCode dan resultCode-nya.
         */
    }

    @SuppressLint("WrongConstant")
    private fun showSnackbarMessage(s: String) {
        Snackbar.make(rv_notes, s, Snackbar.LENGTH_SHORT).show()
    }
}