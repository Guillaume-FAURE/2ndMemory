package com.example.composeproject.ui.art

import android.util.Log
import com.example.composeproject.data.remote.*
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

fun getMangaByString(
    string: String,
    listManga: MutableList<ArtDataDto>
) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.myanimelist.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(MangaAPI::class.java)
    val call: Call<ResponseBody> = api.searchMangaByString(string)
    call.enqueue(object: Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if(response.isSuccessful) {
                val sType = object : TypeToken<List<MangaNode>>() { }.type
                val mangaJson =
                    response.body()?.string()
                        ?.let { JSONObject(it).getJSONArray("data").toString() }
                Log.d("SendRequest", "MangaArray : $mangaJson")
                val mangaArray = Gson().fromJson<List<MangaNode>>(mangaJson, sType)
                Log.d("SendRequest", "MangaArray : $mangaArray")
                listManga.removeAll(listManga)
                mangaArray.forEach{
                    anime -> listManga.add(
                        ArtDataDto(
                            id = anime.node.id,
                            title = anime.node.title,
                            pictureURL = anime.node.main_picture.medium
                        )
                    )
                }
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
        }
    })
}

fun getInformationFromManga(
    id: Int,
    artState: ArtInformationState
) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.myanimelist.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(MangaAPI::class.java)
    val call: Call<ResponseBody> = api.getMangaInfo(id)
    call.enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val mangaJson = response.body()?.string()?.let { JSONObject(it).toString() }
                Log.d("SendRequest", "mangaJson : $mangaJson")
                val sType = object : TypeToken<MangaInformation>() {}.type
                val mangaObject = Gson().fromJson<MangaInformation>(mangaJson, sType)
                Log.d("SendRequest", "MangaObject : $mangaObject")
                artState.titleState.value = mangaObject.title
                artState.descriptionState.value = mangaObject.synopsis
                artState.markState.value = mangaObject.mean.toString()
                artState.pictureState.value = mangaObject.main_picture.medium
                artState.authorState.value = ""
                mangaObject.authors.forEach{
                    author ->  artState.authorState.value+=
                    "${author.node.first_name} : ${author.node.last_name} (${author.role})"
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