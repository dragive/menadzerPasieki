package pl.pasiekaradosna.menadzerpasieki.classes.debug

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import pl.pasiekaradosna.menadzerpasieki.services.Settings
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_USERS_OLD
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG_APP


class TestDbHelper(context: Context) : SQLiteOpenHelper(context,
    Settings.DATABASE_NAME,null,
    Settings.DATABASE_VERSION
) {
    private var sQLiteDatabase: SQLiteDatabase? =null
    override fun onCreate(db: SQLiteDatabase?) {
        sQLiteDatabase = db//dbase = getWriteableDatabase()
        createTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        deleteTable()
        onCreate(db)
    }

    fun createTable(){
        createTable(this.writableDatabase)
    }
    private fun createTable(db: SQLiteDatabase?){
        sQLiteDatabase = db
        try{
            val sql = "Create Table $TABLE_USERS_OLD (" +
                    "id integer primary key autoincrement," +
                    "field varchar(255)" +
                    ")"
            sQLiteDatabase?.execSQL(sql)

            Log.i(TAG_APP,sQLiteDatabase.toString())
            val sqlInsert = "insert into $TABLE_USERS_OLD (" +
                    "field " +
                    ")" +
                    "values (" + "\'123\'" + ")"
            sQLiteDatabase?.execSQL(sqlInsert)
        }catch(ex : Exception){
            Log.e(TAG_APP,"onCreate",ex)
        }
    }
    fun readAllUsers():List<String >?{
        sQLiteDatabase = this.writableDatabase
        val selectQuery = """
            SELECT field FROM $TABLE_USERS_OLD
            """.trimIndent()
        val empList:ArrayList<String > = ArrayList<String >()
        val db = this.readableDatabase
        var cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
//            db.execSQL(selectQuery)
            Log.e(TAG_APP,"rawQuery",e)
            return null
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

    fun deleteTable(){
        sQLiteDatabase = this.writableDatabase
        sQLiteDatabase?.execSQL("""drop table $TABLE_USERS_OLD""")
    }

    fun insertValue(field:String){
        val values = ContentValues()
        values.put("field",field)
        try{
            this.writableDatabase.insert(TABLE_USERS_OLD,null, values)
            Log.i(TAG_APP,"Inserted Values")
        }
        catch (ex:Exception){
            Log.e(TAG_APP,"Error inserting",ex)
        }
    }

}

/*@Override
public void onOpen(SQLiteDatabase db) {
    super.onOpen(db);
    db.disableWriteAheadLogging();
}*/