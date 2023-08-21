package com.sedatkaganaktas.pipeapp.service

import com.sedatkaganaktas.pipeapp.model.Pipe
import com.sedatkaganaktas.pipeapp.model.PipeList
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.jar.Attributes


interface PipeAPI {

     @GET("prices")
     suspend fun getPipeList(
     @Query("key") key: String,
     ): PipeList

     @GET("pipes")
     suspend fun getPipe(
         @Query("key") key: String,
         @Query("ids") id : String,

     @Query("attributes")attributes:String

     ): Pipe


}