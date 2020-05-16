package com.example.weatherapp.model

data class SearchResponse (
    val search_api : SearchApi,
    val data : DataError

)

data class SearchApi (
    val result : List<Result>
)
data class DataError (
    val error : List<Error>
)

data class Error (
    val msg : String
)


data class Result (
    val areaName : List<AreaName>,
    val country : List<Country>,
    val region : List<Region>,
    val latitude : Double,
    val longitude : Double,
    val population : Int,
    val weatherUrl : List<WeatherUrl>
)
data class AreaName (
    val value : String
)
data class Country (
    val value : String
)
data class Region (
    val value : String
)
data class WeatherUrl (
    val value : String
)