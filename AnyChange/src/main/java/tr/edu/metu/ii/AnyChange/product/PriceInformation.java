package tr.edu.metu.ii.AnyChange.product;

import lombok.Getter;

import java.util.ArrayList;

public class PriceInformation {
    @Getter
    private PricePoint currentPrice;
    private ArrayList<PricePoint> priceHistory;

    public void setCurrentPrice(PricePoint pricePoint) {
        priceHistory.add(currentPrice);
        currentPrice = pricePoint;
    }
}
