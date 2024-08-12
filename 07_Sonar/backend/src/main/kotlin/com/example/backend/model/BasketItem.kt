package com.example.backend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class BasketItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    var quantity: Int,

    @ManyToOne
    @JoinColumn(name = "basket_id")
    @JsonIgnore
    val basket: Basket
) {
    override fun toString(): String {
        return "BasketItem(id=$id, product=$product, quantity=$quantity)"
    }
}