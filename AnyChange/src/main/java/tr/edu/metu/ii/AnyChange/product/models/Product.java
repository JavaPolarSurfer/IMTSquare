package tr.edu.metu.ii.AnyChange.product.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;

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
    @OneToMany
    private ArrayList<ProductUrl> productUrls;
    @ManyToMany
    private HashMap<PriceSource, PriceInformation> productPrices;
}
