package com.esipe.msbasket.services;

import com.esipe.msbasket.domain.Product;
import com.esipe.msbasket.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository pr;

    @Autowired
    public ProductService(ProductRepository pr) {
        this.pr = pr;
    }

    public Product add(Product product){
        product.setGtin(UUID.randomUUID().toString());
        return pr.save(product);
    }

    public Product getOne(String id){
        return Optional.ofNullable(
                pr.getOne(id)
        ).orElseThrow(() -> new RuntimeException("Error: no matching product found"));
    }

    public Product update(Product product){

        Product productInDb = getOne(product.getGtin());

        productInDb.setLabel(product.getLabel());
        productInDb.setUnitPrice(product.getUnitPrice());
        productInDb.setVat(product.getVat());

        return pr.update(productInDb);
    }

    public void delete(String id){
        getOne(id);
        pr.delete(id);
    }

    public List<Product> getAll(){
        return pr.getAll();
    }

}
