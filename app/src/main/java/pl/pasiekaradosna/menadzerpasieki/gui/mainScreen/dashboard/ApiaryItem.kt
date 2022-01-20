package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard

import android.app.Activity
import android.util.Log
import android.view.View
import pl.pasiekaradosna.menadzerpasieki.data.Settings

class ApiaryItem : Activity(), View.OnClickListener {
    override fun onClick(view: View) {
        Log.i(Settings.TAG, "OnClickListener")
    }
}