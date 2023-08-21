package com.sedatkaganaktas.pipeapp.respository

import com.sedatkaganaktas.pipeapp.model.Pipe
import com.sedatkaganaktas.pipeapp.model.PipeList
import com.sedatkaganaktas.pipeapp.service.PipeAPI
import com.sedatkaganaktas.pipeapp.util.Constants.API_KEY
import com.sedatkaganaktas.pipeapp.util.Constants.CALL_ATTRIBUTES
import com.sedatkaganaktas.pipeapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PipeRepository@Inject constructor(
    private val api: PipeAPI
) {

    suspend fun getPipeList(): Resource<PipeList> {
        val response = try {
            api.getPipeList(API_KEY)
        } catch(e: Exception) {
            return Resource.Error(" Pipe list Error.")
        }
        return Resource.Success(response)
    }

    suspend fun getPipe(id: String): Resource<Pipe> {
        val response = try {
            api.getPipe(API_KEY,id, CALL_ATTRIBUTES)
        } catch(e: Exception) {
            return Resource.Error(" Pipe Error")
        }
        return Resource.Success(response)
    }
}