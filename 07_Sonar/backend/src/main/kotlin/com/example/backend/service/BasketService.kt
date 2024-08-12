package com.example.backend.service

import com.example.backend.model.Basket
import com.example.backend.model.BasketItem
import com.example.backend.repository.BasketRepository
import com.example.backend.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class BasketService(
    private val basketRepository: BasketRepository,
    private val productRepository: ProductRepository
) {

    fun createBasket(): Basket {
        val basket = Basket()
        return basketRepository.save(basket)
    }

    fun getBasketById(id: Long): Basket? {
        return basketRepository.findById(id).orElse(null)
    }

    fun calculateBasketValue(basketId: Long): Double? {
        val basket = basketRepository.findById(basketId).orElse(null) ?: return null
        return basket.items.sumByDouble { it.product.price * it.quantity }
    }

    fun addItemToBasket(basketId: Long, productId: Long, quantity: Int): Basket? {
        val basket = basketRepository.findById(basketId).orElse(null) ?: return null
        val product = productRepository.findById(productId).orElse(null) ?: return null
        var basketItem = basket.items.find { it.product.id == productId }

        if (basketItem == null) {
            basketItem = BasketItem(product = product, quantity = quantity, basket = basket)
            basket.items += basketItem
        } else {
            basketItem.quantity += quantity
        }

        return basketRepository.save(basket)
    }

    fun removeItemFromBasket(basketId: Long, itemId: Long): Basket? {
        val basket = basketRepository.findById(basketId).orElse(null) ?: return null
        basket.items = basket.items.filter { it.id != itemId }

        return basketRepository.save(basket)
    }

    fun clearBasket(basketId: Long): Basket? {
        val basket = basketRepository.findById(basketId).orElse(null) ?: return null
        basket.items = mutableListOf()

        return basketRepository.save(basket)
    }
}