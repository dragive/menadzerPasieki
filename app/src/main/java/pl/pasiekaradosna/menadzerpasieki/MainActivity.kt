package pl.pasiekaradosna.menadzerpasieki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pl.pasiekaradosna.menadzerpasieki.classes.debug.Element
import pl.pasiekaradosna.menadzerpasieki.classes.debug.ElementAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var elementAdapter : ElementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        elementAdapter = ElementAdapter(mutableListOf(Element("123"),Element("123"),Element("123"),Element("123"),Element("123")))



        rvMenu.adapter = elementAdapter

        rvMenu.layoutManager = LinearLayoutManager(this)

        bNextActivity.setOnClickListener {
            val intent = Intent(this,ApiaryDetails::class.java)
            startActivity(intent)
        }


    }
}