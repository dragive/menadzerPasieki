package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.task

import android.content.ContentValues

data class TaskData(
    var id:Int?,
    var name:String,
    var description:String,
    var hiveId:Int
){
    fun mapToValues(): ContentValues {
        val values = ContentValues()
        if(id!=null && id !=-1){
            values.put("id",id)
        }
        values.put("name", name)
        values.put("description", description)
        values.put("hive_id", hiveId)
        return values
    }
}
