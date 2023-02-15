package com.example.hiltpaging.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.example.hiltpaging.data.model.Character


interface CharacterRepository {

    suspend fun getAllCharacters(): Flow<PagingData<Character>>

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