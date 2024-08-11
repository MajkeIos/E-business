package com.example.backend.model

import jakarta.persistence.*

@Entity
data class Basket (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToMany(
        mappedBy = "basket",
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER)
    var items: List<BasketItem> = mutableListOf()
)