package com.sedatkaganaktas.pipeapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedatkaganaktas.pipeapp.model.PipeItem
import com.sedatkaganaktas.pipeapp.model.PipeList
import com.sedatkaganaktas.pipeapp.model.PipeListItem
import com.sedatkaganaktas.pipeapp.respository.PipeRepository
import com.sedatkaganaktas.pipeapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PipeListViewModel @Inject constructor(
    private val repository: PipeRepository
) : ViewModel() {

    var pipeList = mutableStateOf<List<PipeListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initialPipeList = listOf<PipeListItem>()
    private var isSearchStarting = true

    init {

        loadPipes()
    }


    fun searchPipeList(query: String) {
        val listToSearch = if(isSearchStarting) {
            pipeList.value
        } else {
            initialPipeList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) {
                pipeList.value = initialPipeList
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.pipe.contains(query.trim(), ignoreCase = true)
            }
            if(isSearchStarting) {
                initialPipeList = pipeList.value
                isSearchStarting = false
            }
            pipeList.value = results
        }
    }

    fun loadPipes() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPipeList()
            when(result) {
                is Resource.Success -> {
                    val pipeItems = result.data!!.mapIndexed { index, item ->
                        PipeListItem(item.pipe,item.price)
                    } as List<PipeListItem>

                    errorMessage.value = ""
                    isLoading.value = false
                    pipeList.value += pipeItems
                }
                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    errorMessage.value = ""

                }
            }

        }

    }





}