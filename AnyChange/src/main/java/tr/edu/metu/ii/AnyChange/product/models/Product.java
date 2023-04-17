package tr.edu.metu.ii.AnyChange.product.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(fetch = FetchType.LAZY)
    private List<ProductUrl> productUrls;
    @ManyToMany
    private Map<PriceSource, PriceInformation> productPrices;
}
