package com.unir.conexionapirest.data.repository

import com.unir.conexionapirest.data.model.ItemDetails
import com.unir.conexionapirest.data.service.ApiService
import com.unir.conexionapirest.data.model.ItemResumen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**CAPA LÓGICA DE ACCESO A BASE DE DATOS REMOTA*/
class Repository @Inject constructor(
    private val apiService: ApiService
) {


    suspend fun fetchAll(): Result<List<ItemResumen>> {
        return try {
            val response = apiService.getItems()

            if (!response.isSuccessful) {
                return Result.failure(Exception("Error en la respuesta del servidor: ${response.errorBody()?.string()}"))
            }

            val itemsResponse = response.body()
            if (itemsResponse?.results.isNullOrEmpty()) {
                return Result.failure(Exception("No se encontraron resultados para la búsqueda"))
            }

            val sortedList = itemsResponse!!.results.sortedByDescending { it.price.toInt() }

            Result.success(sortedList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }




    suspend fun fetchById(
        id: String,
        onSuccess: (ItemDetails) -> Unit,
        onError: () -> Unit
    ) {
        val response = apiService.getMovieById(id = id)
        val itemResponse = response.body()
        println("Realizando búsqueda con filtro dentro del REpositorio: $itemResponse y $id")
            if (response.isSuccessful && itemResponse != null) {
                onSuccess(itemResponse)
            } else {
                println("Error: ${response.errorBody()?.string()}")
                onError()
            }


    }




}