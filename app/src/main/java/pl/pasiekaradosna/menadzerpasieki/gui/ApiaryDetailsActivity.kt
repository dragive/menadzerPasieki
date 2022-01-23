package pl.pasiekaradosna.menadzerpasieki.gui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_apiary_details.fabCreateNewHive
import kotlinx.android.synthetic.main.activity_apiary_details.rcApiaryDetailsHiveList
import kotlinx.android.synthetic.main.fragment_apiary_item.tvHiveName
import kotlinx.android.synthetic.main.fragment_apiary_item.tvHivesNumber
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.ApiaryItemFragment
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.hive.HiveAdapter
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.hive.CreateHiveActivity
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG

class ApiaryDetailsActivity : AppCompatActivity() {

    var id: Int = -1

    private lateinit var hiveAdapter: HiveAdapter

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
        val apiaryManagerDbHelper = ApiaryManagerDbHelper(this)

        //przypisanie wartości nazwy do odpowiedniego pola
        fragment.tvHiveName.text = apiaryManagerDbHelper
            .getApiaryById(id)
            ?.name.toString()

        fragment.tvHivesNumber.text = apiaryManagerDbHelper.countAllHivesByApiaryId(id).toString()




        fabCreateNewHive.setOnClickListener { view ->
            try {
                val intent = Intent(this, CreateHiveActivity::class.java)
                intent.putExtra("ApiaryId", this.id)

                startActivity(intent)
            } catch (err: Exception) {
                Log.e(TAG, "ERROR while goinf to CreateHiveActivity", err)
            }
        }

        val list = ApiaryManagerDbHelper(this).getAllHivesByApiaryId(id)
        if (list != null) {
            hiveAdapter = HiveAdapter(list)

            rcApiaryDetailsHiveList.adapter = hiveAdapter

            rcApiaryDetailsHiveList.layoutManager = LinearLayoutManager(this)
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