package com.gabriel.prod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.gabriel.prod.repository.UomDataRepository;
import com.gabriel.prod.entity.UomData;
import com.gabriel.prod.repository.ProductDataRepository;
import com.gabriel.prod.entity.ProductData;

@SpringBootApplication
public class ProductMSApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(ProductMSApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UomDataRepository uomRepo, ProductDataRepository prodRepo) {
        return args -> {
            if (uomRepo.count() == 0) {
                System.out.println("Auto-creating initial database data...");
                
                // Create UOMs
                UomData initialUom = new UomData();
                initialUom.setName("Box");
                initialUom = uomRepo.save(initialUom);
                
                UomData secondUom = new UomData();
                secondUom.setName("Kg");
                secondUom = uomRepo.save(secondUom);

                // Create Sample Products
                if (prodRepo.count() == 0) {
                    System.out.println("Adding sample products...");
                    
                    ProductData p1 = new ProductData();
                    p1.setName("Dell XPS 15");
                    p1.setDescription("High-performance premium laptop from Dell.");
                    p1.setUomId(initialUom.getId());
                    p1.setUomName(initialUom.getName());
                    prodRepo.save(p1);

                    ProductData p2 = new ProductData();
                    p2.setName("Logitech MX Master 3S");
                    p2.setDescription("Wireless ergonomic mouse for productivity.");
                    p2.setUomId(initialUom.getId());
                    p2.setUomName(initialUom.getName());
                    prodRepo.save(p2);

                    ProductData p3 = new ProductData();
                    p3.setName("Keychron ERGO Keyboard");
                    p3.setDescription("Mechanical ergonomic keyboard.");
                    p3.setUomId(initialUom.getId());
                    p3.setUomName(initialUom.getName());
                    prodRepo.save(p3);
                }
                
                System.out.println("Database auto-population complete!");
            }
        };
    }
}
