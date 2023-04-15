package tr.edu.metu.ii.AnyChange.product;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private String name;
    private ArrayList<ProductUrl> productUrls;
    private HashMap<PriceSource, PriceInformation> productPrices;

    public PricePoint getCurrentPrice(PriceSource priceSource) throws NoSuchPriceSourceException {
        if (!productPrices.containsKey(priceSource)) {
            throw new NoSuchPriceSourceException("Price source does not exists for this product!");
        }
        return productPrices.get(priceSource).getCurrentPrice();
    }

    public void updateCurrentPrices() {
        for (ProductUrl url : productUrls) {
            PricePoint pricePoint = url.fetchCurrentPrice();
            productPrices.get(url.getPriceSource()).setCurrentPrice(pricePoint);
        }
    }

}
