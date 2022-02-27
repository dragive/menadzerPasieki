package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.hive

import android.content.ContentValues

data class HiveData(
    var id: Int?,
    var name: String,
    var apiaryId: Int,
    var queenBreed: String,
    var queenBirthDate: String
) {
    fun mapToValues(): ContentValues {
        val values = ContentValues()
        if (id != null && id != -1) {
            values.put("id", id)
        }
        values.put("name", name)
        values.put("apiary_id", apiaryId)
        values.put("queen_bread", queenBreed)
        values.put("queen_birth_date", queenBirthDate)
        return values
    }
}
