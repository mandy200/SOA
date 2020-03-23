package com.esipe.msbasket.resource;

import com.esipe.msbasket.domain.Basket;
import com.esipe.msbasket.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "baskets")
public class BasketResource extends AuthorizedResource {

    private final BasketService basketService;

    @Autowired
    public BasketResource(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("{email}")
    public Basket getOne(@PathVariable("email") String email, @RequestHeader("Authorization") String bearerToken){
        checkTokenValidity(bearerToken);
        return basketService.getOne(email);
    }

    @GetMapping
    public List<Basket> getAll(@RequestHeader("Authorization") String bearerToken){
        checkTokenValidity(bearerToken);
        return basketService.getAll();
    }

    @PostMapping
    public Basket add(@RequestBody Basket basket, @RequestHeader("Authorization") String bearerToken){
        checkTokenValidity(bearerToken);
        return basketService.add(basket);
    }

    @PutMapping("{email}")
    public Basket update(@PathVariable("email") String email, @RequestBody Basket basket, @RequestHeader("Authorization") String bearerToken){

        checkTokenValidity(bearerToken);

        if(!email.equals(basket.getUser())){
            throw new RuntimeException("Error: wrong email");
        }

        return basketService.update(basket);
    }

    @DeleteMapping("{email}")
    public void delete(@PathVariable("email") String email, @RequestHeader("Authorization") String bearerToken){
        checkTokenValidity(bearerToken);
        basketService.delete(email);
    }

}
