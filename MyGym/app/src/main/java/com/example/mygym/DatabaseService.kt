package com.example.mygym

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DatabaseService {
    @GET("esercizi")
    suspend fun listAll(): List<Esercizio>
    @GET("esercizi/{userId}")
    suspend fun listByUser(@Path("userId") userId:String): List<Esercizio>
    @GET("posts/search") // diventa post/search?filter=search
    suspend fun search(@Query("filter") search: String): List<Esercizio>
    @POST("posts/new")
    suspend fun create(@Body esercizio : Esercizio): Esercizio
}