package pl.pasiekaradosna.menadzerpasieki.ui.dashboard

import android.app.Activity
import android.util.Log
import android.view.View
import pl.pasiekaradosna.menadzerpasieki.dal.Settings

class ApiaryItem : Activity(), View.OnClickListener {
    override fun onClick(view: View) {
        Log.i(Settings.TAG, "OnClickListener")
    }
}