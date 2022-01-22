package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.data.Settings

class ApiaryItemFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_apiary_item,null)
    }

    override fun onStart() {
        super.onStart()
        test()
    }


    fun test(){
        Log.d(Settings.TAG, "ApiaryItemFragment TEST")
    }


}