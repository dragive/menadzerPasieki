package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters

import android.content.ContentValues

data class ApiaryData(
    val id: Int?,
    var name: String?,
    var dateOfCreation: String?,
    var location: String?
) {
    fun mapToValues(): ContentValues {
        val values = ContentValues()
        if (id != null) {
            values.put("id", id)
        }
        values.put("name", name)
        values.put("date_of_creation", dateOfCreation)
        values.put("location", location)
        return values
    }
}