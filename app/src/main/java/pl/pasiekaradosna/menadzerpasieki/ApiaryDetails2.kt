package pl.pasiekaradosna.menadzerpasieki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_apiary_details.*
import pl.pasiekaradosna.menadzerpasieki.dal.TestDbHelper

class ApiaryDetails2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiary_details_2)

        actionBar?.setHomeButtonEnabled(true);
    }

    override fun onStart() {
        super.onStart()

        val textView : TextView = findViewById(R.id.textView)

        val myDbHelper = TestDbHelper(this)

        textView.text = myDbHelper.readAllUsers().toString()

        bInsert.setOnClickListener {
            TestDbHelper(this).insertValue(etInsert.text.toString())
            textView.text = myDbHelper.readAllUsers().toString()

        }


    }
}