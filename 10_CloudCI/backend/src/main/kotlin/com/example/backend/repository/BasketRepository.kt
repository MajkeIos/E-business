package com.example.backend.repository

import com.example.backend.model.Basket
import org.springframework.data.jpa.repository.JpaRepository

interface BasketRepository : JpaRepository<Basket, Long>