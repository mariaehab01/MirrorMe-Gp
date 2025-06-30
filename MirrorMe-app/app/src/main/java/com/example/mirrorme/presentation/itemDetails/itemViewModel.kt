import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mirrorme.domain.model.Product
import kotlinx.coroutines.launch
import com.example.mirrorme.di.ServiceLocator
import com.example.mirrorme.di.ServiceLocator.getOutfitItemsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class itemViewModel : ViewModel() {

    private val getProductByIdUseCase = ServiceLocator.getProductByIdUseCase
    private val getSimilarItemsUseCase = ServiceLocator.getSimilarItemsUseCase
    private val GetCompatibleUseCase = ServiceLocator.getComptibleItemsUseCase

    var productUiState by mutableStateOf<Product?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var similarItems by mutableStateOf<List<Int>>(emptyList())
        private set

    var compatibleItems by mutableStateOf<List<Int>>(emptyList())
        private set

    var similarProducts by mutableStateOf<List<Product>>(emptyList())
        private set

    var compatibleProducts by mutableStateOf<List<Product>>(emptyList())
        private set

    var outfitItems by mutableStateOf<List<Int>>(emptyList())
        private set

    var outfitProducts by mutableStateOf<List<Product>>(emptyList())
        private set

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            isLoading = true
            val result = getProductByIdUseCase(productId)
            result.onSuccess { product ->
                productUiState = product
            }.onFailure { error ->
                errorMessage = error.message
            }
            isLoading = false
        }
    }

    fun loadSimilarItems(productId: Int) {
        viewModelScope.launch {
            Log.d("ItemViewModel", "Calling getSimilarItemIds for $productId")
            try {
                val result = getSimilarItemsUseCase(productId)
                Log.d("ItemViewModel", "Similarity IDs result: $result")
                similarItems = result
                loadSimilarProducts()
            } catch (e: Exception) {
                Log.e("ItemViewModel", "Error calling Similarity API: ${e.message}")
            }
        }
    }
    fun loadCompatibleItems(productId: Int) {
        viewModelScope.launch {
            Log.d("ItemViewModel", "Calling getCompatibleItemIds for $productId")
            try {
                val result = GetCompatibleUseCase(productId)
                Log.d("ItemViewModel", "Compatibility IDs result: $result")
                compatibleItems = result
                loadCompatibleProducts()
            } catch (e: Exception) {
                Log.e("ItemViewModel", "Error calling Compatibility API: ${e.message}")
            }
        }
    }

    fun loadOutfitItems(seedItemId: Int) {
        viewModelScope.launch {
            Log.d("ItemViewModel", "Calling getOutfitItemIds for seedItemId: $seedItemId")
            try {
                val result = getOutfitItemsUseCase(seedItemId)
                Log.d("ItemViewModel", "Outfit Item IDs result: $result")
                outfitItems = result
                loadOutfitProducts()
            } catch (e: Exception) {
                Log.e("ItemViewModel", "Error calling Outfit API: ${e.message}")
            }
        }
    }



//    private fun loadRecommendedProducts() {
//        viewModelScope.launch {
//            val products = mutableListOf<Product>()
//            for (id in similarItems) {
//                val result = getProductByIdUseCase(id)
//                result.onSuccess { product ->
//                    products.add(product)
//                }
//            }
//            recommendedProducts = products
//        }
//    }
    private fun loadSimilarProducts() {
        viewModelScope.launch {
            val products = similarItems.map { id ->
                async {
                    val result = getProductByIdUseCase(id+1)
                    result.getOrNull()  // Returns Product if success, else null
                }
            }.awaitAll()

            similarProducts = products.filterNotNull()
        }
    }
    private fun loadCompatibleProducts() {
        viewModelScope.launch {
            val products = compatibleItems.map { id ->
                async {
                    val result = getProductByIdUseCase(id+1)
                    result.getOrNull()  // Returns Product if success, else null
                }
            }.awaitAll()

            compatibleProducts = products.filterNotNull()
        }
    }

    private fun loadOutfitProducts() {
        viewModelScope.launch {
            val products = outfitItems.map { id ->
                async {
                    val result = getProductByIdUseCase(id + 1)
                    Log.d("ItemViewModel", "Outfit Item IDs result: $result")
                    result.getOrNull()
                }
            }.awaitAll()

            outfitProducts = products.filterNotNull()
        }
    }

}
