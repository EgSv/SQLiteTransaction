package ru.startandroid.develop.sqlitetransaction

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

const val LOG_TAG = "myLogs"

class MainActivity : AppCompatActivity() {

    private var dbh: DBHelper? = null
    private var db: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(LOG_TAG, "---onCreate Activity ---")
        dbh = DBHelper(this)
        myActions()
    }

    fun myActions() {
        db = dbh!!.writableDatabase
        val db2 = dbh!!.writableDatabase
        Log.d(LOG_TAG, "db = db2 - " + (db == db2))
        Log.d(LOG_TAG, "db open - " + db!!.isOpen() + ", db2 open - " + db2.isOpen)
        db2.close()
        Log.d(LOG_TAG, "db open - " + db!!.isOpen() + ", db2 open - " + db2.isOpen)
    }

    fun insert(db: SQLiteDatabase?, table: String, value: String) {
        Log.d(LOG_TAG, "Insert in table $table, value = $value")
        val cv = ContentValues()
        cv.put("val", value)
        db?.insert(table, null, cv)
    }

    fun read(db: SQLiteDatabase?, table: String) {
        Log.d(LOG_TAG, "Read table = $table")
        val c = db!!.query(table, null, null, null, null, null, null)
        if (c != null) {
            Log.d(LOG_TAG, "Read count = ${c.count}")
            if (c.moveToFirst()) {
                do {
                    Log.d(LOG_TAG, c.getString(c.getColumnIndexOrThrow("val")))
                } while (c.moveToNext())
            }
            c.close()
        }
    }

    fun delete(db: SQLiteDatabase?, table: String) {
        Log.d(LOG_TAG, "Delete all from table = $table")
        db!!.delete(table, null, null)
    }

    internal inner class DBHelper(context: Context?) :
        SQLiteOpenHelper(context, "mytable", null, 1) {
        override fun onCreate(db: SQLiteDatabase?) {
            Log.d(LOG_TAG, "--- onCreate database ---");

            db?.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "val text"
                    + ");");
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    }
}