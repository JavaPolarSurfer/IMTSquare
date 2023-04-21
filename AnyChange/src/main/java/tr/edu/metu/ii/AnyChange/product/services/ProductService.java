package tr.edu.metu.ii.AnyChange.product.services;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.metu.ii.AnyChange.product.dto.PriceSourceDTO;
import tr.edu.metu.ii.AnyChange.product.dto.ProductDTO;
import tr.edu.metu.ii.AnyChange.product.exceptions.NoSuchPriceSourceException;
import tr.edu.metu.ii.AnyChange.product.exceptions.NoSuchProductException;
import tr.edu.metu.ii.AnyChange.product.models.*;
import tr.edu.metu.ii.AnyChange.product.repositories.*;
import tr.edu.metu.ii.AnyChange.user.models.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ProductService {
    final ProductRepository productRepository;
    final PriceSourceRepository priceSourceRepository;
    final PriceSourceService priceSourceService;
    final ProductUrlRepository productUrlRepository;
    final PricePointRepository pricePointRepository;
    final PriceInformationRepository priceInformationRepository;

    @PostConstruct
    private void initializeRepo() {
        PriceSource priceSource = new PriceSource();
        priceSource.setName("trendyol");
        priceSource.setScript("trendyol");
        priceSourceRepository.save(priceSource);

        ProductUrl productUrl = new ProductUrl();
        productUrl.setUrl("https://www.trendyol.com/baby-turco/dogadan-bebek-bezi-ekonomik-paket-junior-5-numara-160-adet-p-176119387?boutiqueId=61&merchantId=386464");
        productUrl.setPriceSource(priceSource);
        productUrlRepository.save(productUrl);

        PricePoint pricePoint = new PricePoint();
        pricePoint.setPoint(LocalDateTime.now());
        pricePoint.setPrice(0);

        PriceInformation priceInformation = new PriceInformation();
        priceInformation.setCurrentPrice(pricePoint);
        priceInformationRepository.save(priceInformation);

        HashMap<PriceSource, PriceInformation> productPrices = new HashMap<>();
        productPrices.put(priceSource, priceInformation);

        Product product = new Product();
        product.setProductPrices(productPrices);
        product.setName("Baby Turco Doğadan Bebek Bezi Ekonomik Paket Junior 5 Numara 160 Adet");
        ArrayList<ProductUrl> productUrls = new ArrayList<>();
        productUrls.add(productUrl);
        product.setProductUrls(productUrls);
        productRepository.save(product);
    }

    public List<ProductDTO> getMatchingProducts(String keyword) {
        List<Product> products = productRepository.findByNameContainsIgnoreCase(keyword);

        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        products.forEach(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTOS.add(productDTO);
        });

        return productDTOS;
    }

    public ProductDTO getProduct(long productId) throws NoSuchProductException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new NoSuchProductException("Product with given id does not exists!");
        }
        Product product = productOptional.get();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        return productDTO;
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

    @Scheduled(fixedRate = 10000)
    public void updateCurrentPrices() {
        productRepository.findAll().forEach(product -> {
            product.getProductUrls().forEach(productUrl -> {
                PricePoint pricePoint = priceSourceService.fetchCurrentPrice(productUrl);
                product.getProductPrices().get(productUrl.getPriceSource()).setCurrentPrice(pricePoint);
            });
        });
    }

    public List<PriceSourceDTO> getAvailablePriceSources(ProductDTO productDTO) throws NoSuchProductException {
        Optional<Product> productOptional = productRepository.findById(productDTO.getId());
        if (productOptional.isEmpty()) {
            throw new NoSuchProductException("No such product exists!");
        }

        Product product = productOptional.get();

        ArrayList<PriceSourceDTO> priceSourceDTOS = new ArrayList<>();
        product.getProductPrices().forEach(((priceSource, priceInformation) -> {
            PriceSourceDTO priceSourceDTO = new PriceSourceDTO();
            priceSourceDTO.setId(priceSource.getId());
            priceSourceDTO.setName(priceSource.getName());
            priceSourceDTOS.add(priceSourceDTO);
        }));
        return priceSourceDTOS;
    }

    public void monitorProduct(User user, long productId) throws NoSuchProductException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new NoSuchProductException("No such product exists!");
        }

        Product product = productOptional.get();
        user.getMonitoredProducts().add(product);
    }

    public List<ProductDTO> getMonitoredProducts(User user) {
        List<Product> monitoredProducts = user.getMonitoredProducts();
        ArrayList<ProductDTO> monitoredProductDTOS = new ArrayList<>();
        monitoredProducts.forEach(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            monitoredProductDTOS.add(productDTO);
        });

        return monitoredProductDTOS;
    }

    public void removeMonitor(User user, long productId) throws NoSuchProductException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new NoSuchProductException("No such product exists!");
        }

        Product product = productOptional.get();
        user.getMonitoredProducts().remove(product);
    }
}



