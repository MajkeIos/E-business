package com.example.backend.service

import com.example.backend.model.Product
import com.example.backend.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findAll(): List<Product> = productRepository.findAll()

    fun findById(id: Long): Product? = productRepository.findById(id).orElse(null)

    fun save(product: Product): Product = productRepository.save(product)

    fun deleteById(id: Long) = productRepository.deleteById(id)
}