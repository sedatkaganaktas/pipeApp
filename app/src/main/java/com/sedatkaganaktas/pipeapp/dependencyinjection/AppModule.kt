package com.sedatkaganaktas.pipeapp.dependencyinjection

import com.sedatkaganaktas.pipeapp.respository.PipeRepository
import com.sedatkaganaktas.pipeapp.service.PipeAPI
import com.sedatkaganaktas.pipeapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePipeRepository(
        api: PipeAPI
    ) = PipeRepository(api)

    @Singleton
    @Provides
    fun providePipeApi(): PipeAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PipeAPI::class.java)
    }
}