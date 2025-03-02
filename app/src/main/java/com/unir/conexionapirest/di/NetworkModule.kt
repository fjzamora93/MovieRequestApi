package com.unir.conexionapirest.di

import android.content.Context
import androidx.room.Room
import com.unir.conexionapirest.data.service.ApiService
import com.unir.conexionapirest.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**INYECCIÓN DE DEPENDENCIAS (DI).
 *
 * Esta clase nos va a dar una serie de métodos que nos van a permitir
 * gestionar las dependencias de la aplicación sin tener que estar instanciándolas
 * una y otra vez.
 *
 * */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /** INSTANCIA ÚNICA DE RETROFIT + CONVERTIDOR JSON */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://peticiones.online/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /** INSTANCIA ÚNICA DEL SERVICIO API */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }



    /** INSTANCIA ÚNICA DEL REPOSITORIO REMOTO */
    @Provides
    @Singleton
    fun provideMovieRemoteRepository(apiService: ApiService): Repository {
        return Repository(apiService)
    }



}