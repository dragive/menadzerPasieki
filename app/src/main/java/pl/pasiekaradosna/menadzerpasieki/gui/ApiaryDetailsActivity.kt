package pl.pasiekaradosna.menadzerpasieki.gui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_apiary_details.fcvApiaryItem
import kotlinx.android.synthetic.main.fragment_apiary_item.tvHiveName
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.ApiaryItemFragment
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper

class ApiaryDetailsActivity : AppCompatActivity() {

    var id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiary_details)
        actionBar?.setHomeButtonEnabled(true)

        id = intent?.getIntExtra("ApiaryId", -1)!!

        if (id == -1) {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        // Pobranie fragmentu
        var fragment: ApiaryItemFragment =
            this.supportFragmentManager.fragments[0] as ApiaryItemFragment

        //przypisanie wartości nazwy do odpowiedniego pola
        fragment.tvHiveName.text = ApiaryManagerDbHelper(this)
            .getApiaryById(id)
            ?.name.toString()

        fcvApiaryItem.setOnClickListener {
//todo NOW            val intent: Intent = Intent(this,)
            //todo hive fragment
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}