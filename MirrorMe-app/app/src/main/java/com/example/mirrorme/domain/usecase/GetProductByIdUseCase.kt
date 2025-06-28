import com.example.mirrorme.domain.model.Product
import com.example.mirrorme.domain.repository.ProductRepository

class GetProductByIdUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(productId: String): Result<Product> {
        return repository.getProductById(productId)
    }
}
