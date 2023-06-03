package tr.edu.metu.ii.AnyChange.product.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tr.edu.metu.ii.AnyChange.product.dto.PriceSourceDTO;
import tr.edu.metu.ii.AnyChange.product.dto.ProductDTO;
import tr.edu.metu.ii.AnyChange.product.dto.ProductPricesDTO;
import tr.edu.metu.ii.AnyChange.product.exceptions.NoSuchPriceSourceException;
import tr.edu.metu.ii.AnyChange.product.exceptions.NoSuchProductException;
import tr.edu.metu.ii.AnyChange.product.services.ProductService;
import tr.edu.metu.ii.AnyChange.user.dto.PaymentInformationDTO;
import tr.edu.metu.ii.AnyChange.user.models.User;
import tr.edu.metu.ii.AnyChange.user.models.UserRole;
import tr.edu.metu.ii.AnyChange.user.services.UserService;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
    private ProductService productService;
    private UserService userService;

    @GetMapping("/products")
    public String getProducts(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            User user = (User)userService.loadUserByUsername(name);
            List<ProductDTO> monitoredProducts = productService.getMonitoredProducts(user);
            model.addAttribute("monitoredProducts", monitoredProducts);
            model.addAttribute("isSeller", user.getRole() == UserRole.SELLER || user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.SUPER_ADMIN);
            model.addAttribute("isAdmin", user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.SUPER_ADMIN);
        }
        else {
            throw new RuntimeException("Could not authenticate user!");
        }
        return "products";
    }

    @PostMapping("/products/search")
    public String searchProduct(String productName, Model model) {
        List<ProductDTO> matchingProducts = productService.getMatchingProducts(productName);
        model.addAttribute("matchingProducts", matchingProducts);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            User user = (User)userService.loadUserByUsername(name);
            List<ProductDTO> monitoredProducts = productService.getMonitoredProducts(user);
            model.addAttribute("monitoredProducts", monitoredProducts);
            if (user.getRole() == UserRole.SELLER) {
                model.addAttribute("isSeller", true);
            }
        }
        else {
            throw new RuntimeException("Could not authenticate user!");
        }
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

                    var priceInfo = productService.getPriceInformation(priceSourceDTO.getName(), productId);
                    var priceHistory = priceInfo.getPriceHistory();
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < 10 && i < priceHistory.size(); ++i) {
                        builder.append(priceHistory.get(i).getPrice());
                        builder.append(",");
                    }
                    productPricesDTO.setHistory(builder.toString());
                    productPricesDTOS.add(productPricesDTO);
                } catch (NoSuchPriceSourceException | NoSuchProductException e) {
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

    @GetMapping("/monitorProduct")
    public String monitorProduct(@RequestParam("productId")long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            User user = (User)userService.loadUserByUsername(name);
            try {
                productService.monitorProduct(user, productId);
            } catch (NoSuchProductException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            throw new RuntimeException("Could not authenticate user!");
        }
        return "redirect:/products";
    }

    @GetMapping("/removeMonitor")
    public String removeMonitor(@RequestParam("productId")long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            User user = (User)userService.loadUserByUsername(name);
            try {
                productService.removeMonitor(user, productId);
            } catch (NoSuchProductException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            throw new RuntimeException("Could not authenticate user!");
        }
        return "redirect:/products";
    }

    @GetMapping("/addNewProduct")
    public String addNewProduct(Model model) {
        ProductDTO productDTO = new ProductDTO();
        model.addAttribute("productInformation", productDTO);
        return "addNewProduct";
    }

    @PostMapping("/addNewProduct")
    public String addNewProduct(@ModelAttribute("productInformation") ProductDTO productDTO, Model model) {
        try {
            productService.addNewProduct(productDTO);
        } catch (NoSuchPriceSourceException e) {
            model.addAttribute("errorUnsupportedPriceSource", "This product source is not supported!");
            return addNewProduct(model);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/products";
    }

    @GetMapping("/updateProduct")
    public String updateProduct(@RequestParam("productId")long productId, Model model) {
        try {
            ProductDTO productDTO = productService.getProduct(productId);
            model.addAttribute("productInformation", productDTO);
            return "addNewProduct";
        } catch (NoSuchProductException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/removeProduct")
    public String removeProduct(@RequestParam("productId")long productId, Model model) throws NoSuchProductException {
        productService.removeProduct(productId);
        return "redirect:/products";
    }

    @GetMapping("/addNotification")
    public String addNotification(@RequestParam("productId")long productId) {
        productService.addNotification(productId);
        return "redirect:/products";
    }

    @GetMapping("/removeNotification")
    public String removeNotification(@RequestParam("productId")long productId) {
        productService.removeNotification(productId);
        return "redirect:/products";
    }
}
