package pl.pasiekaradosna.menadzerpasieki.dal

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import pl.pasiekaradosna.menadzerpasieki.dal.Settings.*


class ApiaryManagerDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
        var sqlList = ArrayList<String>()
        sqlList.add(
            """CREATE TABLE Apiaries (
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                date_of_creation TEXT,
                location TEXT
            );"""
        )

        sqlList.add(
            """
            CREATE TABLE $TABLE_HIVE (
            id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            apiary_id INTEGER,
            queen_bread TEXT,
            queen_birth_date TEXT
            );"""
        )


        sqlList.add(
            """CREATE TABLE $TABLE_TASK (
            id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            create_date INTEGER NOT NULL,
            "type" TEXT,
            duration NUMERIC,
            due_date TEXT
            );"""
        )
        sqlList.add(
            """CREATE TABLE $TABLE_HIVES_AND_TASKS (
            id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            complete INTEGER DEFAULT 0,
            hive_id INTEGER NOT NULL,
            task_id INTEGER NOT NULL
            );"""
        )
//        sqlList.add("""""")
//        sqlList.add("""""")
//        sqlList.add("""""")


        try {
            sqlList.forEach { sql ->
                Log.d(TAG, "sql executed: $sql")

                sQLiteDatabase?.execSQL(sql)

                Log.d(TAG, "DONE")
            }

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