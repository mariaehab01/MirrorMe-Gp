package com.example.mirrorme.presentation.cart

import androidx.lifecycle.ViewModel
import com.example.mirrorme.domain.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    val totalPrice: StateFlow<Double> = _cartItems
        .map { items -> items.sumOf { it.price * it.quantity } }
        .stateIn(
            scope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main),
            started = SharingStarted.Eagerly,
            initialValue = 0.0
        )

    fun addItem(item: CartItem) {
        val existingItem = _cartItems.value.find {
            it.productId == item.productId && it.color == item.color && it.size == item.size
        }

        if (existingItem != null) {
            existingItem.quantity += item.quantity
            _cartItems.value = _cartItems.value.map {
                if (it == existingItem) existingItem else it
            }
        } else {
            _cartItems.value = _cartItems.value + item
        }
    }


    fun incrementQuantity(item: CartItem) {
        _cartItems.value = _cartItems.value.map {
            if (it == item && it.quantity < 10) it.copy(quantity = it.quantity + 1) else it
        }
    }

    fun decrementQuantity(item: CartItem) {
        _cartItems.value = _cartItems.value.flatMap {
            when {
                it == item && it.quantity > 1 -> listOf(it.copy(quantity = it.quantity - 1))
                it == item && it.quantity == 1 -> emptyList() // remove the item
                else -> listOf(it)
            }
        }
    }
}