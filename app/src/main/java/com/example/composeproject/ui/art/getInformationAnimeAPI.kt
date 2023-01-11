package com.example.composeproject.ui.art

import android.util.Log
import com.example.composeproject.data.remote.AnimeAPI
import com.example.composeproject.data.remote.AnimeInformation
import com.example.composeproject.data.remote.AnimeNode
import com.example.composeproject.data.remote.ArtDataDto
import com.example.composeproject.model.ArtInformationState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getAnimeByString(
    string: String,
    listAnime: MutableList<ArtDataDto>
) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.myanimelist.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(AnimeAPI::class.java)
    val call: Call<ResponseBody> = api.searchAnimeByString(string)
    call.enqueue(object: Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if(response.isSuccessful) {
                // Log.d("SendRequest", "Response Received : " + (response.body()?.string() ?: "Error"))
                val sType = object : TypeToken<List<AnimeNode>>() { }.type
                val animeJson =
                    response.body()?.string()
                        ?.let { JSONObject(it).getJSONArray("data").toString() }
                val animeArray = Gson().fromJson<List<AnimeNode>>(animeJson, sType)
                Log.d("SendRequest", "AnimeArray : $animeArray")
                listAnime.removeAll(listAnime)
                animeArray.forEach{
                        anime -> listAnime.add(
                    ArtDataDto(
                        id = anime.node.id,
                        title = anime.node.title,
                        pictureURL = anime.node.main_picture.medium
                    )
                )
                }
                Log.d("SendRequest", "Array : $listAnime")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
        }
    })
}

fun getInformationFromAnime(
    id: Int,
    artState: ArtInformationState
) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.myanimelist.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(AnimeAPI::class.java)
    val call: Call<ResponseBody> = api.getAnimeInfo(id)
    call.enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val animeJson = response.body()?.string()?.let { JSONObject(it).toString() }
                val sType = object : TypeToken<AnimeInformation>() {}.type
                val animeObject = Gson().fromJson<AnimeInformation>(animeJson, sType)
                Log.d("SendRequest", "AnimeObject : $animeObject")
                artState.titleState.value = animeObject.title
                artState.descriptionState.value = animeObject.synopsis
                artState.markState.value = animeObject.mean.toString()
                artState.pictureState.value = animeObject.main_picture.medium
                artState.authorState.value = ""
                animeObject.studios.forEach { studio ->
                    artState.authorState.value += studio.name
                }
            } else {
                Log.d("SendRequest", "Response unsuccessful" + response.errorBody()?.string())
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
        }
    })
}