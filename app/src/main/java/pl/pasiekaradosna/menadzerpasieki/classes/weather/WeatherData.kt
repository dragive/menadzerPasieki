package pl.pasiekaradosna.menadzerpasieki.classes.weather

import org.json.JSONObject

data class WeatherData(
    var airPressureAtSeaLevel:Double,
    var airTemperature:Double,
    var cloudAreaFraction:Double,
    var relativeHumidity:Double,
    var windFromDirection:Double,
    var windSpeed:Double
){
//    private var body:String = ""
//    fun WeatherData(body:String){
//        this.body = body
//    }

    companion object{
        fun parseFromJson(json: JSONObject):WeatherData{
            return parseJSON_YR_NO(json)
        }

        private fun parseJSON_YR_NO(json: JSONObject):WeatherData{
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

    /*
     this.airPressureAtSeaLevel = json.getDouble("air_pressure_at_sea_level")
            this.airTemperature = json.getDouble("air_temperature")
            this.cloudAreaFraction = json.getDouble("cloud_area_fraction")
            this.relativeHumidity = json.getDouble("relative_humidity")
            this.windFromDirection = json.getDouble("wind_from_direction")
            this.windSpeed = json.getDouble("wind_speed")
     */


}
