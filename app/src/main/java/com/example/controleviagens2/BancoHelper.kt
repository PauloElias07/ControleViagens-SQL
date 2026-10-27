package com.example.controleviagens2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BancoHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE viagem (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                data TEXT NOT NULL,
                km_inicial REAL NOT NULL,
                km_final REAL NOT NULL,
                litros REAL NOT NULL,
                valor_combustivel REAL NOT NULL,
                pedagio REAL NOT NULL
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS viagem")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "viagens.db"
        private const val DATABASE_VERSION = 1
    }
}
