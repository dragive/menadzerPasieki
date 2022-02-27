package pl.pasiekaradosna.menadzerpasieki.gui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_apiary_details.bApiaryDetailsDelete
import kotlinx.android.synthetic.main.activity_apiary_details.bApiaryDetailsEdit
import kotlinx.android.synthetic.main.activity_apiary_details.fabCreateNewHive
import kotlinx.android.synthetic.main.activity_apiary_details.rcApiaryDetailsHiveList
import kotlinx.android.synthetic.main.fragment_apiary_item.tvApiaryLocation
import kotlinx.android.synthetic.main.fragment_apiary_item.tvHiveListItemName
import kotlinx.android.synthetic.main.fragment_apiary_item.tvHivesNumber
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.R.string
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.apiaryManagement.CreateApiaryActivity
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.ApiaryItemFragment
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.hive.HiveAdapter
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.hive.CreateHiveActivity
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG_APP

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

        val apiary = apiaryManagerDbHelper
            .getApiaryById(id)

        //przypisanie wartości nazwy do odpowiedniego pola
        fragment.tvHiveListItemName.text = apiary?.name.toString()

        fragment.tvApiaryLocation.text = apiary?.location.toString()

        fragment.tvHivesNumber.text = apiaryManagerDbHelper.countAllHivesByApiaryId(id).toString()




        fabCreateNewHive.setOnClickListener { view ->
            try {
                val intent = Intent(this, CreateHiveActivity::class.java)
                intent.putExtra("ApiaryId", this.id)

                startActivity(intent)
            } catch (err: Exception) {
                Log.e(TAG_APP, "ERROR while goinf to CreateHiveActivity", err)
            }
        }

        val list = ApiaryManagerDbHelper(this).getAllHivesByApiaryId(id)
        if (list != null) {
            hiveAdapter = HiveAdapter(list)

            rcApiaryDetailsHiveList.adapter = hiveAdapter

            rcApiaryDetailsHiveList.layoutManager = LinearLayoutManager(this)
        }

        bApiaryDetailsDelete.setOnClickListener {

            var apiaryManagerDbHelper = ApiaryManagerDbHelper(this)

            if (apiaryManagerDbHelper.countAllHivesByApiaryId(id) != 0) {
                Toast.makeText(this, getString(string.ToastCannotDeleteApiary), Toast.LENGTH_SHORT).show()
            } else {

                if (apiaryManagerDbHelper.deleteApiary(id)) {
                    Toast.makeText(this, getString(string.ToastDeletedApiary), Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        getString(string.ToastErrorOccuredWgileDeletingApiary),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        bApiaryDetailsEdit.setOnClickListener {
            val intent = Intent(this, CreateApiaryActivity::class.java)
            intent.putExtra("ApiaryId", apiary!!.id!!)
            startActivity(intent)
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