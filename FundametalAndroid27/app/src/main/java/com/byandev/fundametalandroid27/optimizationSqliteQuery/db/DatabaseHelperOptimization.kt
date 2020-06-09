package com.byandev.fundametalandroid27.optimizationSqliteQuery.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.byandev.fundametalandroid27.optimizationSqliteQuery.db.DBContract.MahasiswaColumns.Companion.NAMA
import com.byandev.fundametalandroid27.optimizationSqliteQuery.db.DBContract.MahasiswaColumns.Companion.NIM
import com.byandev.fundametalandroid27.optimizationSqliteQuery.db.DBContract.TABLE_NAME

internal class DatabaseHelperOptimization(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "dbmahasiswa"

        private const val DATABASE_VERSION = 2

        private val CREATE_TABLE_MAHASISWA = "create table $TABLE_NAME ($_ID integer primary key autoincrement, $NAMA text not null, $NIM text not null);"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_MAHASISWA)
    }

    /*
    Method onUpgrade akan di panggil ketika terjadi perbedaan versi
    Gunakan method onUpgrade untuk melakukan proses migrasi data
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
         */
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}