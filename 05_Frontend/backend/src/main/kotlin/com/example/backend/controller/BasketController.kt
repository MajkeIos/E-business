package com.example.backend.controller

import com.example.backend.model.Basket
import com.example.backend.service.BasketService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/baskets")
class BasketController(private val basketService: BasketService) {

    @PostMapping
    fun createBasket(): ResponseEntity<Basket> {
        val basket = basketService.createBasket()
        return ResponseEntity.ok(basket)
    }

    @GetMapping("/{id}")
    fun getBasketById(@PathVariable id: Long): ResponseEntity<Basket> {
        val basket = basketService.getBasketById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(basket)
    }

    @GetMapping("/{id}/value")
    fun getBasketValue(@PathVariable id: Long): ResponseEntity<Double> {
        val basketValue = basketService.calculateBasketValue(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(basketValue)
    }

    @PostMapping("/{basketId}/items")
    fun addItemToBasket(
        @PathVariable basketId: Long,
        @RequestParam productId: Long,
        @RequestParam quantity: Int
    ): ResponseEntity<Basket> {
        val basket = basketService.addItemToBasket(basketId, productId, quantity) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(basket)
    }

    @DeleteMapping("/{basketId}/items/{itemId}")
    fun removeItemFromBasket(@PathVariable basketId: Long, @PathVariable itemId: Long): ResponseEntity<Basket> {
        val basket = basketService.removeItemFromBasket(basketId, itemId) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(basket)
    }

    @DeleteMapping("/{id}")
    fun clearBasket(@PathVariable id: Long): ResponseEntity<Basket> {
        val basket = basketService.clearBasket(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(basket)
    }
}