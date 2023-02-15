package com.example.hiltpaging.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hiltpaging.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import com.example.hiltpaging.data.model.Character
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val characterRepository: CharacterRepository) : ViewModel() {


    private lateinit var _charactersFlow: Flow<PagingData<Character>>
    val charactersFlow: Flow<PagingData<Character>>
        get() = _charactersFlow


    init {
        getAllCharacters()
    }

    private fun getAllCharacters() = launchPagingAsync({
        characterRepository.getAllCharacters().cachedIn(viewModelScope)
    }, {
        _charactersFlow = it
    })




    inline fun <T> launchPagingAsync(
        crossinline execute: suspend () -> Flow<T>,
        crossinline onSuccess: (Flow<T>) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = execute()
                onSuccess(result)

            } catch (ex: Exception) {
                //errorMessage.value = ex.message
            }
        }
    }
}