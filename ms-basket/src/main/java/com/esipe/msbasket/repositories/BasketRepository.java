package com.esipe.msbasket.repositories;

import com.esipe.msbasket.domain.Basket;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BasketRepository {

    private Map<String, Basket> baskets = new HashMap<>();

    public Basket save(Basket basket){
        baskets.put(basket.getUser(), basket);
        return basket;
    }

    public Basket update(Basket basket){
        return save(basket);
    }

    public Basket getOne(String email){
        return baskets.get(email);
    }

    public void delete(String email){
        baskets.remove(email);
    }

    public List<Basket> getAll() {
        return new ArrayList<>(baskets.values());
    }

}
