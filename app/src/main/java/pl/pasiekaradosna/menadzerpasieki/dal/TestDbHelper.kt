package pl.pasiekaradosna.menadzerpasieki.dal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteException
import android.util.Log
import android.widget.Toast
import pl.pasiekaradosna.menadzerpasieki.dal.Settings.TABLE_USERS
import pl.pasiekaradosna.menadzerpasieki.dal.Settings.TAG


class TestDbHelper(context: Context) : SQLiteOpenHelper(context,Settings.DATABASE_NAME,null,Settings.DATABASE_VERSION) {
    private var sQLiteDatabase: SQLiteDatabase? =null
    override fun onCreate(db: SQLiteDatabase?) {
        sQLiteDatabase = db//dbase = getWriteableDatabase()
        createTable()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        deleteTable()
        onCreate(db)
    }

    fun createTable(){
        sQLiteDatabase = this.writableDatabase
        try{
            val sql = "Create Table $TABLE_USERS (" +
                    "id integer primary key autoincrement," +
                    "field varchar(255)" +
                    ")"
            sQLiteDatabase?.execSQL(sql)

            Log.i(TAG,sQLiteDatabase.toString())
            val sqlInsert = "insert into $TABLE_USERS (" +
                    "field " +
                    ")" +
                    "values (" + "\'123\'" + ")"
            sQLiteDatabase?.execSQL(sqlInsert)
        }catch(ex : Exception){
            Log.e(TAG,"onCreate",ex)
        }
    }
    fun readAllUsers():List<String >?{
        sQLiteDatabase = this.writableDatabase
        val selectQuery = """
            SELECT field FROM $TABLE_USERS
            """.trimIndent()
        val empList:ArrayList<String > = ArrayList<String >()
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
//            db.execSQL(selectQuery)
            Log.e(TAG,"rawQuery",e)
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
        sQLiteDatabase?.execSQL("""drop table $TABLE_USERS""")
    }

    fun insertValue(field:String){
        val values = ContentValues()
        values.put("field",field)
        try{
            this.writableDatabase.insert(TABLE_USERS,null, values)
            Log.i(TAG,"Inserted Values")
        }
        catch (ex:Exception){
            Log.e(TAG,"Error inserting",ex)
        }
    }

}

/*@Override
public void onOpen(SQLiteDatabase db) {
    super.onOpen(db);
    db.disableWriteAheadLogging();
}*/