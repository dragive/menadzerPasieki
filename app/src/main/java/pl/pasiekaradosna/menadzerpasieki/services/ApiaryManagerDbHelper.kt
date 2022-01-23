package pl.pasiekaradosna.menadzerpasieki.services

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.apiary.ApiaryData
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.hive.HiveData
import pl.pasiekaradosna.menadzerpasieki.services.Settings.DATABASE_NAME
import pl.pasiekaradosna.menadzerpasieki.services.Settings.DATABASE_VERSION
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_APIARIES
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_HIVES
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_HIVES_AND_TASKS
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_TASK
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_USERS
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG


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
        val sqlList = ArrayList<String>()
        sqlList.add(
            """CREATE TABLE $TABLE_APIARIES (
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                date_of_creation TEXT,
                location TEXT
            );"""
        )

        sqlList.add(
            """
            CREATE TABLE $TABLE_HIVES (
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
        sqlList.add(
            """
            INSERT INTO Apiaries
            (name, date_of_creation, location)
            VALUES( 'nazwa2', 'today1', '4');
            """
        )
        sqlList.add(
            """
            INSERT INTO Apiaries
            (name, date_of_creation, location)
            VALUES('nazwa3', 'today2', '333');"""
        )
        sqlList.add(
            """
            INSERT INTO Apiaries
            ( name, date_of_creation, location)
            VALUES('nazwa4', 'today3', 'there22');"""
        )
        sqlList.add(
            """
            INSERT INTO Apiaries
            ( name, date_of_creation, location)
            VALUES( 'nazwa5', 'today4', '2');"""
        )
        sqlList.add(
            """
            INSERT INTO Apiaries
            ( name, date_of_creation, location)
            VALUES('nazwa6', 'today5', '11');
        """.trimIndent()
        )
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
        try {
            sQLiteDatabase?.execSQL("""drop table $TABLE_APIARIES""")
        } catch (ex: Exception) {
        }
        try {
            sQLiteDatabase?.execSQL("""drop table $TABLE_HIVES""")
        } catch (ex: Exception) {
        }
        try {
            sQLiteDatabase?.execSQL("""drop table $TABLE_HIVES_AND_TASKS""")
        } catch (ex: Exception) {
        }
        try {
            sQLiteDatabase?.execSQL("""drop TABLE $TABLE_USERS""")
        } catch (ex: Exception) {
        }
        try {
            sQLiteDatabase?.execSQL("""drop TABLE $TABLE_TASK""")
        } catch (ex: Exception) {
        }
    }

    fun insertValueTest(field: String) {
        val values = ContentValues()
        values.put("field", field)
        try {
            this.writableDatabase.insert(TABLE_APIARIES, null, values)
            Log.i(TAG, "Inserted Values")
        } catch (ex: Exception) {
            Log.e(TAG, "Error inserting", ex)
        }
    }

    fun createApiary(apiaryData: ApiaryData) {
        try {
            this.writableDatabase.insert(TABLE_APIARIES, null, apiaryData.mapToValues())
            Log.i(TAG, "Inserted Values")
        } catch (ex: Exception) {
            Log.e(TAG, "Error inserting", ex)
        }
    }

    fun getAllApiaries(): List<ApiaryData>? {
        val db = this.readableDatabase

        val selectQuery = """
            SELECT id, name, date_of_creation, location FROM $TABLE_APIARIES
            """.trimIndent()

        val apiaryList = ArrayList<ApiaryData>()

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e(TAG, "Select all apiaries error\n", e)
            return null
        }
        with(cursor) {
            while (moveToNext()) {
                val apiaryName = getString(getColumnIndexOrThrow("name"))
                val apiaryId = getInt(getColumnIndexOrThrow("id"))
                val apiaryDateOfCreation = getString(getColumnIndexOrThrow("date_of_creation"))
                val apiaryLocation = getString(getColumnIndexOrThrow("location"))
                apiaryList.add(
                    ApiaryData(
                        apiaryId,
                        apiaryName,
                        apiaryDateOfCreation,
                        apiaryLocation
                    )
                )
            }
        }
        cursor.close()
        return apiaryList
    }

    fun getApiaryById(id: Int): ApiaryData? {
        val db = this.readableDatabase

        val selectQuery = """
            SELECT id, name, date_of_creation, location FROM $TABLE_APIARIES
             where id = $id
            """.trimIndent()

        lateinit var apiaryResult: ApiaryData

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e(TAG, "Select all apiaries error\n", e)
            return null
        }
        with(cursor) {
            moveToFirst()
            val apiaryName = getString(getColumnIndexOrThrow("name"))
            val apiaryId = getInt(getColumnIndexOrThrow("id"))
            val apiaryDateOfCreation = getString(getColumnIndexOrThrow("date_of_creation"))
            val apiaryLocation = getString(getColumnIndexOrThrow("location"))
            apiaryResult =
                ApiaryData(
                    apiaryId,
                    apiaryName,
                    apiaryDateOfCreation,
                    apiaryLocation
                )


        }
        cursor.close()
        return apiaryResult
    }


    fun getAllHivesByApiaryId(apiaryId: Int): List<HiveData>? {
        val db = this.readableDatabase

        val selectQuery = """
            SELECT             
            id, 
            name,
            apiary_id,
            queen_bread,
            queen_birth_date 
            FROM $TABLE_HIVES
            where apiary_id = $apiaryId 
            """.trimIndent() //FIXME zmiana na BREED zamiast BREAD


        val hiveList = ArrayList<HiveData>()

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e(TAG, "Select all hives error\n", e)
            return null
        }
        with(cursor) {
            while (moveToNext()) {
                val hiveId = getInt(getColumnIndexOrThrow("id"))
                val hiveName = getString(getColumnIndexOrThrow("name"))
                val hiveApiaryId = getInt(getColumnIndexOrThrow("apiary_id"))
                val hiveQueenBreed = getString(getColumnIndexOrThrow("queen_bread"))
                val hiveQueenBirthDate = getString(getColumnIndexOrThrow("queen_birth_date"))
                hiveList.add(
                    HiveData(
                        hiveId,
                        hiveName,
                        hiveApiaryId,
                        hiveQueenBreed,
                        hiveQueenBirthDate
                    )
                )
            }
        }
        cursor.close()
        return hiveList
    }


    fun countAllHives(): Int {
        return countHives()
    }

    fun countAllHivesByApiaryId(apiaryId:Int):Int{
        return countHives(apiaryId)
    }

    private fun countHives(apiaryId: Int = -1): Int {
        val db = this.readableDatabase

        lateinit var selectQuery: String
        if (apiaryId == -1) {
            selectQuery = """
                SELECT             
                count(1) c
                FROM $TABLE_HIVES
                """.trimIndent() //FIXME zmiana w deklaracji na BREED zamiast BREAD
        } else {
            selectQuery = """
                SELECT             
                count(1) c
                FROM $TABLE_HIVES
                where apiary_id = $apiaryId
                """.trimIndent()
        }
        val cursor: Cursor?

        var ret: Int

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e(TAG, "Select all hives error\n", e)
            return -1
        }
        with(cursor) {

            moveToFirst()
            ret = getInt(getColumnIndexOrThrow("c"))

        }
        cursor.close()
        return ret

    }


    fun insertHive(hiveData: HiveData) :Boolean {
        Log.d(TAG,"hives list in DB:"+this.getAllHivesByApiaryId(hiveData.apiaryId))
        try {

            val r = this.writableDatabase.insert(TABLE_HIVES, null, hiveData.mapToValues())
            Log.i(TAG, "Inserted Values?")
            return r!=-1L
        } catch (ex: Exception) {
            Log.e(TAG, "Error inserting", ex)
            return false
        }

    }

}