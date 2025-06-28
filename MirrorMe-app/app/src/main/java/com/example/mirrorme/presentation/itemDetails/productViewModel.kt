import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mirrorme.domain.model.Product
import kotlinx.coroutines.launch
import com.example.mirrorme.di.ServiceLocator

class ProductViewModel : ViewModel() {

    private val getProductByIdUseCase = ServiceLocator.getProductByIdUseCase

    var productUiState by mutableStateOf<Product?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadProduct(productId: String) {
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
}
