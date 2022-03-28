package pl.pasiekaradosna.menadzerpasieki.classes.data

import android.content.ContentValues

data class HiveData(
    var id: Int?,
    var name: String,
    var dateOfCreation: String?,
    var dateOfLastModification: String?,
    var queenId: Int?,
    var sizeOfHive: Int?,
    var deleted:Int?

) {
    fun mapToValues(): ContentValues {
        val values = ContentValues()
        if (id != null && id != -1) {
            values.put("id", id)
        }
        values.put("name", name)
        values.put("dateOfCreation", dateOfCreation)
        values.put("dateOfLastModification", dateOfLastModification)
        values.put("queenId", queenId)
        values.put("sizeOfHive", sizeOfHive)
        values.put("deleted", deleted)
        return values
    }
}
