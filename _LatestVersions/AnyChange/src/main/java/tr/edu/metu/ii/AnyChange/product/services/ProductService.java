package tr.edu.metu.ii.AnyChange.product.services;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.metu.ii.AnyChange.product.dto.PriceSourceDTO;
import tr.edu.metu.ii.AnyChange.product.dto.ProductDTO;
import tr.edu.metu.ii.AnyChange.product.exceptions.NoSuchPriceSourceException;
import tr.edu.metu.ii.AnyChange.product.exceptions.NoSuchProductException;
import tr.edu.metu.ii.AnyChange.product.models.*;
import tr.edu.metu.ii.AnyChange.product.repositories.*;
import tr.edu.metu.ii.AnyChange.user.models.User;
import tr.edu.metu.ii.AnyChange.user.services.UserService;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private UserService userService;

    @PostConstruct
    private void initializeRepo() {
        PriceSource trendyolSource = new PriceSource();
        trendyolSource.setName("trendyol");
        trendyolSource.setScript("trendyol");
        priceSourceRepository.save(trendyolSource);

        PriceSource hcSource = new PriceSource();
        hcSource.setName("hc");
        hcSource.setScript("hc");
        priceSourceRepository.save(hcSource);
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

    public void addNewProduct(ProductDTO productDTO) throws NoSuchPriceSourceException, MalformedURLException {
        Optional<Product> productOptional = productRepository.findById(productDTO.getId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            AtomicBoolean isSupported = new AtomicBoolean(false);
            priceSourceRepository.findAll().forEach(priceSource -> {
                if (productDTO.getProductURL().contains(priceSource.getName())) {
                    isSupported.set(true);
                    ProductUrl productUrl = new ProductUrl();
                    productUrl.setUrl(productDTO.getProductURL());
                    productUrl.setPriceSource(priceSource);
                    productUrlRepository.save(productUrl);

                    PricePoint pricePoint = new PricePoint();
                    pricePoint.setPoint(LocalDateTime.now());
                    pricePoint.setPrice(0);

                    PriceInformation priceInformation = new PriceInformation();
                    priceInformation.setCurrentPrice(pricePoint);
                    priceInformationRepository.save(priceInformation);

                    product.getProductPrices().put(priceSource, priceInformation);
                    product.getProductUrls().add(productUrl);
                }
            });
            if (!isSupported.get()) {
                throw new NoSuchPriceSourceException("This product URL is not supported by any price sources available!");
            }
        }
        else {
            AtomicBoolean isSupported = new AtomicBoolean(false);
            URL url = new URL(productDTO.getProductURL());
            priceSourceRepository.findAll().forEach(priceSource -> {
                if (url.getHost().contains(priceSource.getName())) {
                    isSupported.set(true);
                    ProductUrl productUrl = new ProductUrl();
                    productUrl.setUrl(productDTO.getProductURL());
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
                    product.setName(productDTO.getName());
                    ArrayList<ProductUrl> productUrls = new ArrayList<>();
                    productUrls.add(productUrl);
                    product.setProductUrls(productUrls);
                    productRepository.save(product);

                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication != null) {
                        String name = authentication.getName();
                        User user = (User) userService.loadUserByUsername(name);
                        try {
                            monitorProduct(user, product.getId());
                        } catch (NoSuchProductException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            if (!isSupported.get()) {
                throw new NoSuchPriceSourceException("This product URL is not supported by any price sources available!");
            }
        }
    }

    public void removeProduct(long productId) throws NoSuchProductException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                String name = authentication.getName();
                User user = (User) userService.loadUserByUsername(name);
                removeMonitor(user, productId);
            }

            productRepository.delete(productOptional.get());
        }
    }
}



