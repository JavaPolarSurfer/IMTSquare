package tr.edu.metu.ii.AnyChange.product.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tr.edu.metu.ii.AnyChange.product.models.PricePoint;
import tr.edu.metu.ii.AnyChange.product.models.PriceSource;
import tr.edu.metu.ii.AnyChange.product.models.ProductUrl;
import tr.edu.metu.ii.AnyChange.product.repositories.PriceSourceRepository;

@Service
@AllArgsConstructor
public class PriceSourceService {
    final PriceSourceRepository priceSourceRepository;
    public PricePoint fetchCurrentPrice(ProductUrl productUrl) {
        PriceSource priceSource = productUrl.getPriceSource();
        // TODO: Burada python script'i çalışacak ve price'ı alacak
        return new PricePoint();
    }
}
