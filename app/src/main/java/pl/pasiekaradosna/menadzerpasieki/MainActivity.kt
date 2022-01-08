package pl.pasiekaradosna.menadzerpasieki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pl.pasiekaradosna.menadzerpasieki.classes.debug.Element
import pl.pasiekaradosna.menadzerpasieki.classes.debug.ElementAdapter
import pl.pasiekaradosna.menadzerpasieki.dal.ApiaryManagerDbHelper
import pl.pasiekaradosna.menadzerpasieki.dal.Settings
import pl.pasiekaradosna.menadzerpasieki.dal.TestDbHelper

class MainActivity : AppCompatActivity() {

    private lateinit var elementAdapter : ElementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(Settings.TAG,this.javaClass.name)
        setContentView(R.layout.activity_main)

        elementAdapter = ElementAdapter(mutableListOf(Element("123"),Element("123"),Element("123"),Element("123"),Element("123")))



        rvMenu.adapter = elementAdapter

        rvMenu.layoutManager = LinearLayoutManager(this)

        bNextActivity.setOnClickListener {
            val intent = Intent(this,ApiaryDetails::class.java)
            startActivity(intent)
        }

        bClearDb.setOnClickListener{
            var bError = false
            try{
                TestDbHelper(this).deleteTable()
            }

            catch (ex:Exception){
                bError= true
                Toast.makeText(this,"Unable to droped Table",Toast.LENGTH_SHORT).show()
                Log.e(Settings.TAG, "Unable to droped Table", ex)

            }
            if(!bError){
                Toast.makeText(this,"Dropped",Toast.LENGTH_SHORT).show()
            }

        }


        bCreateDb.setOnClickListener{
            var bError = false
            try{
                TestDbHelper(this).createTable()
            }

            catch (ex:Exception){
                bError= true
                Toast.makeText(this,"Unable to Create",Toast.LENGTH_SHORT).show()
                Log.e(Settings.TAG, "Unable to Create", ex)

            }
            if(!bError){
                Toast.makeText(this,"Created",Toast.LENGTH_SHORT).show()
            }

        }

        bMainApp.setOnClickListener {
            val intent = Intent(this,MainScreenActivity::class.java)
            startActivity(intent)
        }


        bLocationTest.setOnClickListener {
            val intent = Intent(this,LocationTestActivity::class.java)
            startActivity(intent)
        }




        bClearDb2.setOnClickListener{
            var bError = false
            try{
                ApiaryManagerDbHelper(this).dropTables()
            }

            catch (ex:Exception){
                bError= true
                Toast.makeText(this,"Unable to droped Table",Toast.LENGTH_SHORT).show()
                Log.e(Settings.TAG, "Unable to droped Table", ex)

            }
            if(!bError){
                Toast.makeText(this,"Dropped",Toast.LENGTH_SHORT).show()
            }

        }


        bCreateDb2.setOnClickListener{
            var bError = false
            try{
                ApiaryManagerDbHelper(this).createTable()
            }

            catch (ex:Exception){
                bError= true
                Toast.makeText(this,"Unable to Create",Toast.LENGTH_SHORT).show()
                Log.e(Settings.TAG, "Unable to Create", ex)

            }
            if(!bError){
                Toast.makeText(this,"Created",Toast.LENGTH_SHORT).show()
            }

        }

    }
}