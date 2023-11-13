package ru.tanexc.photosolver.domain


import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ImageApi {

    @Multipart
    @POST("api/post/image")
    fun sendImage(
        @Query("iid") iid: String,
        @Part file: MultipartBody.Part
    ): Call<ResponseBody>

    @GET("/")
    fun getCock(): Call<ResponseBody>
}