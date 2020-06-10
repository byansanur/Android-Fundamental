package com.byandev.consumerapp.helper

import android.database.Cursor
import com.byandev.consumerapp.SQLite.DatabaseContract
import com.byandev.consumerapp.entity.Note

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Note> {
        val notesList = ArrayList<Note>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESCRIPTION))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE))
                notesList.add(Note(id, title, description, date))
            }
        }
        return notesList
    }

    // materi content resolver
    fun mapCursorToObject(notesCursor: Cursor?) : Note {
        var note = Note()
        notesCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
            val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE))
            val description = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESCRIPTION))
            val date = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE))
            note = Note(id,title,description,date)
        }
        return note
    }
}

/*
    Catatan :
    Pada NoteHelper proses load data dilakukan dengan eksekusi queryAll() menghasilkan objek Cursor,
    namun pada adapter kita membutuhkan dalam bentuk ArrayList, maka dari itu kita harus mengonversi
    dari Cursor ke Arraylist, di sinilah fungsi kelas pembantu MappingHelper.
    MoveToFirst di sini digunakan untuk memindah cursor ke baris pertama sedangkan
    MoveToNext digunakan untuk memindahkan cursor ke baris selanjutnya.
    Di sini kita ambil datanya satu per satu dan dimasukkan ke dalam ArrayList.

    //////////\\\\\\\\\

    Fungsi apply digunakan untuk menyederhanakan kode yang berulang.
    Misalnya notesCursor.geInt cukup ditulis getInt dan
    notesCursor.getColumnIndexOrThrow cukup ditulis getColumnIndexOrThrow.
    Untuk lengkapnya dapat anda pelajari di kelas Memulai Pemrograman Dengan Kotlin.
 */