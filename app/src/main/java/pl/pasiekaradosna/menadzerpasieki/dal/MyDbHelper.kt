package pl.pasiekaradosna.menadzerpasieki.dal

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteException
import android.widget.TableLayout
import pl.pasiekaradosna.menadzerpasieki.classes.Element


class MyDbHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "main.db"
        private val TABLE_USERS = "USERS"
        private val DEBUG_TAG = "SqLiteTodoManager"
    }

    private val DEBUG_TAG = "SqLiteTodoManager"


    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "Create Table $TABLE_USERS (" +
                "id integer primary key autoincrement," +
                "field varchar(255)" +
                ")"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    fun readAllUsers():List<String >{
        val selectQuery = "SELECT * FROM $TABLE_USERS"
        val empList:ArrayList<String > = ArrayList<String >()
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        with(cursor) {
            while (moveToNext()) {
                val itemId = getString(getColumnIndexOrThrow("field"))
                empList.add(itemId)
            }
        }
        cursor.close()
        return empList
    }

    fun deleteTable(db: SQLiteDatabase?){
        db?.execSQL("drop table $TABLE_USERS")
    }

}