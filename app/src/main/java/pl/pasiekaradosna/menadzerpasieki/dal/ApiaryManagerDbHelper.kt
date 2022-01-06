package pl.pasiekaradosna.menadzerpasieki.dal

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import pl.pasiekaradosna.menadzerpasieki.dal.Settings.TABLE_APIARIES
import pl.pasiekaradosna.menadzerpasieki.dal.Settings.TAG


class ApiaryManagerDbHelper(context: Context) :
    SQLiteOpenHelper(context, Settings.DATABASE_NAME, null, Settings.DATABASE_VERSION) {
    private var sQLiteDatabase: SQLiteDatabase? = null
    override fun onCreate(db: SQLiteDatabase?) {
        sQLiteDatabase = db
        createTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        dropTables()
        onCreate(db)
    }

    fun createTable() {
        createTable(this.writableDatabase)
    }

    private fun createTable(db: SQLiteDatabase?) {
        sQLiteDatabase = db
        try {
            val sql = """CREATE TABLE Apiaries (
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                date_of_creation TEXT,
                location TEXT
            );"""
            Log.d(TAG, "sql executed: $sql")

            sQLiteDatabase?.execSQL(sql)

            Log.d(TAG, sQLiteDatabase.toString())

        } catch (ex: Exception) {
            Log.e(TAG, "onCreate", ex)
        }
    }

    fun dropTables() {
        sQLiteDatabase = this.writableDatabase
        sQLiteDatabase?.execSQL("""drop table $TABLE_APIARIES""")
    }

    fun insertValue(field: String) {
        val values = ContentValues()
        values.put("field", field)
        try {
            this.writableDatabase.insert(TABLE_APIARIES, null, values)
            Log.i(TAG, "Inserted Values")
        } catch (ex: Exception) {
            Log.e(TAG, "Error inserting", ex)
        }
    }

}