package pl.pasiekaradosna.menadzerpasieki.classes.data

import android.content.ContentValues

data class ApiaryData(
    val id: Int?,
    var name: String?,
    var dateOfCreation: String?,
    var dateOfLastModification: String?,
    var location: String?,
    var humidity: Int?,
    var sunExposure: Int?,
    var deleted: Int?
) {
    fun mapToValues(): ContentValues {
        val values = ContentValues()
        if (id != null && id != -1) {
            values.put("id", id)
        }

        values.put("name", name)
        values.put("date_of_creation", dateOfCreation)
        values.put("dateOfLastModification", dateOfLastModification)
        values.put("location", location)
        values.put("humidity", humidity)
        values.put("sunExposure", sunExposure)
        values.put("deleted", deleted)

        return values
    }
}