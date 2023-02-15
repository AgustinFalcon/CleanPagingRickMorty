package com.example.hiltpaging.data.service

import com.example.hiltpaging.data.model.Character
import com.example.hiltpaging.data.model.PagedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("character/")
    suspend fun getAllCharacters(@Query("page") page: Int): Response<PagedResponse<Character>>

    suspend fun getSingleCharacter(id: Int)

    suspend fun getMultipleCharacters(ids: List<Int>)

    suspend fun filterCharacters(
        name: String,
        status: String,
        species: String,
        type: String,
        gender: String
    )
}