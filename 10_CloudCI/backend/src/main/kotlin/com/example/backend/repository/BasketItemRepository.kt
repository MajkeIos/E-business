package com.example.backend.repository

import com.example.backend.model.BasketItem
import org.springframework.data.jpa.repository.JpaRepository

interface BasketItemRepository : JpaRepository<BasketItem, Long>