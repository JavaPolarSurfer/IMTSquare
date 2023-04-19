package tr.edu.metu.ii.AnyChange.product.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tr.edu.metu.ii.AnyChange.product.dto.PriceSourceDTO;
import tr.edu.metu.ii.AnyChange.product.dto.ProductDTO;
import tr.edu.metu.ii.AnyChange.product.dto.ProductPricesDTO;
import tr.edu.metu.ii.AnyChange.product.exceptions.NoSuchPriceSourceException;
import tr.edu.metu.ii.AnyChange.product.exceptions.NoSuchProductException;
import tr.edu.metu.ii.AnyChange.product.models.PriceSource;
import tr.edu.metu.ii.AnyChange.product.services.ProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @PostMapping("/products/search")
    public String searchProduct(String productName, Model model) {
        List<ProductDTO> matchingProducts = productService.getMatchingProducts(productName);
        model.addAttribute("matchingProducts", matchingProducts);
        return "products";
    }

    @GetMapping("/products/product")
    public ModelAndView getProductInformation(@RequestParam("productId")long productId) {
        try {
            ProductDTO productDTO = productService.getProduct(productId);
            List<PriceSourceDTO> priceSourceDTOS = productService.getAvailablePriceSources(productDTO);
            ArrayList<ProductPricesDTO> productPricesDTOS = new ArrayList<>();

            priceSourceDTOS.forEach(priceSourceDTO -> {
                ProductPricesDTO productPricesDTO = new ProductPricesDTO();
                productPricesDTO.setPriceSourceName(priceSourceDTO.getName());
                try {
                    productPricesDTO.setPriceSourceName(priceSourceDTO.getName());
                    productPricesDTO.setPrice(String.valueOf(productService.getCurrentPrice(productDTO, priceSourceDTO).getPrice()));
                    productPricesDTOS.add(productPricesDTO);
                } catch (NoSuchPriceSourceException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchProductException e) {
                    throw new RuntimeException(e);
                }
            });

            ModelAndView mav = new ModelAndView("productInformation");
            mav.addObject("product", productDTO);
            mav.addObject("productPrices", productPricesDTOS);
            return mav;
        } catch (NoSuchProductException e) {
            throw new RuntimeException(e);
        }
    }
}
