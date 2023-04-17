package tr.edu.metu.ii.AnyChange.product.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tr.edu.metu.ii.AnyChange.product.dto.PriceSourceDTO;
import tr.edu.metu.ii.AnyChange.product.dto.ProductDTO;
import tr.edu.metu.ii.AnyChange.product.exceptions.NoSuchPriceSourceException;
import tr.edu.metu.ii.AnyChange.product.exceptions.NoSuchProductException;
import tr.edu.metu.ii.AnyChange.product.models.PricePoint;
import tr.edu.metu.ii.AnyChange.product.models.PriceSource;
import tr.edu.metu.ii.AnyChange.product.models.Product;
import tr.edu.metu.ii.AnyChange.product.repositories.PriceSourceRepository;
import tr.edu.metu.ii.AnyChange.product.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    final ProductRepository productRepository;
    final PriceSourceRepository priceSourceRepository;
    final PriceSourceService priceSourceService;

    public List<ProductDTO> getMatchingProducts(String keyword) {
        List<Product> products = productRepository.search(keyword);

        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        products.forEach(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTOS.add(productDTO);
        });

        return productDTOS;
    }

    public PricePoint getCurrentPrice(ProductDTO productDTO, PriceSourceDTO priceSourceDTO) throws NoSuchPriceSourceException, NoSuchProductException {
        Optional<Product> productOptional = productRepository.findById(productDTO.getId());
        if (productOptional.isEmpty()) {
            throw new NoSuchProductException("No such product exists!");
        }
        Optional<PriceSource> priceSourceOptional = priceSourceRepository.findById(priceSourceDTO.getId());
        if (priceSourceOptional.isEmpty()) {
            throw new NoSuchPriceSourceException("No such price source exists!");
        }

        Product product = productOptional.get();
        PriceSource priceSource = priceSourceOptional.get();

        if (!product.getProductPrices().containsKey(priceSource)) {
            throw new NoSuchPriceSourceException("Price source does not exists for this product!");
        }
        return product.getProductPrices().get(priceSource).getCurrentPrice();
    }

    private void updateCurrentPrices() {
        productRepository.findAll().forEach(product -> {
            product.getProductUrls().forEach(productUrl -> {
                PricePoint pricePoint = priceSourceService.fetchCurrentPrice(productUrl);
                product.getProductPrices().get(productUrl.getPriceSource()).setCurrentPrice(pricePoint);
            });
        });
    }

    public List<PriceSource> getAvailablePriceSources(ProductDTO productDTO) throws NoSuchProductException {
        Optional<Product> productOptional = productRepository.findById(productDTO.getId());
        if (productOptional.isEmpty()) {
            throw new NoSuchProductException("No such product exists!");
        }

        Product product = productOptional.get();

        ArrayList<PriceSource> priceSources = new ArrayList<>();
        product.getProductPrices().forEach(((priceSource, priceInformation) -> priceSources.add(priceSource)));
        return priceSources;
    }
}
