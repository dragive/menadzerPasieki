package pl.pasiekaradosna.menadzerpasieki.classes.data

import org.json.JSONObject

data class WeatherData(
    var airPressureAtSeaLevel: Double,
    var airTemperature: Double,
    var cloudAreaFraction: Double,
    var relativeHumidity: Double,
    var windFromDirection: Double,
    var windSpeed: Double
) {
    companion object {
        fun parseFromJson(json: JSONObject): WeatherData {
            return parseJSON_YR_NO(json)
        }

        private fun parseJSON_YR_NO(json: JSONObject): WeatherData {
            val parsed = (json.getJSONObject("properties").getJSONArray("timeseries")
                .get(0) as JSONObject).getJSONObject("data").getJSONObject("instant")
                .getJSONObject("details")
            return WeatherData(
                parsed.getDouble("air_pressure_at_sea_level"),
                parsed.getDouble("air_temperature"),
                parsed.getDouble("cloud_area_fraction"),
                parsed.getDouble("relative_humidity"),
                parsed.getDouble("wind_from_direction"),
                parsed.getDouble("wind_speed")
            )

        }
    }
}
