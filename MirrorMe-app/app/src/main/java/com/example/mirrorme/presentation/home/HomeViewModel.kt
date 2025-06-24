package com.example.mirrorme.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mirrorme.di.ServiceLocator
import com.example.mirrorme.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val getProductsUseCase = ServiceLocator.getProductsUseCase

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    fun loadProducts() {
        viewModelScope.launch {
            val result = getProductsUseCase()
            if (result.isSuccess) {
                _products.value = result.getOrDefault(emptyList())
            }
        }
    }
}