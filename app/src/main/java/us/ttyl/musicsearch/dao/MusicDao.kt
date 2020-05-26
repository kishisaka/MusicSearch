package us.ttyl.musicsearch.dao

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Singleton
class MusicDao {

    private var musicSearchService: MusicSearchService? = null

    companion object {
        const val ITUNES_URL_ROOT = "https://itunes.apple.com"
    }

    init {
        musicSearchService = Retrofit.Builder()
            .baseUrl(ITUNES_URL_ROOT)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(MusicSearchService::class.java)
    }

    /**
     * get the music search results here
     * @param search term String
     * @return list of MusicItems
     */
    fun getMusicItems(searchTerm: String): MusicSearchResult? {
        val term = mapOf(Pair("term", searchTerm))
        val call = musicSearchService?.getMusicItems(term)
        val response = call?.execute()
        return response?.body()
    }

    /**
     * get an individual music track item via track id
     * @param track id String
     * @return list of MusicItems (should only be one)
     */
    fun getMusicTrackItem(id: String): MusicSearchResult? {
        val trackId = mapOf(Pair("id", id))
        val call = musicSearchService?.getMusicItem(trackId)
        val response = call?.execute()
        return response?.body()
    }

}
