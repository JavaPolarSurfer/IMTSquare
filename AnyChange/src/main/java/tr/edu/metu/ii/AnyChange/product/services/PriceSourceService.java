package tr.edu.metu.ii.AnyChange.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tr.edu.metu.ii.AnyChange.product.models.PricePoint;
import tr.edu.metu.ii.AnyChange.product.models.ProductUrl;
import tr.edu.metu.ii.AnyChange.product.repositories.PriceSourceRepository;

import java.io.*;
import java.nio.file.FileSystems;

@Service
public class PriceSourceService {
    @Autowired
    private PriceSourceRepository priceSourceRepository;
    @Value("${anychange.scripts.path}")
    private String scriptsPath;

    public PricePoint fetchCurrentPrice(ProductUrl productUrl) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python",
                    scriptsPath + FileSystems.getDefault().getSeparator() + productUrl.getPriceSource().getScript() + ".py",
                    productUrl.getUrl()).inheritIO();
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
        }  catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new PricePoint();
    }
}
