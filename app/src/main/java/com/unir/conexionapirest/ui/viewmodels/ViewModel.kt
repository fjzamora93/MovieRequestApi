package com.unir.conexionapirest.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.conexionapirest.data.model.ItemResumen
import com.unir.conexionapirest.data.model.ItemDetails
import com.unir.conexionapirest.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _selectedItem = MutableStateFlow<ItemDetails?>(null)
    val selectedItem: StateFlow<ItemDetails?> = _selectedItem

    private val _itemsList = MutableStateFlow<List<ItemResumen>>(emptyList())
    val itemsList: MutableStateFlow<List<ItemResumen>> get() = _itemsList

    private val _filteredList = MutableStateFlow<List<ItemResumen>>(emptyList())
    val filteredList: MutableStateFlow<List<ItemResumen>> get() = _filteredList

    private val _error =MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error


    // Nada más iniciar, que la lista filtrada sea la misma que la lista completa
    init {
        viewModelScope.launch {
            _itemsList.collect { items -> _filteredList.value = items
            }
        }
    }

    fun fetchAll() {
        viewModelScope.launch {
            val result = repository.fetchAll()
            result.onSuccess { items -> _itemsList.value = items }
            result.onFailure { _error.value = true }
        }
    }


    fun fetchById(id: String) {
        viewModelScope.launch {
            repository.fetchById(
                id = id,
                onSuccess = { item ->
                    _selectedItem.value = item
                    println("Película encontrada Y ACTUALIZADA en el ModelView: ${selectedItem.value}")
                    _error.value = false
                },
                onError = {
                    _error.value = true
                }
            )
        }
    }

    fun filter(filter: String){
        if (filter != "") {
            println("Realizando búsqueda con filtro dentro del ViewMOdel: $filter")
            _filteredList.value = itemsList.value.filter {
                it.name.contains(filter, ignoreCase = true)
            }
            println(filteredList.value)
        } else {
            _filteredList.value = itemsList.value
        }
    }


    fun clearError(){
        _error.value = false
    }

}
