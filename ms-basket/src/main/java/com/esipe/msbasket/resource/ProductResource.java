package com.esipe.msbasket.resource;

import com.esipe.msbasket.domain.Product;
import com.esipe.msbasket.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "products")
public class ProductResource extends AuthorizedResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{id}")
    public Product getOne(@PathVariable("id") String id, @RequestHeader("Authorization") String bearerToken){
        checkTokenValidity(bearerToken);
        return productService.getOne(id);
    }

    @GetMapping
    public List<Product> getAll(@RequestHeader("Authorization") String bearerToken){
        checkTokenValidity(bearerToken);
        return productService.getAll();
    }

    @PostMapping
    public Product add(@RequestBody Product product, @RequestHeader("Authorization") String bearerToken){
        authentifyAdmin(checkTokenValidity(bearerToken));
        return productService.add(product);
    }

    @PutMapping("{email}")
    public Product update(@PathVariable("id") String id, @RequestBody Product product, @RequestHeader("Authorization") String bearerToken){

        authentifyAdmin(checkTokenValidity(bearerToken));

        if(!id.equals(product.getGtin())){
            throw new RuntimeException("Error: wrong product id");
        }

        return productService.update(product);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id, @RequestHeader("Authorization") String bearerToken){
        authentifyAdmin(checkTokenValidity(bearerToken));
        productService.delete(id);
    }

}
