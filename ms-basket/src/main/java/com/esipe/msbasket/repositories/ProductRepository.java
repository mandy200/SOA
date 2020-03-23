package com.esipe.msbasket.repositories;

import com.esipe.msbasket.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {

    private Map<String, Product> products = new HashMap<>();

    public Product save(Product product){
        products.put(product.getGtin(), product);
        return product;
    }

    public Product update(Product product){
        return save(product);
    }

    public Product getOne(String id){
        return products.get(id);
    }

    public void delete(String id){
        products.remove(id);
    }

    public List<Product> getAll() {
        return new ArrayList<>(products.values());
    }

}
