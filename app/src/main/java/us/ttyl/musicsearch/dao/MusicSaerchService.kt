package us.ttyl.musicsearch.dao

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MusicSearchService {
    @GET("/search")
    fun getMusicItems(@QueryMap(encoded=true) option:Map<String, String>): Call<MusicSearchResult>

    @GET("/lookup")
    fun getMusicItem(@QueryMap(encoded=true) option:Map<String, String>): Call<MusicSearchResult>
}