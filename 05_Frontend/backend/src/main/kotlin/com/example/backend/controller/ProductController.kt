package com.example.backend.controller

import com.example.backend.model.Product
import com.example.backend.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getAllProducts(): List<Product> = productService.findAll()

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<Product> {
        val product = productService.findById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(product)
    }

    @PostMapping
    fun createProduct(@RequestBody product: Product): Product = productService.save(product)

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody product: Product): ResponseEntity<Product> {
        val existingProduct = productService.findById(id) ?: return ResponseEntity.notFound().build()
        val updatedProduct = productService.save(product.copy(id = existingProduct.id))
        return ResponseEntity.ok(updatedProduct)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        val existingProduct = productService.findById(id) ?: return ResponseEntity.notFound().build()
        productService.deleteById(existingProduct.id)
        return ResponseEntity.noContent().build()
    }
}