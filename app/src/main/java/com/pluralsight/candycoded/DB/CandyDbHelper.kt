package com.pluralsight.candycoded.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CandyDbHelper(context: Context?) : SQLiteOpenHelper(
    context,
    CandyContract.DB_NAME,
    null,
    CandyContract.DB_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CandyContract.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(CandyContract.SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}