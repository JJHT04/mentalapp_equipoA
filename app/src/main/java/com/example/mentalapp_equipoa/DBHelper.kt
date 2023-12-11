package com.example.mentalapp_equipoa

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException

const val inicializado = "false"
private const val SQL_CREATE_ENTRIES1 = "CREATE TABLE IF NOT EXISTS preguntas" +
        "(Id integer PRIMARY KEY, Pregunta text, factor integer, valor integer)"
private const val SQL_CREATE_ENTRIES2 = "CREATE TABLE IF NOT EXISTS resultados"+
        "(id integer PRIMARY KEY AUTOINCREMENT,username text, fecha date, factor1 integer, factor2 integer,factor3 integer, subido integer)"
private const val SQL_CREATE_USERTABLE = "CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INT, gender TEXT)"
private const val SQL_DELETE_ENTRIES1 = "DROP TABLE IF EXISTS preguntas"
private const val SQL_DELETE_ENTRIES2 = "DROP TABLE IF EXISTS resultados"
private const val SQL_DELETE_ENTRIES3 = "DROP TABLE IF EXISTS usuarios"
private const val SQL_DELETE_ENTRIES4 = "DROP TABLE IF EXISTS respuestas"

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES1)
        db.execSQL(SQL_CREATE_ENTRIES2)
        db.execSQL(SQL_CREATE_USERTABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
// This database is only a cache for online data, so its upgrade policy is
// to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES1)
        db.execSQL(SQL_DELETE_ENTRIES2)
        db.execSQL(SQL_DELETE_ENTRIES3)
        db.execSQL(SQL_DELETE_ENTRIES4)
        onCreate(db)
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "p1_preguntas.db"
    }


}
