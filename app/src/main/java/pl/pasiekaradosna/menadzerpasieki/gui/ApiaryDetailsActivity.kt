package pl.pasiekaradosna.menadzerpasieki.gui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_apiary_details.*
import kotlinx.android.synthetic.main.fragment_apiary_item.*
import kotlinx.android.synthetic.main.fragment_apiary_item.view.*
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.data.ApiaryManagerDbHelper
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.ApiaryItemFragment
import java.util.function.Predicate
import java.util.stream.Collectors

class ApiaryDetailsActivity : AppCompatActivity() {

    var id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiary_details)
        actionBar?.setHomeButtonEnabled(true)


        val id = intent?.getIntExtra("ApiaryId", -1)


        if(id == -1){
            finish()
        }


    }

    override fun onStart() {
        super.onStart()

        var fragment: ApiaryItemFragment = this.supportFragmentManager.fragments[0] as ApiaryItemFragment
        fragment.tvApiaryName.text= "123"
        //tvApiaryName.text = ApiaryManagerDbHelper(this).getAllApiaries()?.stream()
          //  ?.filter { o -> o.id == this.id }?.collect(Collectors.toList())?.get(0)?.name;

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