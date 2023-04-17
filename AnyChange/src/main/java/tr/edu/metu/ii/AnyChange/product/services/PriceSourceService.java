package tr.edu.metu.ii.AnyChange.product.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tr.edu.metu.ii.AnyChange.product.models.PricePoint;
import tr.edu.metu.ii.AnyChange.product.models.ProductUrl;
import tr.edu.metu.ii.AnyChange.product.repositories.PriceSourceRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


@Service
@AllArgsConstructor
public class PriceSourceService {
    final PriceSourceRepository priceSourceRepository;
    public PricePoint fetchCurrentPrice(ProductUrl productUrl) {
        try {

            ProcessBuilder processBuilder = new ProcessBuilder("python", "/home/taylan/workspace/IMTSquare/AnyChange/src/main/resources/scripts/hello.py").inheritIO();
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
        } catch (FileNotFoundException e) {
            assert(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        return new PricePoint();
    }
}
