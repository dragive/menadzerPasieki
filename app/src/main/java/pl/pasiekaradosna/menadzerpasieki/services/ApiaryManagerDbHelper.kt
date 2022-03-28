package pl.pasiekaradosna.menadzerpasieki.services

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import pl.pasiekaradosna.menadzerpasieki.classes.data.ApiaryData
import pl.pasiekaradosna.menadzerpasieki.classes.data.HiveData
import pl.pasiekaradosna.menadzerpasieki.classes.data.TaskData
import pl.pasiekaradosna.menadzerpasieki.services.Settings.DATABASE_NAME
import pl.pasiekaradosna.menadzerpasieki.services.Settings.DATABASE_VERSION
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_APIARIES_OLD
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_APIARY
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_HIVE
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_HIVES_AND_TASKS_OLD
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_HIVES_OLD
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_QUEEN
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_QUEEN_BREED
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_TASK
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_TASK_OLD
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_TASK_TYPE
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TABLE_USERS_OLD
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG_APP


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
        val sqlList = getListOfQueries();
        executeQueriesNoEx(sqlList)
    }

    private fun executeQueriesNoEx(sqlList: List<String>) {
        try {
            sqlList.forEach { sql ->
                Log.d(TAG_APP, "sql executed: $sql")

                sQLiteDatabase?.execSQL(sql)

                Log.d(TAG_APP, "DONE")
            }

        } catch (ex: Exception) {
            Log.e(TAG_APP, "onCreate databease", ex)
        }
    }

    private fun getListOfQueries(): List<String> {
        val sqlList = ArrayList<String>()
        sqlList.add(
            """
            CREATE TABLE $TABLE_APIARY (
            	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            	name TEXT NOT NULL,
            	date_of_creation TEXT NOT NULL,
            	date_of_last_modification TEXT,
            	location TEXT,
            	humidity INTEGER,
            	sun_exposure INTEGER,
            	deleted INTEGER
            );
            """.trimIndent()
        )
        sqlList.add(
            """
            CREATE TABLE $TABLE_HIVE (
            	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            	name TEXT NOT NULL,
            	date_of_creation TEXT NOT NULL,
            	date_of_last_modification TEXT,
            	queen_id integer,
            	size_of_hive integer,
            	deleted Integer
            );
            """.trimIndent()
        )
        sqlList.add(
            """
            CREATE TABLE $TABLE_QUEEN_BREED (
            	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            	name TEXT NOT NULL,
            	date_of_creation TEXT NOT NULL,
            	date_of_last_modification TEXT,
            	description BLOB,
            	rating Integer,
            	deleted Integer
            );
            """.trimIndent()
        )
        sqlList.add(
            """
            create table $TABLE_QUEEN (
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            	name TEXT NOT NULL,
            	date_of_creation TEXT NOT NULL,
            	date_of_last_modification TEXT,
            	queen_breed_id integer,
            	birth_date text
            );
            """.trimIndent()
        )
        sqlList.add(
            """
            CREATE TABLE $TABLE_TASK (
            	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            	name TEXT NOT NULL,
            	date_of_creation TEXT NOT NULL,
            	date_of_last_modification TEXT,
            	description BLOB,
            	priority Integer,
            	type_id Integer,
            	status Integer,
            	deadline Text
            );
            """.trimIndent()
        )
        sqlList.add(
            """
            CREATE TABLE $TABLE_TASK_TYPE (
            	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            	name TEXT NOT NULL,
            	date_of_creation TEXT NOT NULL,
            	date_of_last_modification TEXT,
            	description BLOB,
            	predefined Integer
            );
        """.trimIndent()
        )
        return sqlList;
    }

    fun dropTables() {

        sQLiteDatabase = this.writableDatabase
        dropOldTables()


        dropNewTables()
    }

    private fun dropOldTables() {
        val list: List<String> = listOf(
            TABLE_APIARIES_OLD,
            TABLE_HIVES_OLD, TABLE_HIVES_AND_TASKS_OLD,
            TABLE_USERS_OLD,
            TABLE_TASK_OLD,
        )

        for (i in list) {
            try {
                sQLiteDatabase?.execSQL("""drop table $i""")
                Log.d(TAG_APP, "Dropped OLD $i")
            } catch (ex: Exception) {
                Log.e(TAG_APP, "Unable to drop OLD $i")
            }
        }

    }

    private fun dropNewTables() {
        val list: List<String> = listOf(
            TABLE_TASK_TYPE,
            TABLE_TASK,
            TABLE_QUEEN,
            TABLE_QUEEN_BREED,
            TABLE_HIVE,
            TABLE_APIARY
        )

        for (i in list) {
            try {
                sQLiteDatabase?.execSQL("""drop table $i""")
                Log.d(TAG_APP, "Dropped $i")
            } catch (ex: Exception) {
                Log.e(TAG_APP, "Unable to drop $i")
            }
        }

    }

    fun createApiary(apiaryData: ApiaryData) {
        try {
            this.writableDatabase.insert(TABLE_APIARIES_OLD, null, apiaryData.mapToValues())
            Log.i(TAG_APP, "Inserted Values")
        } catch (ex: Exception) {
            Log.e(TAG_APP, "Error inserting", ex)
        }
    }


    fun updateApiary(apiaryData: ApiaryData) {
        try {
            this.writableDatabase.update(
                TABLE_APIARIES_OLD,
                apiaryData.mapToValues(),
                "id=?",
                arrayOf(apiaryData.id.toString())
            )
            Log.i(TAG_APP, "Inserted Values")
        } catch (ex: Exception) {
            Log.e(TAG_APP, "Error inserting", ex)
        }
    }

    fun updateHive(hiveData: HiveData) {
        try {
            this.writableDatabase.update(
                TABLE_HIVES_OLD,
                hiveData.mapToValues(),
                "id=?",
                arrayOf(hiveData.id.toString())
            )
            Log.i(TAG_APP, "Inserted Values")
        } catch (ex: Exception) {
            Log.e(TAG_APP, "Error inserting", ex)
        }
    }

    fun getAllApiaries(): List<ApiaryData>? {
        val db = this.readableDatabase

        val selectQuery = """
            SELECT id, name, date_of_creation, location FROM $TABLE_APIARIES_OLD
            """.trimIndent()

        val apiaryList = ArrayList<ApiaryData>()

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e(TAG_APP, "Select all apiaries error\n", e)
            return null
        }
        with(cursor) {
            while (moveToNext()) {
                val apiaryName = getString(getColumnIndexOrThrow("name"))
                val apiaryId = getInt(getColumnIndexOrThrow("id"))
                val apiaryDateOfCreation = getString(getColumnIndexOrThrow("date_of_creation"))
                val apiaryLocation = getString(getColumnIndexOrThrow("location"))
//                todo remake
            //                apiaryList.add(
//                    ApiaryData(
//                        apiaryId,
//                        apiaryName,
//                        apiaryDateOfCreation,
//                        apiaryLocation
//                    )
//                )
            }
        }
        cursor.close()
        return apiaryList
    }


    fun getAllTasks(): List<TaskData>? {
        val db = this.readableDatabase

        val selectQuery = """
            SELECT id, name, description, hive_id FROM $TABLE_TASK_OLD
            """.trimIndent()

        val taskList = ArrayList<TaskData>()

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e(TAG_APP, "Select  apiaries error\n", e)
            return null
        }
        with(cursor) {
            while (moveToNext()) {
                val taskName = getString(getColumnIndexOrThrow("name"))
                val taskId = getInt(getColumnIndexOrThrow("id"))
                val taskDescription = getString(getColumnIndexOrThrow("description"))
                val taskHiveId = getInt(getColumnIndexOrThrow("hive_id"))
                taskList.add(
                    TaskData(
                        taskId,
                        taskName,
                        taskDescription,
                        taskHiveId
                    )
                )
            }
        }
        cursor.close()
        return taskList
    }

    fun getApiaryById(id: Int): ApiaryData? {
        val db = this.readableDatabase

        val selectQuery = """
            SELECT id, name, date_of_creation, location FROM $TABLE_APIARIES_OLD
             where id = $id
            """.trimIndent()

        lateinit var apiaryResult: ApiaryData

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e(TAG_APP, "Select all apiaries error\n", e)
            return null
        }
        //todo remake na nową wersje
        with(cursor) {
            moveToFirst()
            //todo remake
//            val apiaryName = getString(getColumnIndexOrThrow("name"))
//            val apiaryId = getInt(getColumnIndexOrThrow("id"))
//            val apiaryDateOfCreation = getString(getColumnIndexOrThrow("date_of_creation"))
//            val apiaryLocation = getString(getColumnIndexOrThrow("location"))
//            apiaryResult =
//                ApiaryData(
//                    apiaryId,
//                    apiaryName,
//                    apiaryDateOfCreation,
//                    apiaryLocation
//                )


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
            FROM $TABLE_HIVES_OLD
            where apiary_id = $apiaryId 
            """.trimIndent() //FIXME zmiana na BREED zamiast BREAD


        val hiveList = ArrayList<HiveData>()

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e(TAG_APP, "Select all hives error\n", e)
            return null
        }
        with(cursor) {
            while (moveToNext()) {
                //todo remake
//                val hiveId = getInt(getColumnIndexOrThrow("id"))
//                val hiveName = getString(getColumnIndexOrThrow("name"))
//                val hiveApiaryId = getInt(getColumnIndexOrThrow("apiary_id"))
//                val hiveQueenBreed = getString(getColumnIndexOrThrow("queen_bread"))
//                val hiveQueenBirthDate = getString(getColumnIndexOrThrow("queen_birth_date"))
//                hiveList.add(
//                    HiveData(
//                        hiveId,
//                        hiveName,
//                        hiveApiaryId,
//                        hiveQueenBreed,
//                        hiveQueenBirthDate
//                    )
//                )
            }
        }
        cursor.close()
        return hiveList
    }


    fun getAllHives(): List<HiveData>? {
        val db = this.readableDatabase

        val selectQuery = """
            SELECT             
            id, 
            name,
            apiary_id,
            queen_bread,
            queen_birth_date 
            FROM $TABLE_HIVES_OLD
            """.trimIndent() //FIXME zmiana na BREED zamiast BREAD


        val hiveList = ArrayList<HiveData>()

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e(TAG_APP, "Select all hives error\n", e)
            return null
        }
        with(cursor) {
            while (moveToNext()) {
//                todo remake
            //                val hiveId = getInt(getColumnIndexOrThrow("id"))
//                val hiveName = getString(getColumnIndexOrThrow("name"))
//                val hiveApiaryId = getInt(getColumnIndexOrThrow("apiary_id"))
//                val hiveQueenBreed = getString(getColumnIndexOrThrow("queen_bread"))
//                val hiveQueenBirthDate = getString(getColumnIndexOrThrow("queen_birth_date"))
//                hiveList.add(
//                    HiveData(
//                        hiveId,
//                        hiveName,
//                        hiveApiaryId,
//                        hiveQueenBreed,
//                        hiveQueenBirthDate
//                    )
//                )
            }
        }
        cursor.close()
        return hiveList
    }

    fun getHiveById(hiveId: Int): HiveData? {
        val db = this.readableDatabase

        val selectQuery = """
            SELECT             
            id, 
            name,
            apiary_id,
            queen_bread,
            queen_birth_date 
            FROM $TABLE_HIVES_OLD
            where id = $hiveId 
            """.trimIndent() //FIXME zmiana na BREED zamiast BREAD


        val hiveData: HiveData? = null

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e(TAG_APP, "Select all hives error\n", e)
            return null
        }
        with(cursor) {
            moveToFirst()
            //todo remake
//            val hiveId = getInt(getColumnIndexOrThrow("id"))
//            val hiveName = getString(getColumnIndexOrThrow("name"))
//            val hiveApiaryId = getInt(getColumnIndexOrThrow("apiary_id"))
//            val hiveQueenBreed = getString(getColumnIndexOrThrow("queen_bread"))
//            val hiveQueenBirthDate = getString(getColumnIndexOrThrow("queen_birth_date"))
//            hiveData = HiveData(
//                hiveId,
//                hiveName,
//                hiveApiaryId,
//                hiveQueenBreed,
//                hiveQueenBirthDate
//            )

        }

        cursor.close()
        return hiveData
    }

    fun countAllHives(): Int {
        return countHives()
    }

    fun countAllHivesByApiaryId(apiaryId: Int): Int {
        return countHives(apiaryId)
    }

    fun countHives(apiaryId: Int = -1): Int {
        val db = this.readableDatabase

        lateinit var selectQuery: String
        if (apiaryId == -1) {
            selectQuery = """
                SELECT             
                count(1) c
                FROM $TABLE_HIVES_OLD
                """.trimIndent() //FIXME zmiana w deklaracji na BREED zamiast BREAD
        } else {
            selectQuery = """
                SELECT             
                count(1) c
                FROM $TABLE_HIVES_OLD
                where apiary_id = $apiaryId
                """.trimIndent()
        }
        val cursor: Cursor?

        var ret: Int

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e(TAG_APP, "Select all hives error\n", e)
            return -1
        }
        with(cursor) {

            moveToFirst()
            ret = getInt(getColumnIndexOrThrow("c"))

        }
        cursor.close()
        return ret

    }


    fun countApiaries(): Int {
        val db = this.readableDatabase

        val selectQuery: String = """
                SELECT             
                count(1) c
                FROM $TABLE_APIARIES_OLD
                """.trimIndent()
        val cursor: Cursor?

        var ret: Int

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e(TAG_APP, "Select all apiaries error\n", e)
            return -1
        }
        with(cursor) {

            moveToFirst()
            ret = getInt(getColumnIndexOrThrow("c"))

        }
        cursor.close()
        return ret

    }


    fun countTasks(): Int {
        val db = this.readableDatabase

        val selectQuery: String = """
                SELECT             
                count(1) c
                FROM $TABLE_TASK_OLD
                """.trimIndent()
        val cursor: Cursor?

        var ret: Int

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.e(TAG_APP, "Select all TASK error\n", e)
            return -1
        }
        with(cursor) {

            moveToFirst()
            ret = getInt(getColumnIndexOrThrow("c"))

        }
        cursor.close()
        return ret

    }

    fun insertHive(hiveData: HiveData): Boolean {
        // todo remake Log.d(TAG_APP, "hives list in DB:" + this.getAllHivesByApiaryId(hiveData.apiaryId))
        return try {

            val r = this.writableDatabase.insert(TABLE_HIVES_OLD, null, hiveData.mapToValues())
            Log.i(TAG_APP, "Inserted Values?")
            r != -1L
        } catch (ex: Exception) {
            Log.e(TAG_APP, "Error inserting", ex)
            false
        }

    }


    fun insertTask(taskData: TaskData): Boolean {
//        Log.d(TAG, "task list in DB:" + this.getAllHivesByApiaryId(taskData.apiaryId))
        return try {

            val r = this.writableDatabase
                .insert(
                    TABLE_TASK_OLD, null,
                    taskData.mapToValues()
                )
            Log.i(TAG_APP, "Inserted Values?")
            r != -1L
        } catch (ex: Exception) {
            Log.e(TAG_APP, "Error inserting", ex)
            false
        }

    }

    fun deleteApiary(apiaryId: Int): Boolean {
        var b = false
        try {
            val args = Array(1) { apiaryId.toString() }
            val affectedRows =
                this.writableDatabase.delete(TABLE_APIARIES_OLD, "id=?", args)

            b = 1 <= affectedRows
        } catch (err: Exception) {
            Log.e(TAG_APP, "Error while deleting from $TABLE_APIARIES_OLD id $apiaryId! err: ", err)
        }
        return b
    }


    fun deleteHive(hiveId: Int): Boolean {
        var b = false
        try {
            val args = Array(1) { hiveId.toString() }
            val affectedRows =
                this.writableDatabase.delete(TABLE_HIVES_OLD, "id=?", args)

            b = 1 <= affectedRows
        } catch (err: Exception) {
            Log.e(TAG_APP, "Error while deleting from $TABLE_HIVES_OLD id $hiveId! err: ", err)
        }
        return b
    }


    fun deleteTask(taskId: Int): Boolean {
        var b = false
        try {
            val args = Array(1) { taskId.toString() }
            val affectedRows =
                this.writableDatabase.delete(TABLE_TASK_OLD, "id=?", args)

            b = 1 <= affectedRows
        } catch (err: Exception) {
            Log.e(TAG_APP, "Error while deleting from $TABLE_TASK_OLD id $taskId! err: ", err)
        }
        return b
    }


}