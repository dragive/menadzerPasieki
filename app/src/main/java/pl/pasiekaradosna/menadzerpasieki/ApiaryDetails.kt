package pl.pasiekaradosna.menadzerpasieki


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_apiary_details.*
import pl.pasiekaradosna.menadzerpasieki.dal.AppDatabase
import pl.pasiekaradosna.menadzerpasieki.dal.User

class ApiaryDetails : AppCompatActivity() {

    private val TAG :String = "MenagerPasieki"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiary_details)

        actionBar?.setHomeButtonEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        try {
            Log.e(TAG, "onStart: ",)
            val db = AppDatabase.getInstance(this)

            val userDao = db.userDao()

            val user = User(1,"1","2");

            val users = userDao.insertAll(user)

            val tvDaoTest: TextView = findViewById(R.id.tvDaoTest)

            tvDaoTest.text = users.toString()

        }
        catch (ex :Exception){
            Log.e(TAG, "onStart: Błąd bazki: ",ex)
        }

    }
}