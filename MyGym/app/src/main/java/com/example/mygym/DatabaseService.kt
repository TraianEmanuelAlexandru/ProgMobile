package com.example.mygym

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DatabaseService {
    @GET("esercizi")
    suspend fun listAll(): List<Giorno.Esercizio>
    @GET("esercizi/{userId}")
    suspend fun listByUser(@Path("userId") userId:String): List<Giorno.Esercizio>
    @GET("posts/search") // diventa post/search?filter=search
    suspend fun search(@Query("filter") search: String): List<Giorno.Esercizio>
    @POST("posts/new")
    suspend fun create(@Body esercizio : Giorno.Esercizio): Giorno.Esercizio
}