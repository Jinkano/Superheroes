package com.example.superheroes.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperheroService
{
    @GET("search/{name}")
    suspend fun findSuperheroesByName(@Path("name") query: String): SuperheroResponse

    @GET("{identifier}")
    suspend fun getSuperheroById(@Path("identifier") id: String):Superhero

    companion object
    {
        fun getInstance(): SuperheroService
        {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.superheroapi.com/api.php/70fbdfa55b1fa5ffa9806c13462a76d0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(SuperheroService::class.java)
        }
    }
}