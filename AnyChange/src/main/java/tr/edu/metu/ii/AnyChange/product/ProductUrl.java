package tr.edu.metu.ii.AnyChange.product;

import lombok.Getter;

public class ProductUrl {
    private String url;
    @Getter
    private PriceSource priceSource;

    public PricePoint fetchCurrentPrice() {
        priceSource.fetchCurrentPrice(url);
    }
}
