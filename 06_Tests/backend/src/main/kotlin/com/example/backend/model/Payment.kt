package com.example.backend.model

import jakarta.persistence.*

@Entity
data class Payment (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val amount: Double,

    val paymentMethod: String,

    val status: String,

    @OneToOne
    @JoinColumn(name = "basket_id")
    val basket: Basket
)