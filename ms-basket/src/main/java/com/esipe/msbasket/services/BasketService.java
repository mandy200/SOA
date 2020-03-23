package com.esipe.msbasket.services;

import com.esipe.msbasket.domain.Basket;
import com.esipe.msbasket.domain.BasketItem;
import com.esipe.msbasket.repositories.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {

    private final BasketRepository br;
    private final ProductService ps;

    @Autowired
    public BasketService(BasketRepository br, ProductService ps) {
        this.br = br;
        this.ps = ps;
    }

    public Basket add(Basket basket){

        verifyBasketItems(basket);

        double totalAmount = getTotalAmount(basket);
        basket.setTotalAmount(totalAmount);

        return br.save(basket);
    }


    public Basket getOne(String email){
        return Optional.ofNullable(
                br.getOne(email)
        ).orElseThrow(() -> new RuntimeException("Error: no basket found"));
    }

    public Basket update(Basket basket){

        Basket basketInDb = getOne(basket.getUser());

        verifyBasketItems(basket);

        basketInDb.setStatus(basket.getStatus());
        basketInDb.setItems(basket.getItems());

        final double totalAmount = getTotalAmount(basketInDb);
        basketInDb.setTotalAmount(totalAmount);

        return br.update(basketInDb);
    }

    public void delete(String email){
        getOne(email);
        br.delete(email);
    }

    public List<Basket> getAll(){
        return br.getAll();
    }

    private double getTotalAmount(Basket basket) {
        double totalAmount = 0;
        for (BasketItem item : basket.getItems()){
            totalAmount += (item.getUnitPrice() + ((item.getVat() * 100) / 100)) * item.getQuantity();
        }
        return totalAmount;
    }

    private void verifyBasketItems(Basket basket) {
        for (BasketItem item : basket.getItems()){
            ps.getOne(item.getGtin());
        }
    }
}
