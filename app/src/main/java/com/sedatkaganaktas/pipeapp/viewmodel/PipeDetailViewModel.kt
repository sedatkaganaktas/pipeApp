package com.sedatkaganaktas.pipeapp.viewmodel

import androidx.lifecycle.ViewModel
import com.sedatkaganaktas.pipeapp.model.Pipe
import com.sedatkaganaktas.pipeapp.respository.PipeRepository
import com.sedatkaganaktas.pipeapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PipeDetailViewModel @Inject constructor(
    private val repository: PipeRepository
) : ViewModel() {

    suspend fun getPipe(id: String): Resource<Pipe> {
        return repository.getPipe(id)
    }
}